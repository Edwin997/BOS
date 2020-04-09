package com.example.bca_bos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.keyboardadapters.KirimFormProdukAdapter;
import com.example.bca_bos.keyboardadapters.MutasiRekeningAdapter;
import com.example.bca_bos.keyboardadapters.OfflineMutasiRekeningAdapter;
import com.example.bca_bos.keyboardadapters.StokProdukAdapter;
import com.example.bca_bos.keyboardadapters.TemplatedTextAdapter;
import com.example.bca_bos.models.locations.KotaKab;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.networks.VolleyClass;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class KeyboardBOSnew extends InputMethodService implements KeyboardView.OnKeyboardActionListener, OnCallBackListener, View.OnClickListener,
        View.OnFocusChangeListener, View.OnTouchListener {

    //region DATA MEMBER

    //FINAL STATIC VALUE
    private final static int KEYCODE_CHANGE_NUMBER_SYMBOL = -7;

    private final static int KEY_DEFAULT = 0;
    private final static int KEY_NEXT = 1;
    private final static int KEY_OK = 2;

    private final static String KEY_ET_EXTERNAL = "g_et_external";
    private final static String KEY_ET_ONGKIR_ASAL = "g_et_ongkir_asal";
    private final static String KEY_ET_ONGKIR_TUJUAN = "g_et_ongkir_tujuan";
    private final static String KEY_ET_ONGKIR_BERAT = "g_et_ongkir_berat";
    private final static String KEY_ET_ONGKIR_KURIR = "g_et_ongkir_kurir";
    private final static String KEY_ET_STOK_SEARCH = "g_et_stok_search";
    private final static String KEY_ET_KIRIMFORM_SEARCH = "g_et_kirimform_search";
    private final static String KEY_ET_KIRIMFORM_NEXT_ASAL = "g_et_kirimform_next_asal";
    private final static String KEY_ET_KIRIMFORM_NEXT_KURIR = "g_et_kirimform_next_kurir";

    private final static String KEY_ET_TEXTWATCHER_SEARCH = "search_type";
    private final static String KEY_ET_TEXTWATCHER_BERAT = "berat_type";

    //LAYOUT KEYBOARD VIEW
    private View g_viewparent;
    private KeyboardView g_keyboardview;

    //DATA MEMBER KEYBOARD TYPE
    private Keyboard g_keyboard_alphabet_default, g_keyboard_alphabet_next, g_keyboard_alphabet_ok;
    private Keyboard g_keyboard_symbol1_default, g_keyboard_symbol1_next, g_keyboard_symbol1_ok;
    private Keyboard g_keyboard_symbol2_default, g_keyboard_symbol2_next, g_keyboard_symbol2_ok;
    private Keyboard g_keyboard_number;

    //DATA MEMBER CURRENT ACTIVE KEYBOARD
    private Keyboard g_curr_keyboard;

    //FLAG
    private boolean IS_CAPS = false;
//    private boolean IS_FIRST_CAPS = false;
    private boolean IS_ALPHABET = true;
    private boolean IS_SYMBOL1 = true;
    private boolean IS_INPUT_CONNECTION_EXTERNAL;
    private boolean IS_FILLED = false;
    private boolean IS_FIRST_SPACE = true;

    private boolean IS_LOGIN = false;

    private int KEYCODE_DONE_TYPE = 0;

    //KEYBOARD TEXT HANDLER
    private String focusedEditText="";
    private StringBuilder typedCharacters = new StringBuilder();

    //region HOME DATA MEMBER
    private LinearLayout g_home_layout;
    private ImageButton g_btn_home, g_btn_template_openapps;
    private RecyclerView g_templatedtext_recyclerview;
    private LinearLayoutManager g_linear_layout;
    private TextView g_no_login_message;
    //endregion

    //region FEATURE DATA MEMBER
    private LinearLayout g_feature_layout;
    private ImageButton g_btn_feature_back;
    private Button g_btn_feature_ongkir, g_btn_feature_stok, g_btn_feature_mutasi, g_btn_feature_kirimform;
    //endregion

    //region ONGKIR DATA MEMBER
    private LinearLayout g_ongkir_berat_layout, g_ongkir_asal_layout, g_ongkir_tujuan_layout, g_ongkir_kurir_layout, g_ongkir_cekongkir_layout;
    private ConstraintLayout g_ongkir_layout;
    private ImageButton g_btn_ongkir_berat_back,g_btn_ongkir_asal_back, g_btn_ongkir_tujuan_back, g_btn_ongkir_kurir_back,  g_btn_ongkir_back, g_btn_ongkir_refresh;
    private AutoCompleteTextView g_actv_ongkir_asal, g_actv_ongkir_tujuan;
    private EditText g_et_ongkir_berat;
    private Button g_btn_ongkir_cekongkir;

    private Button g_btn_ongkir_jne, g_btn_ongkir_tiki, g_btn_ongkir_pos;
    private Boolean IS_CHOOSE_JNE = false, IS_CHOOSE_TIKI = false, IS_CHOOSE_POS = false;

    private LottieAnimationView g_lav_ongkir_loading;
    private TextView g_tv_ongkir_error;

    public static List<String> g_city_name_list = new ArrayList<>();
    public List<KotaKab> g_city_list;
    private String g_asal_city;
    //endregion

    //region STOK DATA MEMBER
    private LinearLayout g_stok_layout, g_stok_search_layout, g_stok_produk_layout, g_stok_produk_add_layout;
    private RecyclerView g_rv_stok;
    private LinearLayoutManager g_stok_item_layout;
    private StokProdukAdapter g_stok_adapter;
    private ImageButton g_btn_stok_back;
    private EditText g_et_stok_search;
    private Spinner g_sp_stok_filter;
    private ArrayAdapter g_sp_stok_filter_adapter;

    private boolean IS_STOK_FOCUS = false;
    //endregion

    //region KIRIM FORM DATA MEMBER
    private LinearLayout g_kirimform_layout, g_kirimform_search_layout, g_kirimform_produk_layout, g_kirimform_produk_button_layout;
    private RecyclerView g_rv_kirimform_produk;
    private LinearLayoutManager g_kirimform_produk_item_layout;
    private KirimFormProdukAdapter g_kirimform_produk_adapter;
    private ImageButton g_btn_kirimform_back;
    private Button g_btn_kirimform_next;
    private EditText g_et_kirimform_search;

    private int g_kirimform_produk_total = 0;

    private boolean IS_KIRIMFORM_FOCUS = false;
    //endregion

    //region KIRIM FORM NEXT DATA MEMBER
    private LinearLayout g_kirimform_next_asal_layout;
    private ConstraintLayout g_kirimform_next_layout;
    private ImageButton g_btn_kirimform_next_asal_back, g_btn_kirimform_next_send;
    private AutoCompleteTextView g_actv_kirimform_next_asal;
    private LottieAnimationView g_lav_kirimform_next_loading;
    private TextView g_tv_kirimform_next_error;


    //endregion

    //region MUTASI DATA MEMBER
    private LinearLayout g_mutasi_layout;
    private RecyclerView g_mutasi_recyclerview;
    private LinearLayoutManager g_mutasi_item_layout;
    private ImageButton g_btn_mutasi_back;
    private MutasiRekeningAdapter g_mutasi_rekening_adapter;
    private OfflineMutasiRekeningAdapter g_offline_mutasi_rekening_adapter;
    private Switch g_switch;
    private TextView g_mutasi_tv_title;
    //endregion

    //DATA MEMBER KIRIM FORM NEXT = ONGKIR
    private ArrayAdapter<String> g_autocompleteadapter;
    private String g_asal_id_city, g_tujuan_id_city, g_asal_id_city_form, g_berat;

    //endregion

    public static KeyboardBOSnew g_instance;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String SELLER_ID = "SELLER_ID";
    int g_seller_id;

    //region METHOD INITIATE BASE KEYBOARD & KEYBOARD LISTENER
    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        setInputView(onCreateInputView(info, restarting));
    }

    public View onCreateInputView(EditorInfo attribute, boolean restarting){
        onStartInput(attribute, restarting);

        //Get Seller ID
        SharedPreferences l_preference = this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        if (l_preference.contains(SELLER_ID)){
            g_seller_id = l_preference.getInt(SELLER_ID, -1);
            IS_LOGIN = true;
        }else {
            g_seller_id = -1;
            IS_LOGIN = false;
        }

        //Inisialisasi
        g_instance = this;
        IS_INPUT_CONNECTION_EXTERNAL = true;

        try{
            initiateMenu();
            initiateFeature();
            initiateOngkir();
            initiateStok();
            initiateKirimForm();
            initiateKirimFormNext();
            initiateMutasi();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return g_viewparent;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        g_viewparent = getLayoutInflater().inflate(R.layout.layout_bcabos_keyboard, null);
        initiateKeyboardView();
        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_PHONE:
                g_curr_keyboard = g_keyboard_number;
                break;
            default:
                g_curr_keyboard = g_keyboard_alphabet_default;
        }
        g_keyboardview.setKeyboard(g_curr_keyboard);
    }

    private void initiateKeyboardView() {
        g_keyboardview = g_viewparent.findViewById(R.id.bcabos_keyboard_view);

        g_keyboard_alphabet_default = new Keyboard(this, R.xml.bcabos_keyboard_alphabet, R.integer.bos_keyboard_mode_alphabet_action_default);
        g_keyboard_alphabet_next = new Keyboard(this, R.xml.bcabos_keyboard_alphabet, R.integer.bos_keyboard_mode_alphabet_action_next);
        g_keyboard_alphabet_ok = new Keyboard(this, R.xml.bcabos_keyboard_alphabet, R.integer.bos_keyboard_mode_alphabet_action_ok);

        g_keyboard_symbol1_default = new Keyboard(this, R.xml.bcabos_keyboard_number_symbol, R.integer.bos_keyboard_mode_symbol_1_default);
        g_keyboard_symbol1_next = new Keyboard(this, R.xml.bcabos_keyboard_number_symbol, R.integer.bos_keyboard_mode_symbol_1_next);
        g_keyboard_symbol1_ok = new Keyboard(this, R.xml.bcabos_keyboard_number_symbol, R.integer.bos_keyboard_mode_symbol_1_ok);

        g_keyboard_symbol2_default = new Keyboard(this, R.xml.bcabos_keyboard_number_symbol, R.integer.bos_keyboard_mode_symbol_2_default);
        g_keyboard_symbol2_next = new Keyboard(this, R.xml.bcabos_keyboard_number_symbol, R.integer.bos_keyboard_mode_symbol_2_next);
        g_keyboard_symbol2_ok = new Keyboard(this, R.xml.bcabos_keyboard_number_symbol, R.integer.bos_keyboard_mode_symbol_2_ok);

        g_keyboard_number = new Keyboard(this, R.xml.bcabos_keyboard_number);

        g_keyboardview.setOnKeyboardActionListener(this);
        g_keyboardview.setPreviewEnabled(false);
    }

    //region METHOD IMPLEMENT KEYBOARD ACTION LISTENER
    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        final InputConnection l_inputconnection = getCurrentInputConnection();
        CharSequence selectedText = l_inputconnection.getSelectedText(0);

        switch (primaryCode){
            case KEYCODE_CHANGE_NUMBER_SYMBOL:
                IS_SYMBOL1 = !IS_SYMBOL1;
                setKeyboardType();
                break;
            case Keyboard.KEYCODE_DONE:
                doneAction(l_inputconnection);
                break;
            case Keyboard.KEYCODE_MODE_CHANGE :
                IS_SYMBOL1 = true;
                IS_ALPHABET = !IS_ALPHABET;
                setKeyboardType();
                break;
            case Keyboard.KEYCODE_DELETE :
                deleteKeyPressed(l_inputconnection, selectedText);
                break;
            case Keyboard.KEYCODE_SHIFT:
                IS_CAPS = !IS_CAPS;

                g_keyboard_alphabet_default.setShifted(IS_CAPS);
                g_keyboard_alphabet_next.setShifted(IS_CAPS);
                g_keyboard_alphabet_ok.setShifted(IS_CAPS);

                g_keyboardview.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_CANCEL:
                InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
                break;
            default :
                IS_FILLED = false;

                char code = (char) primaryCode;
                String tmpText = String.valueOf(code);
                if(tmpText.equals(" ")){
                    if(IS_FIRST_SPACE)
                        IS_FIRST_SPACE = false;
                    else{
                        IS_FIRST_SPACE = true;
                        deleteKeyPressed(l_inputconnection, selectedText);
                        tmpText = ". ";
                    }
                }
                else {
                    IS_FIRST_SPACE = true;
                }
                if(Character.isLetter(code) && IS_CAPS){
                    tmpText = tmpText.toUpperCase();
                }
                commitTextToBOSKeyboardEditText(tmpText);

        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
    //endregion

    //endregion

    private void initiateMenu() {
        //inisiasi layout
        g_home_layout = g_viewparent.findViewById(R.id.bcabos_extended_home_layout);

        //inisiasi recyclerview
        g_templatedtext_recyclerview = g_viewparent.findViewById(R.id.bcabos_extended_home_recyclerview);

        //inisiasi button
        g_btn_home = g_viewparent.findViewById(R.id.bcabos_extended_home_button);
        g_btn_template_openapps = g_viewparent.findViewById(R.id.bcabos_extended_home_button_add_template);

        //inisiasi textview
        g_no_login_message = g_viewparent.findViewById(R.id.bcabos_extended_home_text_not_login);

        if(IS_LOGIN){
            //config recyclerview
            g_linear_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
            TemplatedTextAdapter tmpTemplatedTextAdapter = new TemplatedTextAdapter();
            tmpTemplatedTextAdapter.setParentOnCallBack(this);

            g_templatedtext_recyclerview.setLayoutManager(g_linear_layout);
            g_templatedtext_recyclerview.setAdapter(tmpTemplatedTextAdapter);

            g_templatedtext_recyclerview.setVisibility(View.VISIBLE);
            g_no_login_message.setVisibility(View.GONE);
            g_btn_template_openapps.setVisibility(View.VISIBLE);

            VolleyClass.getTemplatedTextByName(getApplicationContext(), g_seller_id, Method.ASC, tmpTemplatedTextAdapter);
        }
        else{
            g_templatedtext_recyclerview.setVisibility(View.GONE);
            g_no_login_message.setVisibility(View.VISIBLE);
            g_btn_template_openapps.setVisibility(View.GONE);
        }

        //config button
        g_btn_home.setOnClickListener(this);
        g_btn_template_openapps.setOnClickListener(this);
    }

    private void initiateFeature() {
        //inisiasi layout
        g_feature_layout = g_viewparent.findViewById(R.id.bcabos_extended_feature_layout);

        //inisiasi button
        g_btn_feature_back = g_viewparent.findViewById(R.id.bcabos_extended_feature_back_button);
        g_btn_feature_ongkir = g_viewparent.findViewById(R.id.bcabos_extended_feature_ongkir_button);
        g_btn_feature_stok = g_viewparent.findViewById(R.id.bcabos_extended_feature_stok_button);
        g_btn_feature_kirimform = g_viewparent.findViewById(R.id.bcabos_extended_feature_form_button);
        g_btn_feature_mutasi = g_viewparent.findViewById(R.id.bcabos_extended_feature_mutasi_button);

        //config button
        g_btn_feature_back.setOnClickListener(this);
        g_btn_feature_ongkir.setOnClickListener(this);
        g_btn_feature_stok.setOnClickListener(this);
        g_btn_feature_kirimform.setOnClickListener(this);
        g_btn_feature_mutasi.setOnClickListener(this);

    }

    @SuppressLint("ClickableViewAccessibility") //ini biar dropdownnya langsung muncul data
    private void initiateOngkir(){

        //inisiasi layout
        g_ongkir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_layout);
        g_ongkir_berat_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_layout);
        g_ongkir_asal_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_layout);
        g_ongkir_tujuan_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_layout);
        g_ongkir_kurir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_kurir_layout);
        g_ongkir_cekongkir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_layout);
        g_lav_ongkir_loading = g_viewparent.findViewById(R.id.bcabos_ongkir_loading_animation_view);

        //inisiasi edittext
        VolleyClass.getCityKeyboard(getApplicationContext());
        g_actv_ongkir_asal = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_auto_complete_text_view);
        g_actv_ongkir_asal.setAdapter(g_autocompleteadapter);
        g_actv_ongkir_tujuan = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_auto_complete_text_view);
        g_actv_ongkir_tujuan.setAdapter(g_autocompleteadapter);
        g_et_ongkir_berat = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_text);

        //inisiasi button
        g_btn_ongkir_asal_back = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_back_button);
        g_btn_ongkir_tujuan_back = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_back_button);
        g_btn_ongkir_berat_back = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_back_button);

        g_btn_ongkir_jne = g_viewparent.findViewById(R.id.jneButton);
        g_btn_ongkir_tiki = g_viewparent.findViewById(R.id.tikiButton);
        g_btn_ongkir_pos = g_viewparent.findViewById(R.id.posButton);
        g_btn_ongkir_kurir_back = g_viewparent.findViewById(R.id.bcabos_ongkir_kurir_back_button);

        g_btn_ongkir_back = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_back_button);
        g_btn_ongkir_cekongkir = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_check_button);
        g_btn_ongkir_refresh = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_refresh_button);

        //Error Text View
        g_tv_ongkir_error = g_viewparent.findViewById(R.id.bcabos_ongkir_error_text_view);
        g_tv_ongkir_error.setOnClickListener(this);

        //config edittext
        //ASAL
        g_actv_ongkir_asal.setOnFocusChangeListener(this);
        g_actv_ongkir_asal.setOnTouchListener(this);
        g_actv_ongkir_asal.addTextChangedListener(new KeyboardBosTextWatcher(g_actv_ongkir_asal));
        g_actv_ongkir_asal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IS_FILLED = true;
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        //TUJUAN
        g_actv_ongkir_tujuan.setOnFocusChangeListener(this);
        g_actv_ongkir_tujuan.setOnTouchListener(this);
        g_actv_ongkir_tujuan.addTextChangedListener(new KeyboardBosTextWatcher(g_actv_ongkir_tujuan));
        g_actv_ongkir_tujuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IS_FILLED = true;
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        //BERAT
        g_et_ongkir_berat.addTextChangedListener(new KeyboardBosTextWatcher(KEY_ET_TEXTWATCHER_BERAT, g_et_ongkir_berat));
        g_et_ongkir_berat.setOnFocusChangeListener(this);
        g_et_ongkir_berat.setOnTouchListener(this);

        //config button
        //ASAL
        g_btn_ongkir_asal_back.setOnClickListener(this);

        //TUJUAN
        g_btn_ongkir_tujuan_back.setOnClickListener(this);

        //BERAT
        g_btn_ongkir_berat_back.setOnClickListener(this);

        //KURIR
        g_btn_ongkir_jne.setOnClickListener(this);
        g_btn_ongkir_tiki.setOnClickListener(this);
        g_btn_ongkir_pos.setOnClickListener(this);
        g_btn_ongkir_kurir_back.setOnClickListener(this);

        //SUBMIT
        g_btn_ongkir_back.setOnClickListener(this);
        g_btn_ongkir_cekongkir.setOnClickListener(this);
        g_btn_ongkir_refresh.setOnClickListener(this);
    }

    public void getCity(List<KotaKab> p_kotakab){
        g_city_name_list.clear();
        g_city_list = p_kotakab;
        String l_city_name;

        for (int i = 0; i < p_kotakab.size(); i++){
            l_city_name = p_kotakab.get(i).getKota_kab_name();

            g_city_name_list.add(l_city_name);
        }

        g_autocompleteadapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, g_city_name_list);
        g_autocompleteadapter.notifyDataSetChanged();
        g_autocompleteadapter.setNotifyOnChange(true);
    }

    public void cekOngkirLoading(String p_status){
        if (p_status.equals("show")){
            g_lav_ongkir_loading.setVisibility(View.VISIBLE);
        }else if (p_status.equals("hide")){
            g_lav_ongkir_loading.setVisibility(View.GONE);
        }else
            g_lav_ongkir_loading.setVisibility(View.GONE);
    }

    public void cekOngkirError(String p_status){
        if (p_status.equals("show")){
            g_tv_ongkir_error.setVisibility(View.VISIBLE);
        }else if (p_status.equals("hide")){
            g_tv_ongkir_error.setVisibility(View.GONE);
        }else
            g_tv_ongkir_error.setVisibility(View.GONE);
    }

    private void initiateStok(){
        //inisiasi layout
        g_stok_layout = g_viewparent.findViewById(R.id.bcabos_stok_layout);
        g_stok_search_layout = g_viewparent.findViewById(R.id.bcabos_stok_search_layout);
        g_stok_produk_layout = g_viewparent.findViewById(R.id.bcabos_stok_produk_layout);
        g_stok_produk_add_layout = g_viewparent.findViewById(R.id.bcabos_stok_tambah_product);

        //inisiasi recyclerview
        g_rv_stok = g_viewparent.findViewById(R.id.bcabos_stok_recyclerview);

        //inisiasi spinner
        g_sp_stok_filter = g_viewparent.findViewById(R.id.bcabos_stok_spinner_filter);

        //inisiasi edittext
        g_et_stok_search = g_viewparent.findViewById(R.id.bcabos_stok_search_edittext);

        //inisiasi button
        g_btn_stok_back = g_viewparent.findViewById(R.id.bcabos_stok_search_back_button);

        //config linearlayout
        g_stok_produk_add_layout.setOnClickListener(this);

        //config recyclerview
        g_stok_item_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        g_stok_adapter = new StokProdukAdapter();
        g_stok_adapter.setParentOnCallBack(this);
        g_rv_stok.setLayoutManager(g_stok_item_layout);
        g_rv_stok.setAdapter(g_stok_adapter);

        VolleyClass.getProductByName(getApplicationContext(), g_seller_id, Method.ASC, g_stok_adapter);

        //config spinner
        ArrayList<String> tmpInitialFilterValue = new ArrayList<>();
        g_sp_stok_filter_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tmpInitialFilterValue);
        g_sp_stok_filter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        g_sp_stok_filter.setAdapter(g_sp_stok_filter_adapter);
        g_sp_stok_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idx = VolleyClass.findProductCategoryId(position);
                g_stok_adapter.getProductFiltered(idx);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                g_stok_adapter.getProductFiltered(0);
            }
        });

        VolleyClass.getProductCategory(getApplicationContext(), g_seller_id, g_sp_stok_filter_adapter);

        //config edittext
        g_et_stok_search.setOnFocusChangeListener(this);
        g_et_stok_search.setOnTouchListener(this);
        g_et_stok_search.addTextChangedListener(new KeyboardBosTextWatcher(KEY_ET_TEXTWATCHER_SEARCH, g_et_stok_search));

        //config button
        g_btn_stok_back.setOnClickListener(this);
    }

    private void initiateKirimForm(){
        //inisiasi total count
        g_kirimform_produk_total = 0;

        //inisasi layout
        g_kirimform_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_layout);
        g_kirimform_search_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_search_layout);
        g_kirimform_produk_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_layout);
        g_kirimform_produk_button_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_button_layout);

        //inisiasi recyclerview
        g_rv_kirimform_produk = g_viewparent.findViewById(R.id.bcabos_kirimform_recyclerview);

        //inisiasi edittext
        g_et_kirimform_search = g_viewparent.findViewById(R.id.bcabos_kirimform_search_edittext);

        //inisiasi button
        g_btn_kirimform_back = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_button_back_button);
        g_btn_kirimform_next = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_button_next_button);

        //config recyclerview
        g_kirimform_produk_item_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        g_kirimform_produk_adapter = new KirimFormProdukAdapter();
        g_kirimform_produk_adapter.setParentOnCallBack(this);
        g_rv_kirimform_produk.setLayoutManager(g_kirimform_produk_item_layout);
        g_rv_kirimform_produk.setAdapter(g_kirimform_produk_adapter);
        VolleyClass.getProductByName(getApplicationContext(), g_seller_id, Method.ASC, g_kirimform_produk_adapter);

        //config edittext
        g_et_kirimform_search.setOnFocusChangeListener(this);
        g_et_kirimform_search.setOnTouchListener(this);

        //config button
        g_btn_kirimform_back.setOnClickListener(this);
        g_btn_kirimform_next.setOnClickListener(this);
    }

    @SuppressLint("ClickableViewAccessibility") //ini biar dropdownnya langsung muncul data
    private void initiateKirimFormNext(){
        //inisiasi layout
        g_kirimform_next_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_layout);
        g_kirimform_next_asal_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_asal_layout);
