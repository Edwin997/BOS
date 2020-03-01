package com.example.bca_bos;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.keyboardadapters.KirimFormProdukAdapter;
import com.example.bca_bos.keyboardadapters.MutasiRekeningAdapter;
import com.example.bca_bos.keyboardadapters.StokProdukAdapter;

import com.example.bca_bos.keyboardadapters.TemplatedTextAdapter;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;

public class KeyboardBOS extends InputMethodService implements KeyboardView.OnKeyboardActionListener, OnCallBackListener {

    private final static int KEYCODE_CHANGE_NUMBER_SYMBOL = -7;

    private final static int KEY_DEFAULT = 0;
    private final static int KEY_NEXT = 1;
    private final static int KEY_OK = 2;

    private View g_viewparent;
    private KeyboardView g_keyboardview;

    private Keyboard g_keyboard_alphabet_default, g_keyboard_alphabet_next, g_keyboard_alphabet_ok;
    private Keyboard g_keyboard_symbol1_default, g_keyboard_symbol1_next, g_keyboard_symbol1_ok;
    private Keyboard g_keyboard_symbol2_default, g_keyboard_symbol2_next, g_keyboard_symbol2_ok;
    private Keyboard g_keyboard_number;


    private boolean IS_CAPS = false;
    private boolean IS_ALPHABET = true;
    private boolean IS_SYMBOL1 = true;

    private int KEYCODE_DONE_TYPE = 0;

    private boolean isInputConnectionExternalBOSKeyboard;
    String focusedEditText="";
    StringBuilder typedCharacters = new StringBuilder();

    //HOME
    private LinearLayout g_home_layout;
    private ImageButton g_btn_home, g_btn_template_openapps;
    private RecyclerView g_templatedtext_recyclerview;
    private LinearLayoutManager g_linear_layout;

    //FEATURE
    private LinearLayout g_feature_layout;
    private ImageButton g_btn_feature_back;
    private Button g_btn_feature_ongkir, g_btn_feature_stok, g_btn_feature_mutasi, g_btn_feature_kirimform;

    //ONGKIR
    private LinearLayout g_ongkir_layout, g_ongkir_berat_layout, g_ongkir_asal_layout, g_ongkir_tujuan_layout, g_ongkir_kurir_layout, g_ongkir_cekongkir_layout;
    private ImageButton g_btn_ongkir_berat_back,g_btn_ongkir_asal_back, g_btn_ongkir_tujuan_back, g_btn_ongkir_kurir_back,  g_btn_ongkir_back, g_btn_ongkir_refresh;
    private AutoCompleteTextView g_actv_ongkir_asal, g_actv_ongkir_tujuan;
    private EditText g_et_ongkir_berat;
    private Button g_btn_ongkir_cekongkir;


    private ArrayAdapter<String> g_ongkir_asaladapter;
    private ArrayAdapter<String> g_ongkir_tujuanadapter;

    private String g_ongkir_asal, g_ongkir_tujuan, g_ongkir_berat;

    private Button g_btn_ongkir_jne, g_btn_ongkir_tiki, g_btn_ongkir_pos;
    private Boolean g_ongkir_kurir_jne = false, g_ongkir_kurir_tiki = false, g_ongkir_kurir_pos = false;

    //STOK
    private LinearLayout g_stok_layout, g_stok_search_layout, g_stok_produk_layout;
    private RecyclerView g_rv_stok;
    private LinearLayoutManager g_stok_item_layout;
    private StokProdukAdapter g_stok_adapter;
    private ImageButton g_btn_stok_back;
    private EditText g_et_stok_search;
    private boolean g_et_stok_keyboard_fokus = false;

    //KIRIM FORM
    private LinearLayout g_kirimform_layout, g_kirimform_search_layout, g_kirimform_produk_layout, g_kirimform_produk_button_layout;
    private RecyclerView g_rv_kirimform_produk;
    private LinearLayoutManager g_kirimform_produk_item_layout;
    private KirimFormProdukAdapter g_kirimform_produk_adapter;
    private ImageButton g_btn_kirimform_back;
    private Button g_btn_kirimform_next;
    private EditText g_et_kirimform_search;
    private int g_kirimform_produk_total = 0;
    private boolean g_et_kirimform_keyboard_fokus = false;

    //KIRIM FORM NEXT
    private LinearLayout g_kirimform_next_layout, g_kirimform_next_berat_layout, g_kirimform_next_asal_layout, g_kirimform_next_tujuan_layout, g_kirimform_next_kurir_layout, g_kirimform_next_kirim_layout;
    private ImageButton g_btn_kirimform_next_berat_back,g_btn_kirimform_next_asal_back, g_btn_kirimform_next_tujuan_back, g_btn_kirimform_next_kurir_back,  g_btn_kirimform_next_back;
    private AutoCompleteTextView g_actv_kirimform_next_asal, g_actv_kirimform_next_tujuan;
    private EditText g_et_kirimform_next_berat;
    private Button g_btn_kirimform_next_kirim;

    private ArrayAdapter<String> g_kirimform_next_asaladapter;
    private ArrayAdapter<String> g_kirimform_next_tujuanadapter;

    private String g_kirimform_next_asal, g_kirimform_next_tujuan, g_kirimform_next_berat;

    private Button g_btn_kirimform_next_jne, g_btn_kirimform_next_tiki, g_btn_kirimform_next_pos;
    private Boolean g_kirimform_next_kurir_jne = false, g_kirimform_next_kurir_tiki = false, g_kirimform_next_kurir_pos = false;

    //MUTASI REKENING
    private LinearLayout g_mutasi_layout;
    private RecyclerView g_mutasi_recyclerview;
    private LinearLayoutManager g_mutasi_item_layout;
    private ImageButton g_btn_mutasi_back;


