package com.example.bca_bos;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.adapters.StokProdukAdapter;

import com.example.bca_bos.adapters.TemplatedTextAdapter;
import com.example.bca_bos.interfaces.OnCallBackListener;

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
    private ImageButton g_btn_home;
    private RecyclerView g_templatedtext_recyclerview;
    private LinearLayoutManager g_linear_layout;

    //FEATURE
    private LinearLayout g_feature_layout;
    private ImageButton g_btn_feature_back;
    private Button g_btn_feature_ongkir;
    private Button g_btn_feature_stok;

    //ONGKIR
    private LinearLayout g_ongkir_layout, g_ongkir_berat_layout, g_ongkir_asal_layout, g_ongkir_tujuan_layout, g_ongkir_cekongkir_layout;
    private ImageButton g_btn_ongkir_berat_back,g_btn_ongkir_asal_back, g_btn_ongkir_tujuan_back, g_btn_ongkir_back;
    private EditText g_et_ongkir_berat, g_et_ongkir_asal, g_et_ongkir_tujuan;
    private Button g_btn_ongkir_cekongkir;

    //STOK
    private LinearLayout g_stok_layout, g_stok_search_layout, g_stok_produk_layout;

    private RecyclerView g_rv_stok;
    private LinearLayoutManager g_stok_item_layout;
    private StokProdukAdapter g_stok_adapter;

    private ImageButton g_btn_stok_back;
    private EditText g_et_stok_search;

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
        } catch (Exception ex){
            Log.i("EHS", ex.toString());
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
    }

    private void initiateOngkir(){
        g_ongkir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_layout);
        g_ongkir_berat_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_layout);
        g_ongkir_asal_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_layout);
        g_ongkir_tujuan_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_layout);
        g_ongkir_cekongkir_layout = g_viewparent.findViewById(R.id.bcabos_ongkir_cekongkir_layout);

        //BAGIAN BERAT
        g_et_ongkir_berat = g_viewparent.findViewById(R.id.bcabos_ongkir_berat_text);
        g_et_ongkir_berat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditText = "g_et_ongkir_berat";
                    typedCharacters.setLength(0);
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

        //BAGIAN ASAL
        g_et_ongkir_asal = g_viewparent.findViewById(R.id.bcabos_ongkir_asal_text);
        g_et_ongkir_asal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    focusedEditText = "g_et_ongkir_asal";
                    typedCharacters.setLength(0);
                }
            }
        });
        g_et_ongkir_asal.setOnTouchListener(new View.OnTouchListener() {
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
        g_et_ongkir_tujuan = g_viewparent.findViewById(R.id.bcabos_ongkir_tujuan_text);
        g_et_ongkir_tujuan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    focusedEditText = "g_et_ongkir_tujuan";
                    typedCharacters.setLength(0);
                }
            }
        });
        g_et_ongkir_tujuan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedEditText = "g_et_ongkir_tujuan";
                showTujuanMenu();
                IS_ALPHABET = true;
                KEYCODE_DONE_TYPE = KEY_OK;
                setKeyboardType();
                return false;
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
                emptyOngkirEditText();
            }
        });

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
        g_keyboardview.setVisibility(View.GONE);
        g_btn_ongkir_berat_back.setVisibility(View.GONE);
        g_btn_ongkir_asal_back.setVisibility(View.GONE);
        g_btn_ongkir_tujuan_back.setVisibility(View.GONE);
        g_ongkir_layout.setVisibility(View.VISIBLE);
        changeLayoutStatus(false);
    }

    private void showBeratMenu() {
        refreshDisplay();
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_asal_layout.setVisibility(View.GONE);
        g_ongkir_tujuan_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showAsalMenu() {
        refreshDisplay();
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_berat_layout.setVisibility(View.GONE);
        g_ongkir_tujuan_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showTujuanMenu() {
        refreshDisplay();
        g_ongkir_layout.setVisibility(View.VISIBLE);
        g_ongkir_berat_layout.setVisibility(View.GONE);
        g_ongkir_asal_layout.setVisibility(View.GONE);
        g_ongkir_cekongkir_layout.setVisibility(View.GONE);
        changeLayoutStatus(false);
    }

    private void showStok() {
        refreshDisplay();
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

    private void refreshDisplay(){
        //keyboard
        g_keyboardview.setVisibility(View.VISIBLE);

        //parent layout
        g_home_layout.setVisibility(View.GONE);
        g_feature_layout.setVisibility(View.GONE);
        g_ongkir_layout.setVisibility(View.GONE);
        g_stok_layout.setVisibility(View.GONE);

        //inside ongkir layout
        g_ongkir_berat_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_berat_back.setVisibility(View.VISIBLE);
        g_ongkir_asal_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_asal_back.setVisibility(View.VISIBLE);
        g_ongkir_tujuan_layout.setVisibility(View.VISIBLE);
        g_btn_ongkir_tujuan_back.setVisibility(View.VISIBLE);
        g_ongkir_cekongkir_layout.setVisibility(View.VISIBLE);

        //inside stok layout
        g_stok_produk_layout.setVisibility(View.VISIBLE);
        g_stok_search_layout.setVisibility(View.VISIBLE);
    }

    //endregion
    private void changeLayoutStatus(Boolean homeStatus){
        this.isInputConnectionExternalBOSKeyboard = homeStatus;
    }

    //Menghapus isi editText
    private void emptyOngkirEditText() {
        g_et_ongkir_berat.setText("");
        g_et_ongkir_asal.setText("");
        g_et_ongkir_tujuan.setText("");
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
                    case "g_et_ongkir_berat":
                        focusedEditText = "g_et_ongkir_asal";
                        showAsalMenu();
                        KEYCODE_DONE_TYPE = KEY_NEXT;
                        setKeyboardType();
                        break;
                    case "g_et_ongkir_asal":
                        focusedEditText = "g_et_ongkir_tujuan";
                        showTujuanMenu();
                        KEYCODE_DONE_TYPE = KEY_OK;
                        setKeyboardType();
                        break;
                    case "g_et_ongkir_tujuan":
                        focusedEditText = "g_et_external";
                        showOngkir();
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

    private void commitTextToBOSKeyboardEditText(String character){
        typedCharacters.append(character);

        switch (focusedEditText) {
            case "g_et_ongkir_berat":
                g_et_ongkir_berat.setText(typedCharacters);
                g_et_ongkir_berat.setSelection(g_et_ongkir_berat.getText().length());
                break;
            case "g_et_ongkir_asal":
                g_et_ongkir_asal.setText(typedCharacters);
                g_et_ongkir_asal.setSelection(g_et_ongkir_asal.getText().length());
                break;
            case "g_et_ongkir_tujuan":
                g_et_ongkir_tujuan.setText(typedCharacters);
                g_et_ongkir_tujuan.setSelection(g_et_ongkir_tujuan.getText().length());
                break;
            case "g_et_stok_search":
                g_et_stok_search.setText(typedCharacters);
                g_et_stok_search.setSelection(g_et_stok_search.getText().length());
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
                    int etAsalLength = g_et_ongkir_asal.getText().length();
                    if (etAsalLength > 0) {
                        g_et_ongkir_asal.getText().delete(etAsalLength - 1, etAsalLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etAsalLength - 1);
                        }
                    }
                    g_et_ongkir_asal.setSelection(g_et_ongkir_asal.getText().length());
                    break;
                case "g_et_ongkir_tujuan":
                    int etTujuanLength = g_et_ongkir_tujuan.getText().length();
                    if (etTujuanLength > 0) {
                        g_et_ongkir_tujuan.getText().delete(etTujuanLength - 1, etTujuanLength);
                        if(typedCharacters.length()>0){
                            typedCharacters.deleteCharAt(etTujuanLength - 1);
                        }
                    }
                    g_et_ongkir_tujuan.setSelection(g_et_ongkir_tujuan.getText().length());
                    break;

            }
        }
    }

    @Override
    public void OnCallBack(String p_text) {
        String[] tmpText = p_text.split(";");

        if(tmpText[0].equals("STOK")){
            if(Integer.parseInt(tmpText[1]) <= 0){
                commitTextToBOSKeyboardEditText("Maaf, stok kami untuk produk tersebut sudah habis. \nTerima kasih.");
            }else{
                commitTextToBOSKeyboardEditText("Stok kami untuk produk tersebut masih tersisa : " + Integer.parseInt(tmpText[1]) + " buah. \nTerima kasih.");
            }
        }
        else if(tmpText[0].equals("TEXT")){
            commitTextToBOSKeyboardEditText(tmpText[1]);
        }

    }

}