//        g_autocompleteadapter = RajaOngkir.getRajaOngkirCity(this);
        g_lav_kirimform_next_loading = g_viewparent.findViewById(R.id.bcabos_kirimform_next_loading_animation_view);

        //inisiasi edittext
        VolleyClass.getCityKeyboard(getApplicationContext());
        g_actv_kirimform_next_asal = g_viewparent.findViewById(R.id.bcabos_kirimform_next_asal_auto_complete_text_view);
        g_actv_kirimform_next_asal.setAdapter(g_autocompleteadapter);

        //inisiasi button
        g_btn_kirimform_next_asal_back = g_viewparent.findViewById(R.id.bcabos_kirimform_next_asal_back_button);
        g_btn_kirimform_next_send = g_viewparent.findViewById(R.id.bcabos_kirimform_send_button);

        //Error Text View
        g_tv_kirimform_next_error = g_viewparent.findViewById(R.id.bcabos_kirimform_next_error_text_view);
        g_tv_kirimform_next_error.setOnClickListener(this);

        //config edittext
        //ASAL
        g_actv_kirimform_next_asal.setOnFocusChangeListener(this);
        g_actv_kirimform_next_asal.setOnTouchListener(this);
        g_actv_kirimform_next_asal.addTextChangedListener(new KeyboardBosTextWatcher(g_actv_kirimform_next_asal));
        g_actv_kirimform_next_asal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IS_FILLED = true;
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        //config button
        //ASAL
        g_btn_kirimform_next_asal_back.setOnClickListener(this);
        g_btn_kirimform_next_send.setOnClickListener(this);

    }

    public void kirimFormNextLoading(String p_status){
        if (p_status.equals("show")){
            g_lav_kirimform_next_loading.setVisibility(View.VISIBLE);
        }else if (p_status.equals("hide")){
            g_lav_kirimform_next_loading.setVisibility(View.GONE);
        }else
            g_lav_kirimform_next_loading.setVisibility(View.GONE);
    }

    public void kirimFormNextError(String p_status){
        if (p_status.equals("show")){
            g_tv_kirimform_next_error.setVisibility(View.VISIBLE);
        }else if (p_status.equals("hide")){
            g_tv_kirimform_next_error.setVisibility(View.GONE);
        }else
            g_tv_kirimform_next_error.setVisibility(View.GONE);
    }

    private void initiateMutasi() {
        //inisiasi layout
        g_mutasi_layout = g_viewparent.findViewById(R.id.bcabos_extended_mutasi_layout);
        g_mutasi_tv_title = g_viewparent.findViewById(R.id.bcabos_mutasi_title_text_view);

        //inisiasi recyclerview
        g_mutasi_recyclerview = g_viewparent.findViewById(R.id.bcabos_extended_mutasi_recyclerview);

        //inisiasi button
        g_btn_mutasi_back = g_viewparent.findViewById(R.id.bcabos_mutasi_back_button);

        //config switch
        g_switch = g_viewparent.findViewById(R.id.bcabos_mutasi_switch);
        g_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    g_mutasi_tv_title.setText("Online");
                    g_mutasi_rekening_adapter = new MutasiRekeningAdapter();
                    VolleyClass.getTransaksi(getApplicationContext(), g_seller_id, -1, g_mutasi_rekening_adapter);
                    g_mutasi_recyclerview.setAdapter(g_mutasi_rekening_adapter);
                }else {
                    g_mutasi_tv_title.setText("Offline");
                    g_offline_mutasi_rekening_adapter = new OfflineMutasiRekeningAdapter();
                    VolleyClass.getTransaksiOffline(getApplicationContext(), g_seller_id, g_offline_mutasi_rekening_adapter);
                    g_mutasi_recyclerview.setAdapter(g_offline_mutasi_rekening_adapter);
                }
            }
        });

        //config recyclerview
        g_mutasi_item_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        g_mutasi_rekening_adapter = new MutasiRekeningAdapter();
        VolleyClass.getTransaksi(getApplicationContext(), g_seller_id, -1, g_mutasi_rekening_adapter);
        g_mutasi_recyclerview.setLayoutManager(g_mutasi_item_layout);
        g_mutasi_recyclerview.setAdapter(g_mutasi_rekening_adapter);

        //config button
        g_btn_mutasi_back.setOnClickListener(this);
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Product){
            Product tmpProduct = (Product) p_obj;
//            if(tmpProduct.getId_product() == -1){
//                Intent tmpIntent = new Intent(this, ApplicationContainer.class);
//                tmpIntent.putExtra(ApplicationContainer.KEY_OPEN_APPS, ApplicationContainer.ID_PRODUK);
//                tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(tmpIntent);
//            }
//            else {
                if(tmpProduct.getStock() <= 0){
                    commitTextToBOSKeyboardEditText("Maaf, stok kami untuk produk tersebut sudah habis. \nTerima kasih.");
                }else{
                    commitTextToBOSKeyboardEditText("Stok kami untuk produk tersebut masih ada. \nTerima kasih.");
                }
//            }
        }
        else if(p_obj instanceof String){
            String p_text = p_obj.toString();
            String[] tmpText = p_text.split(";");

            if(tmpText[0].equals("TEXT")){
                commitTextToBOSKeyboardEditText(tmpText[1]);
            }
            else if(tmpText[0].equals("SUBTOTAL")){
                g_kirimform_produk_total -= Integer.parseInt(tmpText[1]);
                g_kirimform_produk_total += Integer.parseInt(tmpText[2]);
                String tmpString = "NEXT (TOTAL : " + Method.getIndoCurrency(g_kirimform_produk_total) + ")";
                g_btn_kirimform_next.setText(tmpString);
            }
        }
    }

    @Override
    public void onClick(View p_view) {
        if(p_view == g_stok_produk_add_layout){
            Intent tmpIntent = new Intent(this, ApplicationContainer.class);
            tmpIntent.putExtra(ApplicationContainer.KEY_OPEN_APPS, ApplicationContainer.ID_PRODUK);
            tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(tmpIntent);
        }

        switch (p_view.getId()){
            //region ONCLICK AT HOME
            case R.id.bcabos_extended_home_button:
                if(IS_LOGIN){
                    showFeatureMenu();
                }
                else{
                    Intent tmpIntent = new Intent(this, StartActivity.class);
                    tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(tmpIntent);
                }

                break;
            case R.id.bcabos_extended_home_button_add_template:
                Intent tmpIntent = new Intent(this, ApplicationContainer.class);
                tmpIntent.putExtra(ApplicationContainer.KEY_OPEN_APPS, ApplicationContainer.ID_TEMPLATE);
                tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tmpIntent);
                break;
                //endregion

            //region ONCLICK AT FEATURE
            case R.id.bcabos_extended_feature_back_button:
                showHomeMenu();
                break;
            case R.id.bcabos_extended_feature_ongkir_button:
                showOngkir();
                break;
            case R.id.bcabos_extended_feature_stok_button:
                showStok();
                break;
            case R.id.bcabos_extended_feature_form_button:
                showKirimForm();
                break;
            case R.id.bcabos_extended_feature_mutasi_button:
                showMutasi();
                break;
                //endregion

            //region ONCLICK AT ONGKIR
            case R.id.bcabos_ongkir_asal_back_button:
                showOngkir();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                break;
            case R.id.bcabos_ongkir_tujuan_back_button:
                showOngkir();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                break;
            case R.id.bcabos_ongkir_berat_back_button:
                showOngkir();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                break;
            case R.id.jneButton:
                IS_CHOOSE_JNE = !IS_CHOOSE_JNE;
                configChooseCourierButton(p_view, IS_CHOOSE_JNE);
                break;
            case R.id.tikiButton:
                IS_CHOOSE_TIKI = !IS_CHOOSE_TIKI;
                configChooseCourierButton(p_view, IS_CHOOSE_TIKI);
                break;
            case R.id.posButton:
                IS_CHOOSE_POS = !IS_CHOOSE_POS;
                configChooseCourierButton(p_view, IS_CHOOSE_POS);
                break;
            case R.id.bcabos_ongkir_kurir_back_button:
                showOngkir();
                break;
            case R.id.bcabos_ongkir_cekongkir_back_button:
                focusedEditText = KEY_ET_EXTERNAL;
                refreshOngkir();
                showFeatureMenu();
                break;
            case R.id.bcabos_ongkir_cekongkir_check_button:
                cekOngkirLoading("show");
                focusedEditText = KEY_ET_EXTERNAL;
                getAsalCityId(g_actv_ongkir_asal);
                getTujuanCityId(g_actv_ongkir_tujuan);
                g_berat = g_et_ongkir_berat.getText().toString();
                getOngkirByCourier();
                showOngkir();
                break;
            case R.id.bcabos_ongkir_cekongkir_refresh_button:
                refreshOngkir();
                break;
            case R.id.bcabos_ongkir_error_text_view:
                g_tv_ongkir_error.setVisibility(View.GONE);
                break;
                //endregion

            //region ONCLICK AT STOK
            case R.id.bcabos_stok_search_back_button:
                focusedEditText = KEY_ET_EXTERNAL;
                if(IS_STOK_FOCUS)
                    showStok();
                else
                    showFeatureMenu();
                break;
            //endregion

            //region ONCLICK AT KIRIMFORM
            case R.id.bcabos_kirimform_produk_button_back_button:
                focusedEditText = KEY_ET_EXTERNAL;
                if(IS_KIRIMFORM_FOCUS)
                    showKirimForm();
                else
                    showFeatureMenu();
                break;
            case R.id.bcabos_kirimform_produk_button_next_button:
                if(g_kirimform_produk_adapter.getListOrder().size() > 0){
                    showKirimFormNext();    
                } else{
                    Toast.makeText(this, "Silahkan pilih produk terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                
                g_actv_kirimform_next_asal.requestFocus();
                break;
            //endregion

            //region ONCLICK AT KIRIMFORM NEXT
            case R.id.bcabos_kirimform_next_asal_back_button:
                showKirimForm();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                break;
            case R.id.bcabos_kirimform_send_button:
                kirimFormNextLoading("show");
                focusedEditText = KEY_ET_EXTERNAL;
                getAsalCityIdForm(g_actv_kirimform_next_asal);
                try {
                    VolleyClass.insertOrder(this, g_seller_id,Integer.valueOf(g_asal_id_city_form), g_kirimform_produk_adapter.getListOrder());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bcabos_kirimform_next_error_text_view:
                g_tv_kirimform_next_error.setVisibility(View.GONE);
                break;

            //endregion

            //region ONCLICK AT MUTASI
            case R.id.bcabos_mutasi_back_button:
                showFeatureMenu();
                break;
            //endregion
        }
    }

    private void configChooseCourierButton(View p_view, boolean p_choose){
        if (p_choose){
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_blue);
        }else {
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        }
    }

    @Override
    public void onFocusChange(View p_view, boolean hasFocus) {
        switch (p_view.getId()){
            //region ONFOCUSCHANGE ONGKIR
            case R.id.bcabos_ongkir_asal_auto_complete_text_view:
                configOnFocusChangeEdittext(KEY_ET_ONGKIR_ASAL, hasFocus, g_actv_ongkir_asal.getText().toString());
                break;
            case R.id.bcabos_ongkir_tujuan_auto_complete_text_view:
                configOnFocusChangeEdittext(KEY_ET_ONGKIR_TUJUAN, hasFocus, g_actv_ongkir_tujuan.getText().toString());
                break;
            case R.id.bcabos_ongkir_berat_text:
                configOnFocusChangeEdittext(KEY_ET_ONGKIR_BERAT, hasFocus, g_et_ongkir_berat.getText().toString());
                break;
            //endregion
            //region ONFOCUSCHANGE STOK
            case R.id.bcabos_stok_search_edittext:
                IS_STOK_FOCUS = hasFocus;
                configOnFocusChangeEdittext(KEY_ET_STOK_SEARCH, hasFocus, g_et_stok_search.getText().toString());
                break;
            //endregion
            //region ONFOCUSCHANGE KIRIMFORM
            case R.id.bcabos_kirimform_search_edittext:
                IS_KIRIMFORM_FOCUS = hasFocus;
                configOnFocusChangeEdittext(KEY_ET_KIRIMFORM_SEARCH, hasFocus, g_et_kirimform_search.getText().toString());
                break;
            //endregion
            //region ONFOCUSCHANGE KIRIMFORM NEXT
            case R.id.bcabos_kirimform_next_asal_auto_complete_text_view:
                configOnFocusChangeEdittext(KEY_ET_KIRIMFORM_NEXT_ASAL, hasFocus, g_actv_kirimform_next_asal.getText().toString());
                break;
            //endregion
        }
    }

    private void configOnFocusChangeEdittext(String p_key, boolean p_focus, String p_text){
        if (p_focus){
            focusedEditText = p_key;
            typedCharacters.setLength(0);
            typedCharacters.append(p_text);
        } else {
            typedCharacters.delete(0, typedCharacters.length());
        }
    }

    @Override
    public boolean onTouch(View p_view, MotionEvent event) {
        switch (p_view.getId()){
            //region ONTOUCH ONGKIR
            case R.id.bcabos_ongkir_asal_auto_complete_text_view:
                focusedEditText = KEY_ET_ONGKIR_ASAL;
                showAsalMenu();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_NEXT;
                setKeyboardType();
                break;
            case R.id.bcabos_ongkir_tujuan_auto_complete_text_view:
                focusedEditText = KEY_ET_ONGKIR_TUJUAN;
                showTujuanMenu();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_NEXT;
                setKeyboardType();
                break;
            case R.id.bcabos_ongkir_berat_text:
                focusedEditText = KEY_ET_ONGKIR_BERAT;
                showBeratMenu();
                g_keyboardview.setKeyboard(g_keyboard_number);
                break;
            //endregion

            //region ONTOUCH STOK
            case R.id.bcabos_stok_search_edittext:
                showStokSearch();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                break;
            //endregion

            //region ONTOUCH KIRIMFORM
            case R.id.bcabos_kirimform_search_edittext:
                showKirimFormSearch();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                break;
            //endregion

        }
        return false;
    }

    //region KEYBOARD BOS SHOW VIEW UTIL
    private void refreshDisplay(){
        //keyboard
        g_keyboardview.setVisibility(View.VISIBLE);

        //parent layout
        g_home_layout.setVisibility(View.GONE);
        g_feature_layout.setVisibility(View.GONE);
        g_ongkir_layout.setVisibility(View.GONE);
        g_stok_layout.setVisibility(View.GONE);
        g_kirimform_layout.setVisibility(View.GONE);
        g_kirimform_next_layout.setVisibility(View.GONE);
        g_mutasi_layout.setVisibility(View.GONE);

        //inside ongkir layout
        g_ongkir_berat_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_berat_back.setVisibility(View.VISIBLE);
        g_ongkir_asal_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_asal_back.setVisibility(View.VISIBLE);
        g_ongkir_tujuan_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_tujuan_back.setVisibility(View.VISIBLE);
        g_ongkir_kurir_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_kurir_back.setVisibility(View.VISIBLE);
        g_ongkir_cekongkir_layout.setVisibility(View.VISIBLE);

        //inside stok layout
        g_stok_produk_layout.setVisibility(View.VISIBLE);
        g_stok_search_layout.setVisibility(View.VISIBLE);

        //inside kirim form layout
        g_kirimform_produk_layout.setVisibility(View.VISIBLE);
        g_kirimform_search_layout.setVisibility(View.VISIBLE);
        g_kirimform_produk_button_layout.setVisibility(View.VISIBLE);

        //inside kirim form next layout
        g_kirimform_next_asal_layout.setVisibility(View.VISIBLE);
    }

    private void showHomeMenu() {
        refreshDisplay();
        g_home_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(true);
    }

    private void showFeatureMenu() {
        refreshDisplay();
        g_feature_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(true);
    }

    //region SHOW ONGKIR
    private void showOngkir() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(setHeight283());
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_keyboardview.setVisibility(View.GONE);

        g_btn_ongkir_asal_back.setVisibility(View.GONE);
        g_btn_ongkir_tujuan_back.setVisibility(View.GONE);
        g_btn_ongkir_berat_back.setVisibility(View.GONE);
        g_btn_ongkir_kurir_back.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showBeratMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(setHeightDefault());
        g_ongkir_layout.setVisibility(View.VISIBLE);

        goneOngkirMenu();
        g_ongkir_berat_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showAsalMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(setHeightDefault());
        g_ongkir_layout.setVisibility(View.VISIBLE);

        goneOngkirMenu();
        g_ongkir_asal_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showTujuanMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(setHeightDefault());
        g_ongkir_layout.setVisibility(View.VISIBLE);

        goneOngkirMenu();
        g_ongkir_tujuan_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showKurirMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(setHeightDefault());
        g_ongkir_layout.setVisibility(View.VISIBLE);

        goneOngkirMenu();
        g_ongkir_kurir_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void goneOngkirMenu(){
        g_ongkir_berat_layout.setVisibility(View.GONE);
        g_ongkir_asal_layout.setVisibility(View.GONE);
        g_ongkir_tujuan_layout.setVisibility(View.GONE);
        g_ongkir_kurir_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
    }
    //endregion

    //region SHOW STOK
    private void showStok() {
        refreshDisplay();
        g_stok_layout.setLayoutParams(setHeight283());
        g_keyboardview.setVisibility(View.GONE);
        g_stok_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showStokSearch() {
        refreshDisplay();
        g_stok_layout.setLayoutParams(setHeightDefault());
        g_stok_layout.setVisibility(View.VISIBLE);
        g_stok_produk_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }
    //endregion

    //region SHOW KIRIMFORM
    private void showKirimForm() {
        refreshDisplay();
        g_kirimform_layout.setLayoutParams(setHeight283());
        g_keyboardview.setVisibility(View.GONE);
        g_kirimform_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showKirimFormSearch() {
        refreshDisplay();
        g_kirimform_layout.setLayoutParams(setHeightDefault());
        g_kirimform_layout.setVisibility(View.VISIBLE);
        g_kirimform_produk_layout.setVisibility(View.GONE);
        g_kirimform_produk_button_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }
    //endregion

    //region SHOW KIRIMFORM NEXT
    private void showKirimFormNext() {
        refreshDisplay();
        g_kirimform_next_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void goneKirimFormNextMenu(){
        g_kirimform_next_asal_layout.setVisibility(View.GONE);
        g_keyboardview.setVisibility(View.GONE);
    }
    //endregion

    //region SHOW MUTASI
    private void showMutasi() {
        refreshDisplay();
        g_mutasi_layout.setLayoutParams(setHeight283());
        g_mutasi_layout.setVisibility(View.VISIBLE);
        g_keyboardview.setVisibility(View.GONE);
        changeLayoutStatus(true);
    }
    //endregion

    //endregion

    private void changeLayoutStatus(Boolean homeStatus){
        this.IS_INPUT_CONNECTION_EXTERNAL = homeStatus;
    }

    //Menghapus isi editText
    private void refreshOngkir() {
        g_et_ongkir_berat.setText("");
        g_actv_ongkir_asal.setText("");
        g_actv_ongkir_tujuan.setText("");

        IS_CHOOSE_JNE = false;
        g_btn_ongkir_jne.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        IS_CHOOSE_TIKI = false;
        g_btn_ongkir_tiki.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        IS_CHOOSE_POS = false;
        g_btn_ongkir_pos.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
    }

    //Mendapatkan ID dari City
    private void getAsalCityId(AutoCompleteTextView p_autocomplete){
        int l_position = g_city_name_list.indexOf(p_autocomplete.getText().toString());
        g_asal_id_city = String.valueOf(g_city_list.get(l_position).getId_kota_kab());
    }

    private void getTujuanCityId(AutoCompleteTextView p_autocomplete){
        int l_position = g_city_name_list.indexOf(p_autocomplete.getText().toString());
        g_tujuan_id_city = String.valueOf(g_city_list.get(l_position).getId_kota_kab());
    }

    private void getAsalCityIdForm(AutoCompleteTextView p_autocomplete){
        int l_position = g_city_name_list.indexOf(p_autocomplete.getText().toString());
        g_asal_id_city_form = String.valueOf(g_city_list.get(l_position).getId_kota_kab());
    }

    private void setKeyboardType(){
        if(IS_ALPHABET){
            if(KEYCODE_DONE_TYPE == KEY_DEFAULT){
                g_keyboardview.setKeyboard(g_keyboard_alphabet_default);
            }
            else if(KEYCODE_DONE_TYPE == KEY_NEXT){
                g_keyboardview.setKeyboard(g_keyboard_alphabet_next);
            }
            else if(KEYCODE_DONE_TYPE == KEY_OK) {
                g_keyboardview.setKeyboard(g_keyboard_alphabet_ok);
            }
        }
        else{
            if(IS_SYMBOL1){
                if(KEYCODE_DONE_TYPE == KEY_DEFAULT){
                    g_keyboardview.setKeyboard(g_keyboard_symbol1_default);
                }
                else if(KEYCODE_DONE_TYPE == KEY_NEXT){
                    g_keyboardview.setKeyboard(g_keyboard_symbol1_next);
                }
                else if(KEYCODE_DONE_TYPE == KEY_OK) {
                    g_keyboardview.setKeyboard(g_keyboard_symbol1_ok);
                }
            }
            else{
                if(KEYCODE_DONE_TYPE == KEY_DEFAULT){
                    g_keyboardview.setKeyboard(g_keyboard_symbol2_default);
                }
                else if(KEYCODE_DONE_TYPE == KEY_NEXT){
                    g_keyboardview.setKeyboard(g_keyboard_symbol2_next);
                }
                else if(KEYCODE_DONE_TYPE == KEY_OK) {
                    g_keyboardview.setKeyboard(g_keyboard_symbol2_ok);
                }
            }
        }
    }

    public void commitTextToBOSKeyboardEditText(String character){
        typedCharacters.append(character);

        switch (focusedEditText) {
            case KEY_ET_ONGKIR_ASAL:
                g_actv_ongkir_asal.setText(typedCharacters);
                g_actv_ongkir_asal.setSelection(g_actv_ongkir_asal.getText().length());
                break;
            case KEY_ET_ONGKIR_TUJUAN:
                g_actv_ongkir_tujuan.setText(typedCharacters);
                g_actv_ongkir_tujuan.setSelection(g_actv_ongkir_tujuan.getText().length());
                break;
            case KEY_ET_ONGKIR_BERAT:
                g_et_ongkir_berat.setText(typedCharacters);
                g_et_ongkir_berat.setSelection(g_et_ongkir_berat.getText().length());
                break;
            case KEY_ET_STOK_SEARCH:
                g_et_stok_search.setText(typedCharacters);
                g_et_stok_search.setSelection(g_et_stok_search.getText().length());
                break;
            case KEY_ET_KIRIMFORM_SEARCH:
                g_et_kirimform_search.setText(typedCharacters);
                g_et_kirimform_search.setSelection(g_et_kirimform_search.getText().length());
                break;
            case KEY_ET_KIRIMFORM_NEXT_ASAL:
                g_actv_kirimform_next_asal.setText(typedCharacters);
                g_actv_kirimform_next_asal.setSelection(g_actv_kirimform_next_asal.getText().length());
                break;
            default:
                InputConnection inputConnection = getCurrentInputConnection();
                inputConnection.commitText(character, 1);
                break;
        }

    }

    private void deleteKeyPressed(InputConnection ic, CharSequence selectedText){
        if(IS_INPUT_CONNECTION_EXTERNAL){
            if(TextUtils.isEmpty(selectedText)) {
                ic.deleteSurroundingText(1, 0);
            } else {
                ic.commitText("", 1);
            }
        } else if(!IS_INPUT_CONNECTION_EXTERNAL){
            switch (focusedEditText) {
                case KEY_ET_ONGKIR_BERAT:
                    int etBeratLength = g_et_ongkir_berat.getText().length();
                    if (etBeratLength > 0) {
                        g_et_ongkir_berat.getText().delete(etBeratLength - 1, etBeratLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etBeratLength - 1);
                        }
                    }
                    g_et_ongkir_berat.setSelection(g_et_ongkir_berat.getText().length());
                    break;
                case KEY_ET_ONGKIR_ASAL:
                    int etAsalLength = g_actv_ongkir_asal.getText().length();
                    if (etAsalLength > 0) {
                        g_actv_ongkir_asal.getText().delete(etAsalLength - 1, etAsalLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etAsalLength - 1);
                        }
                    }
                    g_actv_ongkir_asal.setSelection(g_actv_ongkir_asal.getText().length());
                    break;
                case KEY_ET_ONGKIR_TUJUAN:
                    int etTujuanLength = g_actv_ongkir_tujuan.getText().length();
                    if (etTujuanLength > 0) {
                        g_actv_ongkir_tujuan.getText().delete(etTujuanLength - 1, etTujuanLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etTujuanLength - 1);
                        }
                    }
                    g_actv_ongkir_tujuan.setSelection(g_actv_ongkir_tujuan.getText().length());
                    break;
                case KEY_ET_STOK_SEARCH:
                    int etStokLength = g_et_stok_search.getText().length();
                    if (etStokLength > 0) {
                        g_et_stok_search.getText().delete(etStokLength - 1, etStokLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etStokLength - 1);
                        }
                    }
                    g_et_stok_search.setSelection(g_et_stok_search.getText().length());
                    break;
                case KEY_ET_KIRIMFORM_SEARCH:
                    int etKirimFormSearchLength = g_et_kirimform_search.getText().length();
                    if (etKirimFormSearchLength > 0) {
                        g_et_kirimform_search.getText().delete(etKirimFormSearchLength - 1, etKirimFormSearchLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etKirimFormSearchLength - 1);
                        }
                    }
                    g_et_kirimform_search.setSelection(g_et_kirimform_search.getText().length());
                    break;
                case KEY_ET_KIRIMFORM_NEXT_ASAL:
                    int etAsalNextLength = g_actv_kirimform_next_asal.getText().length();
                    if (etAsalNextLength > 0) {
                        g_actv_kirimform_next_asal.getText().delete(etAsalNextLength - 1, etAsalNextLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etAsalNextLength - 1);
                        }
                    }
                    g_actv_kirimform_next_asal.setSelection(g_actv_kirimform_next_asal.getText().length());
                    break;
            }
        }
    }

    private void doneAction(InputConnection ic){
        switch (focusedEditText){
            case KEY_ET_ONGKIR_ASAL:
                showTujuanMenu();
                KEYCODE_DONE_TYPE = KEY_NEXT;
                configOnFocusChangeEdittext(KEY_ET_ONGKIR_TUJUAN, true, g_actv_ongkir_tujuan.getText().toString());
                setKeyboardType();
                g_actv_ongkir_tujuan.requestFocus(); //memunculkan cursor
                break;
            case KEY_ET_ONGKIR_TUJUAN:
                focusedEditText = KEY_ET_ONGKIR_BERAT;
                showBeratMenu();
                g_keyboardview.setKeyboard(g_keyboard_number);
                KEYCODE_DONE_TYPE = KEY_NEXT;
                typedCharacters.delete(0,typedCharacters.length());;
                g_et_ongkir_berat.requestFocus(); //memunculkan cursor
                break;
            case KEY_ET_ONGKIR_BERAT:
                focusedEditText = KEY_ET_ONGKIR_KURIR;
                showKurirMenu();
                KEYCODE_DONE_TYPE = KEY_OK;
                setKeyboardType();
                typedCharacters.delete(0,typedCharacters.length());
                break;
            case KEY_ET_ONGKIR_KURIR:
                focusedEditText = KEY_ET_EXTERNAL;
                showOngkir();
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                typedCharacters.delete(0,typedCharacters.length());
                break;
            case KEY_ET_KIRIMFORM_NEXT_KURIR:
                focusedEditText = KEY_ET_EXTERNAL;
                showKirimFormNext();
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                typedCharacters.delete(0,typedCharacters.length());
                break;
            default:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                typedCharacters.delete(0,typedCharacters.length());
        }
    }

    private void getOngkirByCourier() {
        if (IS_CHOOSE_JNE){
            VolleyClass.getOngkirCost(getApplicationContext(), g_asal_id_city, g_tujuan_id_city, g_berat, "jne");
        }
        if (IS_CHOOSE_TIKI){
            VolleyClass.getOngkirCost(getApplicationContext(), g_asal_id_city, g_tujuan_id_city, g_berat, "tiki");
        }
        if (IS_CHOOSE_POS){
            VolleyClass.getOngkirCost(getApplicationContext(), g_asal_id_city, g_tujuan_id_city, g_berat, "pos");
        }
        if (!IS_CHOOSE_JNE && !IS_CHOOSE_TIKI && !IS_CHOOSE_POS){
            commitTextToBOSKeyboardEditText("Masukan kurir terlebih dahulu");
            cekOngkirLoading("hide");
        }
    }

    private LinearLayout.LayoutParams setHeight283(){
        int tmpheightdp = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 283, getResources().getDisplayMetrics() );
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tmpheightdp);
    }

    private LinearLayout.LayoutParams setHeightDefault(){
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private class KeyboardBosTextWatcher implements TextWatcher {

        private EditText l_edittext;
        private String l_type;
        private AutoCompleteTextView l_autocomplete;

        private int l_delay = 1000;
        private int l_count_char = 0;
        private long l_time_last_editted = 0;
        private Handler l_handler;

        private Runnable l_thread_show_dropdown = new Runnable() {
            public void run() {
                if (l_autocomplete != null) {
                    if (System.currentTimeMillis() > (l_time_last_editted + l_delay - 500)) {
                        if(!IS_FILLED){
                            l_autocomplete.showDropDown();
                            g_keyboardview.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        };

        private Runnable l_thread_show_search_result = new Runnable() {
            public void run() {
                if (l_edittext != null) {
                    if (System.currentTimeMillis() > (l_time_last_editted + l_delay - 500)) {
                        g_stok_adapter.findProduct(l_edittext.getText().toString());
                        showStok();
                    }
                }
            }
        };

        public KeyboardBosTextWatcher(String p_type, EditText p_edittext){
            l_edittext = p_edittext;
            l_type = p_type;
            l_handler = new Handler();
        }

        public KeyboardBosTextWatcher(AutoCompleteTextView p_autocomplete) {
            l_autocomplete = p_autocomplete;
            l_handler = new Handler();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            g_autocompleteadapter.notifyDataSetChanged();
            l_count_char = charSequence.length();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            l_handler.removeCallbacks(l_thread_show_dropdown);

        }

        @Override
        public void afterTextChanged(final Editable editable) {
            if(l_autocomplete != null){
                if (editable.length() > 0 && l_count_char != editable.length()) {
                    l_time_last_editted = System.currentTimeMillis();
                    l_handler.postDelayed(l_thread_show_dropdown, l_delay);
                }
            }

            if(l_edittext != null){
                switch (l_type){
                    case KEY_ET_TEXTWATCHER_BERAT:
                        String tmp_added_number = l_edittext.getText().toString();
                        if (tmp_added_number.length() != 0) {
                            int tmpnumber  = Integer.parseInt(tmp_added_number);

                            if (tmpnumber > 30000){
                                l_edittext.setText("30000");
                                Toast.makeText(getApplicationContext(), "Maksimal berat 30.000 gram", Toast.LENGTH_SHORT).show();
                            } if (tmpnumber < 1){
                                l_edittext.setText("");
                            }

                        }
                        break;
                    case KEY_ET_TEXTWATCHER_SEARCH:
                        if (editable.length() > 0 && l_count_char != editable.length()) {
                            l_time_last_editted = System.currentTimeMillis();
                            l_handler.postDelayed(l_thread_show_search_result, l_delay);
                        }
                        break;
                }
            }
        }
    }

}