    @Override
    public View onCreateInputView() {

        isInputConnectionExternalBOSKeyboard = true;
        g_viewparent = getLayoutInflater().inflate(R.layout.layout_bcabos_keyboard, null);
        initiateKeyboardView();

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

        g_keyboardview.setKeyboard(g_keyboard_alphabet_default);
        g_keyboardview.setOnKeyboardActionListener(this);
        g_keyboardview.setPreviewEnabled(false);
    }

    private void initiateMenu() {
        g_home_layout = g_viewparent.findViewById(R.id.bcabos_extended_home_layout);

        //Recyclerview Template text
        g_templatedtext_recyclerview = g_viewparent.findViewById(R.id.bcabos_extended_home_recyclerview);
        g_linear_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        TemplatedTextAdapter tta = new TemplatedTextAdapter();
        tta.setParentOnCallBack(this);

        g_templatedtext_recyclerview.setLayoutManager(g_linear_layout);
        g_templatedtext_recyclerview.setAdapter(tta);

        g_btn_home = g_viewparent.findViewById(R.id.bcabos_extended_home_button);
        g_btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFeatureMenu();
            }
        });

        g_btn_template_openapps = g_viewparent.findViewById(R.id.bcabos_extended_home_button_add_template);
        g_btn_template_openapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmpIntent = new Intent(KeyboardBOS.this, ApplicationContainer.class);
                tmpIntent.putExtra(ApplicationContainer.KEY_OPEN_APPS, ApplicationContainer.ID_TEMPLATE);
                tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tmpIntent);
            }
        });

    }

    private void initiateFeature() {
        g_feature_layout = g_viewparent.findViewById(R.id.bcabos_extended_feature_layout);

        g_btn_feature_back = g_viewparent.findViewById(R.id.bcabos_extended_feature_back_button);
        g_btn_feature_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHomeMenu();
            }
        });

        g_btn_feature_ongkir = g_viewparent.findViewById(R.id.bcabos_extended_feature_ongkir_button);
        g_btn_feature_ongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOngkir();
            }
        });

        g_btn_feature_stok = g_viewparent.findViewById(R.id.bcabos_extended_feature_stok_button);
        g_btn_feature_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStok();
            }
        });

        g_btn_feature_kirimform = g_viewparent.findViewById(R.id.bcabos_extended_feature_form_button);
        g_btn_feature_kirimform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKirimForm();
            }
        });

        g_btn_feature_mutasi = g_viewparent.findViewById(R.id.bcabos_extended_feature_mutasi_button);
        g_btn_feature_mutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMutasi();
            }
        });

    }

    private void initiateOngkir(){
        g_ongkir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_layout);
        g_ongkir_berat_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_layout);
        g_ongkir_asal_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_layout);
        g_ongkir_tujuan_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_layout);
        g_ongkir_kurir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_kurir_layout);
        g_ongkir_cekongkir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_layout);

        g_ongkir_asaladapter = RajaOngkir.getRajaOngkirCity(KeyboardBOS.this);
        g_ongkir_tujuanadapter = RajaOngkir.getRajaOngkirCity(KeyboardBOS.this);

        //BAGIAN ASAL
        g_actv_ongkir_asal = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_auto_complete_text_view);
        g_actv_ongkir_asal.setAdapter(g_ongkir_asaladapter);
        g_actv_ongkir_asal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    focusedEditText = "g_et_ongkir_asal";
                    typedCharacters.setLength(0);
                }
                else
                {
                    typedCharacters.delete(0, typedCharacters.length());
                }
            }
        });
        g_actv_ongkir_asal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_ongkir_asal";
                showAsalMenu();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_NEXT;
                setKeyboardType();
                return false;
            }
        });
        g_actv_ongkir_asal.addTextChangedListener(new TextWatcher() {

            long delay = 1000; // 1 seconds after user stops typing
            long last_text_edit = 0;
            Handler handler = new Handler();

            int longs = 0;

            private Runnable input_finish_checker = new Runnable() {
                public void run() {
                    if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                        g_actv_ongkir_asal.showDropDown();
                        g_keyboardview.setVisibility(View.INVISIBLE);
                    }
                }
            };


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                g_ongkir_asaladapter.notifyDataSetChanged();
                longs = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.length() > 0) {
                    if(longs != editable.length()){
                        last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);
                    }
                }
            }
        });
        g_actv_ongkir_asal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        g_btn_ongkir_asal_back = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_back_button);
        g_btn_ongkir_asal_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOngkir();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
            }
        });

        //BAGIAN TUJUAN
        g_actv_ongkir_tujuan = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_auto_complete_text_view);
        g_actv_ongkir_tujuan.setAdapter(g_ongkir_tujuanadapter);
        g_actv_ongkir_tujuan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditText = "g_et_ongkir_tujuan";
                    typedCharacters.setLength(0);
                }
                else
                {
                    typedCharacters.delete(0, typedCharacters.length());
                }
            }
        });
        g_actv_ongkir_tujuan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_ongkir_tujuan";
                showTujuanMenu();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_NEXT;
                setKeyboardType();
                return false;
            }
        });
        g_actv_ongkir_tujuan.addTextChangedListener(new TextWatcher() {

            long delay = 1000; // 1 seconds after user stops typing
            long last_text_edit = 0;
            Handler handler = new Handler();

            int longs = 0;

            private Runnable input_finish_checker = new Runnable() {
                public void run() {
                    if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                        g_actv_ongkir_tujuan.showDropDown();
                        g_keyboardview.setVisibility(View.INVISIBLE);
                    }
                }
            };

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                g_ongkir_tujuanadapter.notifyDataSetChanged();
                longs = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if(longs != editable.length()){
                        last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);
                    }
                }
            }
        });
        g_actv_ongkir_tujuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        g_btn_ongkir_tujuan_back = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_back_button);
        g_btn_ongkir_tujuan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOngkir();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
            }
        });

        //BAGIAN BERAT
        g_et_ongkir_berat = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_text);
        g_et_ongkir_berat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tmp_added_number = g_et_ongkir_berat.getText().toString();
                if (tmp_added_number.length() != 0) {
                    int number  = Integer.parseInt(tmp_added_number);

                    if (number > 30000){
                        g_et_ongkir_berat.setText("30000");
                        Toast.makeText(getApplicationContext(), "Maksimal berat 30.000 gram", Toast.LENGTH_SHORT).show();
                    } if (number < 1){
                        g_et_ongkir_berat.setText("");
                    }

                }

            }
        });
        g_et_ongkir_berat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditText = "g_et_ongkir_berat";
                    typedCharacters.setLength(0);
                }
                else
                {
                    typedCharacters.delete(0, typedCharacters.length());
                }
            }
        });
        g_et_ongkir_berat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_ongkir_berat";
                showBeratMenu();
                g_keyboardview.setKeyboard(g_keyboard_number);
                return false;
            }
        });
        g_btn_ongkir_berat_back = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_back_button);
        g_btn_ongkir_berat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOngkir();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
            }
        });

        //BAGIAN KURIR
        g_btn_ongkir_jne = g_viewparent.findViewById(R.id.jneButton);
        g_btn_ongkir_jne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!g_ongkir_kurir_jne){
                    g_ongkir_kurir_jne = true;
                    g_btn_ongkir_jne.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
                }else {
                    g_ongkir_kurir_jne = false;
                    g_btn_ongkir_jne.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
                }
            }
        });

        g_btn_ongkir_tiki = g_viewparent.findViewById(R.id.tikiButton);
        g_btn_ongkir_tiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!g_ongkir_kurir_tiki){
                    g_ongkir_kurir_tiki = true;
                    g_btn_ongkir_tiki.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
                }else {
                    g_ongkir_kurir_tiki = false;
                    g_btn_ongkir_tiki.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
                }
            }
        });

        g_btn_ongkir_pos = g_viewparent.findViewById(R.id.posButton);
        g_btn_ongkir_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!g_ongkir_kurir_pos){
                    g_ongkir_kurir_pos = true;
                    g_btn_ongkir_pos.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
                }else {
                    g_ongkir_kurir_pos = false;
                    g_btn_ongkir_pos.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
                }
            }
        });

        g_btn_ongkir_kurir_back = g_viewparent.findViewById(R.id.bcabos_ongkir_kurir_back_button);
        g_btn_ongkir_kurir_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOngkir();
            }
        });

        //BAGIAN BUTTON
        g_btn_ongkir_back = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_back_button);
        g_btn_ongkir_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedEditText = "g_et_external";
                emptyOngkirEditText();
                showFeatureMenu();
            }
        });

        g_btn_ongkir_cekongkir = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_check_button);
        g_btn_ongkir_cekongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAsalCityId();
                getTujuanCityId();
                g_ongkir_berat = g_et_ongkir_berat.getText().toString();
                focusedEditText = "g_et_external";
                getOngkirByCourier();
                showOngkir();

            }
        });

        g_btn_ongkir_refresh = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_refresh_button);
        g_btn_ongkir_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyOngkirEditText();
            }
        });

    }

    private void getOngkirByCourier() {
        if (g_ongkir_kurir_jne){
            RajaOngkir.getRajaOngkirCost(KeyboardBOS.this, g_ongkir_asal, g_ongkir_tujuan, g_ongkir_berat, "jne", KeyboardBOS.this);
        }if (g_ongkir_kurir_tiki){
            RajaOngkir.getRajaOngkirCost(KeyboardBOS.this, g_ongkir_asal, g_ongkir_tujuan, g_ongkir_berat, "tiki", KeyboardBOS.this);
        }if (g_ongkir_kurir_pos){
            RajaOngkir.getRajaOngkirCost(KeyboardBOS.this, g_ongkir_asal, g_ongkir_tujuan, g_ongkir_berat, "pos", KeyboardBOS.this);
        }if (!g_ongkir_kurir_jne && !g_ongkir_kurir_tiki && !g_ongkir_kurir_pos){
            commitTextToBOSKeyboardEditTextWoy("Masukan kurir terlebih dahulu");
        }
    }

    private void getKirimFormNextByCourier() {
        if (g_kirimform_next_kurir_jne){
            RajaOngkir.getRajaOngkirCost(KeyboardBOS.this, g_kirimform_next_asal, g_kirimform_next_tujuan, g_kirimform_next_berat, "jne", KeyboardBOS.this);
        }if (g_kirimform_next_kurir_tiki){
            RajaOngkir.getRajaOngkirCost(KeyboardBOS.this, g_kirimform_next_asal, g_kirimform_next_tujuan, g_kirimform_next_berat, "tiki", KeyboardBOS.this);
        }if (g_kirimform_next_kurir_pos){
            RajaOngkir.getRajaOngkirCost(KeyboardBOS.this, g_kirimform_next_asal, g_kirimform_next_tujuan, g_kirimform_next_berat, "pos", KeyboardBOS.this);
        }if (!g_kirimform_next_kurir_jne && !g_kirimform_next_kurir_tiki && !g_kirimform_next_kurir_pos){
            commitTextToBOSKeyboardEditTextWoy("Masukan kurir terlebih dahulu");
        }
    }

    private void initiateStok(){
        g_stok_layout = g_viewparent.findViewById(R.id.bcabos_stok_layout);
        g_stok_search_layout = g_viewparent.findViewById(R.id.bcabos_stok_search_layout);
        g_stok_produk_layout = g_viewparent.findViewById(R.id.bcabos_stok_produk_layout);
        g_rv_stok = g_viewparent.findViewById(R.id.bcabos_stok_recyclerview);

        g_stok_item_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        g_stok_adapter = new StokProdukAdapter();
        g_stok_adapter.setParentOnCallBack(this);

        g_rv_stok.setLayoutManager(g_stok_item_layout);
        g_rv_stok.setAdapter(g_stok_adapter);

        //BAGIAN SEARCH
        g_et_stok_search = g_viewparent.findViewById(R.id.bcabos_stok_search_edittext);
        g_et_stok_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                g_et_stok_keyboard_fokus = hasFocus;
                if (hasFocus) {
                    focusedEditText = "g_et_stok_search";
                    typedCharacters.setLength(0);
                }
            }
        });
        g_et_stok_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showStokSearch();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                return false;
            }
        });

        //BAGIAN BACK
        g_btn_stok_back = g_viewparent.findViewById(R.id.bcabos_stok_search_back_button);
        g_btn_stok_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedEditText = "g_et_external";
                if(g_et_stok_keyboard_fokus)
                    showStok();
                else
                    showFeatureMenu();
            }
        });
    }

    private void initiateKirimForm(){
        g_kirimform_produk_total = 0;
        g_kirimform_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_layout);
        g_kirimform_search_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_search_layout);
        g_kirimform_produk_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_layout);
        g_kirimform_produk_button_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_button_layout);
        g_rv_kirimform_produk = g_viewparent.findViewById(R.id.bcabos_kirimform_recyclerview);

        g_kirimform_produk_item_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        g_kirimform_produk_adapter = new KirimFormProdukAdapter();
        g_kirimform_produk_adapter.setParentOnCallBack(this);

        g_rv_kirimform_produk.setLayoutManager(g_kirimform_produk_item_layout);
        g_rv_kirimform_produk.setAdapter(g_kirimform_produk_adapter);

        //BAGIAN SEARCH
        g_et_kirimform_search = g_viewparent.findViewById(R.id.bcabos_kirimform_search_edittext);
        g_et_kirimform_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                g_et_kirimform_keyboard_fokus = hasFocus;
                if (hasFocus) {
                    focusedEditText = "g_et_kirimform_search";
                    typedCharacters.setLength(0);
                }
            }
        });
        g_et_kirimform_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showKirimFormSearch();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
                return false;
            }
        });

