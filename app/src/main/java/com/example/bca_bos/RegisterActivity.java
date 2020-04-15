package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bca_bos.networks.NetworkUtil;
import com.example.bca_bos.networks.VolleyClass;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    Button g_register_btn;
    EditText g_register_et_bos_id, g_register_et_nama, g_register_et_no_rekening, g_register_et_no_hp, g_register_et_password, g_register_et_confirm_password, g_register_et_email;
    TextView g_register_tv_bos_id, g_register_tv_nama, g_register_tv_no_rekening, g_register_tv_no_hp, g_register_tv_password, g_register_tv_confirm_password, g_register_tv_email;

    //Error
    TextView g_register_tv_error, g_register_tv_bos_id_error, g_register_tv_nama_error, g_register_tv_no_rek_error, g_register_no_hp_error, g_register_password_error, g_register_confirm_error, g_register_email_error;

    //
    public static RegisterActivity g_instance = null;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String NO_HP = "NO_HP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Connect Internet
        NetworkUtil.disableSSL();
        g_instance = this;

        g_register_btn = findViewById(R.id.register_register_button);
        g_register_btn.setOnClickListener(this);

        //bos id
        g_register_et_bos_id = findViewById(R.id.register_et_bos_id);
        g_register_et_bos_id.setOnTouchListener(this);
        g_register_et_bos_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_bos_id.getText().toString().isEmpty()){
                    g_register_tv_bos_id.setVisibility(View.INVISIBLE);
                    g_register_et_bos_id.setHint("BOS ID*");
                }
            }
        });

        //nama lengkap
        g_register_et_nama = findViewById(R.id.register_et_nama);
        g_register_et_nama.setOnTouchListener(this);
        g_register_et_nama.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_nama.getText().toString().isEmpty()) {
                    g_register_tv_nama.setVisibility(View.INVISIBLE);
                    g_register_et_nama.setHint("Nama Lengkap*");
                }
            }
        });

        //no. rekening
        g_register_et_no_rekening = findViewById(R.id.register_et_no_kartu);
        g_register_et_no_rekening.setOnTouchListener(this);
        g_register_et_no_rekening.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_no_rekening.getText().toString().isEmpty()) {
                    g_register_tv_no_rekening.setVisibility(View.INVISIBLE);
                    g_register_et_no_rekening.setHint("Nomor Rekening*");
                }
            }
        });

        //no. hp
        g_register_et_no_hp = findViewById(R.id.register_et_no_hp);
        g_register_et_no_hp.setOnTouchListener(this);
        g_register_et_no_hp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1 && editable.toString().startsWith("0")) {
                    editable.clear();
                }
            }
        });
        g_register_et_no_hp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_no_hp.getText().toString().isEmpty()) {
                    g_register_tv_no_hp.setVisibility(View.INVISIBLE);
                    g_register_et_no_hp.setHint("Nomor HP*");
                }
            }
        });

        //email
        g_register_et_email = findViewById(R.id.register_et_email);
        g_register_et_email.setOnTouchListener(this);
        g_register_et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_email.getText().toString().isEmpty()) {
                    g_register_tv_email.setVisibility(View.INVISIBLE);
                    g_register_et_email.setHint("Password*");
                }
            }
        });
        g_register_et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editable).matches() && editable.length() > 0){
                    g_register_email_error.setText("Email valid");
                }else {
                    g_register_email_error.setText("Email tidak valid");
                }
            }
        });

        //password
        g_register_et_password = findViewById(R.id.register_et_password);
        g_register_et_password.setOnTouchListener(this);
        g_register_et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_password.getText().toString().isEmpty()) {
                    g_register_tv_password.setVisibility(View.INVISIBLE);
                    g_register_et_password.setHint("Password*");
                }
            }
        });

        //konfirmasi password
        g_register_et_confirm_password = findViewById(R.id.register_et_confirm_password);
        g_register_et_confirm_password.setOnTouchListener(this);
        g_register_et_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && g_register_et_confirm_password.getText().toString().isEmpty()) {
                    g_register_tv_confirm_password.setVisibility(View.INVISIBLE);
                    g_register_et_confirm_password.setHint("Password*");
                }
            }
        });

        //Text View
        g_register_tv_bos_id = findViewById(R.id.register_tv_bos_id);
        g_register_tv_nama = findViewById(R.id.register_tv_nama);
        g_register_tv_no_rekening = findViewById(R.id.register_tv_no_rekening);
        g_register_tv_no_hp = findViewById(R.id.register_tv_no_hp);
        g_register_tv_email = findViewById(R.id.register_tv_email);
        g_register_tv_password = findViewById(R.id.register_tv_password);
        g_register_tv_confirm_password = findViewById(R.id.register_tv_confirm_password);

        //Error
        g_register_tv_error = findViewById(R.id.register_tv_error);
        g_register_tv_bos_id_error = findViewById(R.id.register_tv_bos_id_error);
        g_register_tv_nama_error = findViewById(R.id.register_tv_nama_error);
        g_register_tv_no_rek_error = findViewById(R.id.register_tv_no_rek_error);
        g_register_no_hp_error = findViewById(R.id.register_tv_no_hp_error);
        g_register_email_error = findViewById(R.id.register_tv_email_error);
        g_register_password_error = findViewById(R.id.register_tv_password_error);
        g_register_confirm_error = findViewById(R.id.register_tv_confirm_error);

    }


    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.register_register_button:
                isInputValid();
                if (isInputValid()){
                    register();
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View p_view, MotionEvent motionEvent) {
        switch (p_view.getId()){
            case R.id.register_et_bos_id:
                g_register_et_bos_id.setHint("");
                g_register_tv_bos_id.setVisibility(View.VISIBLE);
                g_register_tv_bos_id_error.setText("");
                break;
            case R.id.register_et_nama:
                g_register_et_nama.setHint("");
                g_register_tv_nama.setVisibility(View.VISIBLE);
                g_register_tv_nama_error.setText("");
                break;
            case R.id.register_et_no_kartu:
                g_register_et_no_rekening.setHint("");
                g_register_tv_no_rekening.setVisibility(View.VISIBLE);
                g_register_tv_no_rek_error.setText("");
                break;
            case R.id.register_et_no_hp:
                g_register_et_no_hp.setHint("");
                g_register_tv_no_hp.setVisibility(View.VISIBLE);
                g_register_no_hp_error.setText("");
                break;
            case R.id.register_et_email:
                g_register_et_email.setHint("");
                g_register_tv_email.setVisibility(View.VISIBLE);
                g_register_email_error.setText("");
                break;
            case R.id.register_et_password:
                g_register_et_password.setHint("");
                g_register_tv_password.setVisibility(View.VISIBLE);
                g_register_password_error.setText("");
                break;
            case R.id.register_et_confirm_password:
                g_register_et_confirm_password.setHint("");
                g_register_tv_confirm_password.setVisibility(View.VISIBLE);
                g_register_confirm_error.setText("");
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent tmp_back_intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(tmp_back_intent);
        overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        g_instance = null;
    }

    private Boolean isInputValid(){
        g_register_tv_bos_id_error.setText("");
        g_register_tv_nama_error.setText("");
        g_register_tv_no_rek_error.setText("");
        g_register_no_hp_error.setText("");
        g_register_email_error.setText("");
        g_register_password_error.setText("");
        g_register_confirm_error.setText("");
        String l_bos_id = g_register_et_bos_id.getText().toString();
        String l_nama = g_register_et_nama.getText().toString();
        String l_no_rekening = g_register_et_no_rekening.getText().toString();
        String l_no_hp = g_register_et_no_hp.getText().toString();
        String l_email = g_register_et_email.getText().toString();
        String l_password = g_register_et_password.getText().toString();
        String l_confirm = g_register_et_confirm_password.getText().toString();

        Boolean b_bos_id = false, b_nama = false, b_no_rekekning = false, b_no_hp = false, b_email = false, b_password = false, b_confirm = false;

        if (l_bos_id.isEmpty()){
            g_register_tv_bos_id_error.setText("BOS ID harus diisi");
            b_bos_id = false;
        }else if (l_bos_id.length()<8){
            g_register_tv_bos_id_error.setText("BOS ID minimal 8 karakter");
            b_bos_id = false;
        }else b_bos_id = true;

        if (l_nama.isEmpty()){
            g_register_tv_nama_error.setText("Nama harus diisi");
            b_nama = false;
        }else b_nama = true;

        if (l_no_rekening.isEmpty()){
            g_register_tv_no_rek_error.setText("No rekening harus diisi");
            b_no_rekekning = false;
        }else if (l_no_rekening.length()<10){
            g_register_tv_no_rek_error.setText("No rekening harus 10 digit angka");
            b_no_rekekning = false;
        }else b_no_rekekning = true;

        if (l_no_hp.isEmpty()){
            g_register_no_hp_error.setText("No HP harus diisi");
            b_no_hp = false;
        }else if (l_no_hp.length()<9){
            g_register_no_hp_error.setText("No HP minimal 10 digit angka");
            b_no_hp = false;
        }else b_no_hp = true;

        if (l_email.isEmpty()){
            g_register_email_error.setText("Email harus diisi");
            b_email = false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(l_email).matches() && l_email.length() > 0){
            g_register_email_error.setText("Email tidak valid");
            b_email = false;
        }else b_email = true;

        if (l_password.isEmpty()){
            g_register_password_error.setText("Password harus diisi");
            b_password = false;
        }else if (l_password.length()<6){
            g_register_password_error.setText("Password harus 6 digit angka");
            b_password = false;
        }else b_password = true;

        if (l_confirm.isEmpty()){
            g_register_confirm_error.setText("Konfirmasi Password harus diisi");
            b_confirm = false;
        } else if (l_confirm.length()<6){
            g_register_confirm_error.setText("Konfirmasi Password harus 6 digit angka");
            b_confirm = false;
        }else if (!l_password.equals(l_confirm)){
            g_register_confirm_error.setText("\nPassword tidak cocok");
            b_confirm = false;
        }else b_confirm = true;

        if (b_bos_id && b_nama && b_no_rekekning && b_no_hp && b_email && b_password && b_confirm){
            return true;
        }else return false;
    }

    private void register(){
        String l_bos_id = g_register_et_bos_id.getText().toString();
        String l_nama = g_register_et_nama.getText().toString();
        String l_no_rekening = g_register_et_no_rekening.getText().toString();
        String l_no_hp = g_register_et_no_hp.getText().toString();
        l_no_hp = "+62" + l_no_hp;
        String l_email = g_register_et_email.getText().toString().trim();
        String l_password = g_register_et_password.getText().toString();

        VolleyClass.register(this, l_bos_id, l_nama, l_no_rekening, l_no_hp, l_email, l_password);
//       g_register_tv_error.setText(l_bos_id + "\n" + l_nama + "\n" + l_no_rekening + "\n" + l_no_hp + "\n" + l_password);
    }

    public void moveToOTPActivity(String p_bos_id, String p_no_hp){
        Intent tmp_register_intent = new Intent(RegisterActivity.this, OTPActivity.class);
        tmp_register_intent.putExtra(BOS_ID, p_bos_id);
        tmp_register_intent.putExtra("NO_HP", p_no_hp);
        startActivity(tmp_register_intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void setError(String p_message){
        g_register_tv_error.setVisibility(View.VISIBLE);
        g_register_tv_error.setText(p_message);
    }
}