//        BAGIAN BACK & NEXT
        g_btn_kirimform_back = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_button_back_button);
        g_btn_kirimform_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedEditText = "g_et_external";
                if(g_et_kirimform_keyboard_fokus)
                    showKirimForm();
                else
                    showFeatureMenu();
            }
        });

        g_btn_kirimform_next = g_viewparent.findViewById(R.id.bcabos_kirimform_produk_button_next_button);
        g_btn_kirimform_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKirimFormNext();
            }
        });
    }

    private void initiateKirimFormNext(){
        g_kirimform_next_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_layout);
        g_kirimform_next_berat_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_berat_layout);
        g_kirimform_next_asal_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_asal_layout);
        g_kirimform_next_tujuan_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_tujuan_layout);
        g_kirimform_next_kirim_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_kirim_layout);
        g_kirimform_next_kurir_layout = g_viewparent.findViewById(R.id.bcabos_kirimform_next_kurir_layout);

        g_kirimform_next_asaladapter = RajaOngkir.getRajaOngkirCity(KeyboardBOS.this);
        g_kirimform_next_tujuanadapter = RajaOngkir.getRajaOngkirCity(KeyboardBOS.this);

        //BAGIAN ASAL
        g_actv_kirimform_next_asal = g_viewparent.findViewById(R.id.bcabos_kirimform_next_asal_auto_complete_text_view);
        g_actv_kirimform_next_asal.setAdapter(g_kirimform_next_asaladapter);
        g_actv_kirimform_next_asal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    focusedEditText = "g_et_kirimform_next_asal";
                    typedCharacters.setLength(0);
                    typedCharacters.delete(0, typedCharacters.length());
                }
            }
        });
        g_actv_kirimform_next_asal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_kirimform_next_asal";
                showAsalKirimFormNext();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_NEXT;
                setKeyboardType();
                return false;
            }
        });
        g_actv_kirimform_next_asal.addTextChangedListener(new TextWatcher() {

            long delay = 1000; // 1 seconds after user stops typing
            long last_text_edit = 0;
            Handler handler = new Handler();

            int longs = 0;

            private Runnable input_finish_checker = new Runnable() {
                public void run() {
                    if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                        g_actv_kirimform_next_asal.showDropDown();
                        g_keyboardview.setVisibility(View.INVISIBLE);
                    }
                }
            };

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                g_kirimform_next_asaladapter.notifyDataSetChanged();
                longs = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.length() > 0) {
                    if(longs != editable.length()){
                        last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);
                    }
                }
            }
        });
        g_actv_kirimform_next_asal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        g_btn_kirimform_next_asal_back = g_viewparent.findViewById(R.id.bcabos_kirimform_next_asal_back_button);
        g_btn_kirimform_next_asal_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKirimFormNext();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
            }
        });

        //BAGIAN TUJUAN
        g_actv_kirimform_next_tujuan = g_viewparent.findViewById(R.id.bcabos_kirimform_next_tujuan_auto_complete_text_view);
        g_actv_kirimform_next_tujuan.setAdapter(g_ongkir_tujuanadapter);
        g_actv_kirimform_next_tujuan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditText = "g_et_kirimform_next_tujuan";
                    typedCharacters.setLength(0);
                }
            }
        });
        g_actv_kirimform_next_tujuan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_kirimform_next_tujuan";
                showTujuanKirimFormNext();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_NEXT;
                setKeyboardType();
                return false;
            }
        });
        g_actv_kirimform_next_tujuan.addTextChangedListener(new TextWatcher() {

            long delay = 1000; // 1 seconds after user stops typing
            long last_text_edit = 0;
            Handler handler = new Handler();

            int longs = 0;

            private Runnable input_finish_checker = new Runnable() {
                public void run() {
                    if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                        g_actv_kirimform_next_tujuan.showDropDown();
                        g_keyboardview.setVisibility(View.INVISIBLE);
                    }
                }
            };

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                g_kirimform_next_tujuanadapter.notifyDataSetChanged();
                longs = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if(longs != editable.length()){
                        last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);
                    }
                }
            }
        });
        g_actv_kirimform_next_tujuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                g_keyboardview.setVisibility(View.VISIBLE);
            }
        });

        g_btn_kirimform_next_tujuan_back = g_viewparent.findViewById(R.id.bcabos_kirimform_next_tujuan_back_button);
        g_btn_kirimform_next_tujuan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKirimFormNext();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
            }
        });

        //BAGIAN BERAT
        g_et_kirimform_next_berat = g_viewparent.findViewById(R.id.bcabos_kirimform_next_berat_text);
        g_et_kirimform_next_berat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditText = "g_et_kirimform_next_berat";
                    typedCharacters.setLength(0);
                    typedCharacters.delete(0, typedCharacters.length());
                }
            }
        });
        g_et_kirimform_next_berat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_kirimform_next_berat";
                showBeratKirimFormNext();
                g_keyboardview.setKeyboard(g_keyboard_number);
                return false;
            }
        });
        g_btn_kirimform_next_berat_back = g_viewparent.findViewById(R.id.bcabos_kirimform_next_berat_back_button);
        g_btn_kirimform_next_berat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKirimFormNext();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_DEFAULT;
                setKeyboardType();
            }
        });

        //BAGIAN KURIR
        g_btn_kirimform_next_jne = g_viewparent.findViewById(R.id.bcabos_kirimform_next_jne_btn);
        g_btn_kirimform_next_jne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelectedCourier();
                g_kirimform_next_kurir_jne = true;
                g_btn_kirimform_next_jne.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
            }
        });

        g_btn_kirimform_next_tiki = g_viewparent.findViewById(R.id.bcabos_kirimform_next_tiki_btn);
        g_btn_kirimform_next_tiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            clearSelectedCourier();
            g_kirimform_next_kurir_tiki = true;
            g_btn_kirimform_next_tiki.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
            }
        });

        g_btn_kirimform_next_pos = g_viewparent.findViewById(R.id.bcabos_kirimform_next_pos_btn);
        g_btn_kirimform_next_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            clearSelectedCourier();
            g_kirimform_next_kurir_pos = true;
            g_btn_kirimform_next_pos.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
            }
        });

        g_btn_kirimform_next_kurir_back = g_viewparent.findViewById(R.id.bcabos_kirimform_next_kurir_back_button);
        g_btn_kirimform_next_kurir_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKirimFormNext();
            }
        });

        //BAGIAN BUTTON
        g_btn_kirimform_next_back = g_viewparent.findViewById(R.id.bcabos_kirimform_next_back_button);
        g_btn_kirimform_next_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedEditText = "g_et_external";
                emptyKirimFormNextEditText();
                showKirimForm();
            }
        });

        g_btn_kirimform_next_kirim = g_viewparent.findViewById(R.id.bcabos_kirimform_next_kirim_button);
        g_btn_kirimform_next_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAsalCityIdKirim();
                getTujuanCityIdKirim();
                g_kirimform_next_berat = g_et_kirimform_next_berat.getText().toString();
                focusedEditText = "g_et_external";
                getKirimFormNextByCourier();;
                emptyKirimFormNextEditText();;
                showKirimForm();

            }
        });

    }

    private void clearSelectedCourier(){
        g_kirimform_next_kurir_jne = false;
        g_kirimform_next_kurir_pos = false;
        g_kirimform_next_kurir_tiki = false;
        g_btn_kirimform_next_tiki.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        g_btn_kirimform_next_jne.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        g_btn_kirimform_next_pos.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
    }

    private void initiateMutasi() {
        g_mutasi_layout = g_viewparent.findViewById(R.id.bcabos_extended_mutasi_layout);

        //Recyclerview Mutasi Rekening
        g_mutasi_recyclerview = g_viewparent.findViewById(R.id.bcabos_extended_mutasi_recyclerview);
        g_mutasi_item_layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        MutasiRekeningAdapter mra = new MutasiRekeningAdapter();

        g_mutasi_recyclerview.setLayoutManager(g_mutasi_item_layout);
        g_mutasi_recyclerview.setAdapter(mra);

        g_btn_mutasi_back = g_viewparent.findViewById(R.id.bcabos_mutasi_back_button);
        g_btn_mutasi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFeatureMenu();
            }
        });
    }

    //region SHOW FUNCTION
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

    private void showOngkir() {
        refreshDisplay();
        int tmpheightdp = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 283, getResources().getDisplayMetrics() );
        g_ongkir_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tmpheightdp));
        g_keyboardview.setVisibility(View.GONE);
        g_btn_ongkir_berat_back.setVisibility(View.GONE);
        g_btn_ongkir_asal_back.setVisibility(View.GONE);
        g_btn_ongkir_tujuan_back.setVisibility(View.GONE);
        g_btn_ongkir_kurir_back.setVisibility(View.GONE);
        g_ongkir_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showBeratMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_asal_layout.setVisibility(View.GONE);
        g_ongkir_tujuan_layout.setVisibility(View.GONE);
        g_ongkir_kurir_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showAsalMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_berat_layout.setVisibility(View.GONE);
        g_ongkir_tujuan_layout.setVisibility(View.GONE);
        g_ongkir_kurir_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showTujuanMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_berat_layout.setVisibility(View.GONE);
        g_ongkir_asal_layout.setVisibility(View.GONE);
        g_ongkir_kurir_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showKurirMenu() {
        refreshDisplay();
        g_ongkir_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_berat_layout.setVisibility(View.GONE);
        g_ongkir_asal_layout.setVisibility(View.GONE);
        g_ongkir_tujuan_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showStok() {
        refreshDisplay();
        int tmpheightdp = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 283, getResources().getDisplayMetrics() );
        g_stok_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tmpheightdp));
        g_keyboardview.setVisibility(View.GONE);
        g_stok_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showStokSearch() {
        refreshDisplay();
        g_stok_layout.setVisibility(View.VISIBLE);
        g_stok_produk_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showKirimForm() {
        refreshDisplay();
        int tmpheightdp = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 283, getResources().getDisplayMetrics() );
        g_kirimform_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tmpheightdp));
        g_keyboardview.setVisibility(View.GONE);
        g_kirimform_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showKirimFormSearch() {
        refreshDisplay();
        g_kirimform_layout.setVisibility(View.VISIBLE);
        g_kirimform_produk_layout.setVisibility(View.GONE);
        g_kirimform_produk_button_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showKirimFormNext() {
        refreshDisplay();
        g_keyboardview.setVisibility(View.GONE);
        g_btn_kirimform_next_berat_back.setVisibility(View.GONE);
        g_btn_kirimform_next_asal_back.setVisibility(View.GONE);
        g_btn_kirimform_next_tujuan_back.setVisibility(View.GONE);
        g_btn_kirimform_next_kurir_back.setVisibility(View.GONE);
        g_kirimform_next_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showBeratKirimFormNext() {
        refreshDisplay();
        g_kirimform_next_layout.setVisibility(View.VISIBLE);
        g_kirimform_next_asal_layout.setVisibility(View.GONE);
        g_kirimform_next_tujuan_layout.setVisibility(View.GONE);
        g_kirimform_next_kurir_layout.setVisibility(View.GONE);
        g_kirimform_next_kirim_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showAsalKirimFormNext() {
        refreshDisplay();
        g_kirimform_next_layout.setVisibility(View.VISIBLE);
        g_kirimform_next_berat_layout.setVisibility(View.GONE);
        g_kirimform_next_tujuan_layout.setVisibility(View.GONE);
        g_kirimform_next_kurir_layout.setVisibility(View.GONE);
        g_kirimform_next_kirim_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showTujuanKirimFormNext() {
        refreshDisplay();
        g_kirimform_next_layout.setVisibility(View.VISIBLE);
        g_kirimform_next_berat_layout.setVisibility(View.GONE);
        g_kirimform_next_asal_layout.setVisibility(View.GONE);
        g_kirimform_next_kurir_layout.setVisibility(View.GONE);
        g_kirimform_next_kirim_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showKurirKirimFormNext() {
        refreshDisplay();
        g_kirimform_next_layout.setVisibility(View.VISIBLE);
        g_kirimform_next_berat_layout.setVisibility(View.GONE);
        g_kirimform_next_asal_layout.setVisibility(View.GONE);
        g_kirimform_next_tujuan_layout.setVisibility(View.GONE);
        g_kirimform_next_kirim_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showMutasi() {
        refreshDisplay();
        int tmpheightdp = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 283, getResources().getDisplayMetrics() );
        g_mutasi_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tmpheightdp));
        g_mutasi_layout.setVisibility(View.VISIBLE);
        g_keyboardview.setVisibility(View.GONE);
        changeLayoutStatus(true);
    }

    private void refreshDisplay(){
        //keyboard
        g_keyboardview.setVisibility(View.VISIBLE);

        //parent layout
        g_home_layout.setVisibility(View.GONE);
        g_feature_layout.setVisibility(View.GONE);
        g_ongkir_layout.setVisibility(View.GONE);
        g_stok_layout.setVisibility(View.GONE);
        g_mutasi_layout.setVisibility(View.GONE);
        g_kirimform_layout.setVisibility(View.GONE);
        g_kirimform_next_layout.setVisibility(View.GONE);

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
        g_kirimform_next_berat_layout.setVisibility(View.VISIBLE);
        g_btn_kirimform_next_berat_back.setVisibility(View.VISIBLE);
        g_kirimform_next_asal_layout.setVisibility(View.VISIBLE);
        g_btn_kirimform_next_asal_back.setVisibility(View.VISIBLE);
        g_kirimform_next_tujuan_layout.setVisibility(View.VISIBLE);
        g_btn_kirimform_next_tujuan_back.setVisibility(View.VISIBLE);
        g_kirimform_next_kurir_layout.setVisibility(View.VISIBLE);
        g_btn_kirimform_next_kurir_back.setVisibility(View.VISIBLE);
        g_kirimform_next_kirim_layout.setVisibility(View.VISIBLE);

        //inside mutasi rekening
    }

    //endregion
    private void changeLayoutStatus(Boolean homeStatus){
        this.isInputConnectionExternalBOSKeyboard = homeStatus;
    }

    //Menghapus isi editText
    private void emptyOngkirEditText() {
        g_et_ongkir_berat.setText("");
        g_actv_ongkir_asal.setText("");
        g_actv_ongkir_tujuan.setText("");
        g_ongkir_kurir_jne = false;
        g_btn_ongkir_jne.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        g_ongkir_kurir_tiki = false;
        g_btn_ongkir_tiki.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        g_ongkir_kurir_pos = false;
        g_btn_ongkir_pos.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
    }

    private void emptyKirimFormNextEditText() {
        g_et_kirimform_next_berat.setText("");
        g_actv_kirimform_next_asal.setText("");
        g_actv_kirimform_next_tujuan.setText("");
    }

    //Mendapatkan ID dari City
    private void getAsalCityId(){
        g_ongkir_asal = String.valueOf(RajaOngkir.cityNameList.indexOf(g_actv_ongkir_asal.getText().toString())+1);
    }

    private void getAsalCityIdKirim(){
        g_kirimform_next_asal = String.valueOf(RajaOngkir.cityNameList.indexOf(g_actv_kirimform_next_asal.getText().toString())+1);
    }

    private void getTujuanCityId(){
        g_ongkir_tujuan = String.valueOf(RajaOngkir.cityNameList.indexOf(g_actv_ongkir_tujuan.getText().toString())+1);
    }

    private void getTujuanCityIdKirim(){
        g_kirimform_next_tujuan = String.valueOf(RajaOngkir.cityNameList.indexOf(g_actv_kirimform_next_tujuan.getText().toString())+1);
    }

    @Override
    public void onPress(int i) {
        //Ganti keyboard
//        char code = (char) i;
//        if(code == ' '){
//            InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
//            imeManager.showInputMethodPicker();
//        }
    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        final InputConnection l_inputconnection = getCurrentInputConnection();  //getCurrentInputConnection digunakan untuk mendapatkan koneksi ke bidang input aplikasi lain.
        CharSequence selectedText = l_inputconnection.getSelectedText(0);
        playClick(i);
        switch (i){
            case KEYCODE_CHANGE_NUMBER_SYMBOL:
                IS_SYMBOL1 = !IS_SYMBOL1;
                setKeyboardType();
                break;
            case Keyboard.KEYCODE_DONE:
                switch (focusedEditText){
                    case "g_et_ongkir_asal":
                        focusedEditText = "g_et_ongkir_tujuan";
                        showTujuanMenu();
                        KEYCODE_DONE_TYPE = KEY_NEXT;
                        setKeyboardType();
                        break;
                    case "g_et_ongkir_tujuan":
                        focusedEditText = "g_et_ongkir_berat";
                        showBeratMenu();
                        KEYCODE_DONE_TYPE = KEY_NEXT;
                        setKeyboardType();
                        break;
                    case "g_et_ongkir_berat":
                        focusedEditText = "g_et_ongkir_kurir";
                        showKurirMenu();
                        KEYCODE_DONE_TYPE = KEY_OK;
                        setKeyboardType();
                        break;
                    case "g_et_ongkir_kurir":
                        focusedEditText = "g_et_external";
                        showOngkir();
                        KEYCODE_DONE_TYPE = KEY_DEFAULT;
                        setKeyboardType();
                        break;
                    case "g_et_kirimform_next_asal":
                        focusedEditText = "g_et_kirimform_next_tujuan";
                        showTujuanKirimFormNext();
                        KEYCODE_DONE_TYPE = KEY_NEXT;
                        setKeyboardType();
                        break;
                    case "g_et_kirimform_next_tujuan":
                        focusedEditText = "g_et_kirimform_next_berat";
                        showBeratKirimFormNext();
                        KEYCODE_DONE_TYPE = KEY_NEXT;
                        setKeyboardType();
                        break;
                    case "g_et_kirimform_next_berat":
                        focusedEditText = "g_et_kirimform_next_kurir";
                        showKurirKirimFormNext();
                        KEYCODE_DONE_TYPE = KEY_OK;
                        setKeyboardType();
                        break;
                    case "g_et_kirimform_next_kurir":
                        focusedEditText = "g_et_external";
                        showKirimFormNext();
                        KEYCODE_DONE_TYPE = KEY_DEFAULT;
                        setKeyboardType();
                        break;
                    default:
                        l_inputconnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                        KEYCODE_DONE_TYPE = KEY_DEFAULT;
                        setKeyboardType();
                }

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
                char code = (char) i;
                if(Character.isLetter(code) && IS_CAPS){
                    code = Character.toUpperCase(code);
                }
                commitTextToBOSKeyboardEditText(String.valueOf(code));

        }

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

    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {
        Toast.makeText(this, "TEST COY", Toast.LENGTH_SHORT).show();
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

    public void commitTextToBOSKeyboardEditTextWoy(String character){
        typedCharacters.append(character);

        switch (focusedEditText) {
            case "g_et_ongkir_asal":
                g_actv_ongkir_asal.setText(typedCharacters);
                g_actv_ongkir_asal.setSelection(g_actv_ongkir_asal.getText().length());
                break;
            case "g_et_ongkir_tujuan":
                g_actv_ongkir_tujuan.setText(typedCharacters);
                g_actv_ongkir_tujuan.setSelection(g_actv_ongkir_tujuan.getText().length());
                break;
            case "g_et_ongkir_berat":
                g_et_ongkir_berat.setText(typedCharacters);
                g_et_ongkir_berat.setSelection(g_et_ongkir_berat.getText().length());
                break;
            case "g_et_stok_search":
                g_et_stok_search.setText(typedCharacters);
                g_et_stok_search.setSelection(g_et_stok_search.getText().length());
                break;
            case "g_et_kirimform_search":
                g_et_kirimform_search.setText(typedCharacters);
                g_et_kirimform_search.setSelection(g_et_kirimform_search.getText().length());
                break;
            case "g_et_kirimform_next_asal":
                g_actv_kirimform_next_asal.setText(typedCharacters);
                g_actv_kirimform_next_asal.setSelection(g_actv_kirimform_next_asal.getText().length());
                break;
            case "g_et_kirimform_next_tujuan":
                g_actv_kirimform_next_tujuan.setText(typedCharacters);
                g_actv_kirimform_next_tujuan.setSelection(g_actv_kirimform_next_tujuan.getText().length());
                break;
            case "g_et_kirimform_next_berat":
                g_et_kirimform_next_berat.setText(typedCharacters);
                g_et_kirimform_next_berat.setSelection(g_et_kirimform_next_berat.getText().length());
                break;
            default:
                InputConnection inputConnection = getCurrentInputConnection();
                inputConnection.commitText(character, 1);
                break;
        }

    }

    private void commitTextToBOSKeyboardEditText(String character){
        typedCharacters.append(character);

        switch (focusedEditText) {
            case "g_et_ongkir_asal":
                g_actv_ongkir_asal.setText(typedCharacters);
                g_actv_ongkir_asal.setSelection(g_actv_ongkir_asal.getText().length());
                break;
            case "g_et_ongkir_tujuan":
                g_actv_ongkir_tujuan.setText(typedCharacters);
                g_actv_ongkir_tujuan.setSelection(g_actv_ongkir_tujuan.getText().length());
                break;
            case "g_et_ongkir_berat":
                g_et_ongkir_berat.setText(typedCharacters);
                g_et_ongkir_berat.setSelection(g_et_ongkir_berat.getText().length());
                break;
            case "g_et_stok_search":
                g_et_stok_search.setText(typedCharacters);
                g_et_stok_search.setSelection(g_et_stok_search.getText().length());
                break;
            case "g_et_kirimform_search":
                g_et_kirimform_search.setText(typedCharacters);
                g_et_kirimform_search.setSelection(g_et_kirimform_search.getText().length());
                break;
            case "g_et_kirimform_next_asal":
                g_actv_kirimform_next_asal.setText(typedCharacters);
                g_actv_kirimform_next_asal.setSelection(g_actv_kirimform_next_asal.getText().length());
                break;
            case "g_et_kirimform_next_tujuan":
                g_actv_kirimform_next_tujuan.setText(typedCharacters);
                g_actv_kirimform_next_tujuan.setSelection(g_actv_kirimform_next_tujuan.getText().length());
                break;
            case "g_et_kirimform_next_berat":
                g_et_kirimform_next_berat.setText(typedCharacters);
                g_et_kirimform_next_berat.setSelection(g_et_kirimform_next_berat.getText().length());
                break;
            default:
                InputConnection inputConnection = getCurrentInputConnection();
                inputConnection.commitText(character, 1);
                break;
        }

    }

    private void deleteKeyPressed(InputConnection ic, CharSequence selectedText){
        if(isInputConnectionExternalBOSKeyboard){
            if(TextUtils.isEmpty(selectedText)) {
                ic.deleteSurroundingText(1, 0);
            } else {
                ic.commitText("", 1);
            }
        } else if(!isInputConnectionExternalBOSKeyboard){
            switch (focusedEditText) {
                case "g_et_ongkir_berat":
                    int etBeratLength = g_et_ongkir_berat.getText().length();
                    if (etBeratLength > 0) {
                        g_et_ongkir_berat.getText().delete(etBeratLength - 1, etBeratLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etBeratLength - 1);
                        }
                    }
                    g_et_ongkir_berat.setSelection(g_et_ongkir_berat.getText().length());
                    break;
                case "g_et_ongkir_asal":
                    int etAsalLength = g_actv_ongkir_asal.getText().length();
                    if (etAsalLength > 0) {
                        g_actv_ongkir_asal.getText().delete(etAsalLength - 1, etAsalLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etAsalLength - 1);
                        }
                    }
                    g_actv_ongkir_asal.setSelection(g_actv_ongkir_asal.getText().length());
                    break;
                case "g_et_ongkir_tujuan":
                    int etTujuanLength = g_actv_ongkir_tujuan.getText().length();
                    if (etTujuanLength > 0) {
                        g_actv_ongkir_tujuan.getText().delete(etTujuanLength - 1, etTujuanLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etTujuanLength - 1);
                        }
                    }
                    g_actv_ongkir_tujuan.setSelection(g_actv_ongkir_tujuan.getText().length());
                    break;
                case "g_et_stok_search":
                    int etStokLength = g_et_stok_search.getText().length();
                    if (etStokLength > 0) {
                        g_et_stok_search.getText().delete(etStokLength - 1, etStokLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etStokLength - 1);
                        }
                    }
                    g_et_stok_search.setSelection(g_et_stok_search.getText().length());
                    break;
                case "g_et_kirimform_search":
                    int etKirimFormSearchLength = g_et_kirimform_search.getText().length();
                    if (etKirimFormSearchLength > 0) {
                        g_et_kirimform_search.getText().delete(etKirimFormSearchLength - 1, etKirimFormSearchLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etKirimFormSearchLength - 1);
                        }
                    }
                    g_et_kirimform_search.setSelection(g_et_kirimform_search.getText().length());
                    break;
                case "g_et_kirimform_next_asal":
                    int etAsalNextLength = g_actv_kirimform_next_asal.getText().length();
                    if (etAsalNextLength > 0) {
                        g_actv_kirimform_next_asal.getText().delete(etAsalNextLength - 1, etAsalNextLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etAsalNextLength - 1);
                        }
                    }
                    g_actv_kirimform_next_asal.setSelection(g_actv_kirimform_next_asal.getText().length());
                    break;
                case "g_et_kirimform_next_tujuan":
                    int etTujuanNextLength = g_actv_kirimform_next_tujuan.getText().length();
                    if (etTujuanNextLength > 0) {
                        g_actv_kirimform_next_tujuan.getText().delete(etTujuanNextLength - 1, etTujuanNextLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etTujuanNextLength - 1);
                        }
                    }
                    g_actv_kirimform_next_tujuan.setSelection(g_actv_kirimform_next_tujuan.getText().length());
                    break;
                case "g_et_kirimform_next_berat":
                    int etBeratNextLength = g_et_kirimform_next_berat.getText().length();
                    if (etBeratNextLength > 0) {
                        g_et_kirimform_next_berat.getText().delete(etBeratNextLength - 1, etBeratNextLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etBeratNextLength - 1);
                        }
                    }
                    g_et_kirimform_next_berat.setSelection(g_et_kirimform_next_berat.getText().length());
                    break;
            }
        }
    }

    @Override
    public void OnCallBack(Object p_obj) {

        if(p_obj instanceof Produk){
            Produk tmpProduk = (Produk) p_obj;
            if(tmpProduk.getId() == -1){
                Intent tmpIntent = new Intent(KeyboardBOS.this, ApplicationContainer.class);
                tmpIntent.putExtra(ApplicationContainer.KEY_OPEN_APPS, ApplicationContainer.ID_PRODUK);
                tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tmpIntent);
            }
            else {
                if(tmpProduk.getStok() <= 0){
                    commitTextToBOSKeyboardEditText("Maaf, stok kami untuk produk tersebut sudah habis. \nTerima kasih.");
                }else{
                    commitTextToBOSKeyboardEditText("Stok kami untuk produk tersebut masih ada. \nTerima kasih.");
                }
            }
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

}