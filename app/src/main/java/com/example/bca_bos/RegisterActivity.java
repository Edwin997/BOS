package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    EditText g_register_et_bos_id, g_register_et_nama, g_register_et_no_rekening, g_register_et_no_hp, g_register_et_password;
    TextView g_register_tv_bos_id, g_register_tv_nama, g_register_tv_no_rekening, g_register_tv_no_hp, g_register_tv_password, g_register_tv_error;

    //
    public static RegisterActivity g_instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Connect Internet
        NetworkUtil.disableSSL();
        g_instance = this;

        g_register_btn = findViewById(R.id.register_register_button);
        g_register_btn.setOnClickListener(this);

        g_register_et_bos_id = findViewById(R.id.register_et_bos_id);
        g_register_et_bos_id.setOnTouchListener(this);
        g_register_et_nama = findViewById(R.id.register_et_nama);
        g_register_et_nama.setOnTouchListener(this);
        g_register_et_no_rekening = findViewById(R.id.register_et_no_kartu);
        g_register_et_no_rekening.setOnTouchListener(this);
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
        g_register_et_password = findViewById(R.id.register_et_password);
        g_register_et_password.setOnTouchListener(this);

        g_register_tv_bos_id = findViewById(R.id.register_tv_bos_id);
        g_register_tv_nama = findViewById(R.id.register_tv_nama);
        g_register_tv_no_rekening = findViewById(R.id.register_tv_no_rekening);
        g_register_tv_no_hp = findViewById(R.id.register_tv_no_hp);
        g_register_tv_password = findViewById(R.id.register_tv_password);
        g_register_tv_error = findViewById(R.id.register_tv_error);
        g_register_tv_error.setText("");

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
                break;
            case R.id.register_et_nama:
                g_register_et_nama.setHint("");
                g_register_tv_nama.setVisibility(View.VISIBLE);
                break;
            case R.id.register_et_no_kartu:
                g_register_et_no_rekening.setHint("");
                g_register_tv_no_rekening.setVisibility(View.VISIBLE);
                break;
            case R.id.register_et_no_hp:
                g_register_et_no_hp.setHint("");
                g_register_tv_no_hp.setVisibility(View.VISIBLE);
                break;
            case R.id.register_et_password:
                g_register_et_password.setHint("");
                g_register_tv_password.setVisibility(View.VISIBLE);
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
        g_register_tv_error.setText("");
        String l_bos_id = g_register_et_bos_id.getText().toString();
        String l_nama = g_register_et_nama.getText().toString();
        String l_no_rekening = g_register_et_no_rekening.getText().toString();
        String l_no_hp = g_register_et_no_hp.getText().toString();
        String l_password = g_register_et_password.getText().toString();

        Boolean b_bos_id = false, b_nama = false, b_no_rekekning = false, b_no_hp = false, b_password = false;

        if (l_bos_id.isEmpty() || l_nama.isEmpty() || l_no_rekening.isEmpty() || l_no_hp.isEmpty() || l_password.isEmpty()){
            g_register_tv_error.setText("\nSemua field harus diisi");
            b_bos_id = false; b_nama = false; b_no_rekekning = false; b_no_hp = false; b_password = false;
        }else {
            b_bos_id = true; b_nama = true; b_no_rekekning = true; b_no_hp = true; b_password = true;

        }
        if (l_bos_id.length()<8){
            g_register_tv_error.append("\nBOS ID minimal 8 karakter");
            b_bos_id = false;
        }else b_bos_id = true;

        if (l_no_rekening.length()<10){
            g_register_tv_error.append("\nNo rekening harus 10 digit angka");
            b_no_rekekning = false;
        }else b_no_rekekning = true;

        if (l_no_hp.length()<9){
            g_register_tv_error.append("\nNo HP minimal 10 digit angka");
            b_no_hp = false;
        }else b_no_hp = true;

        if (l_password.length()<6){
            g_register_tv_error.append("\nPassword harus 6 digit angka");
            b_password = false;
        }else b_password = true;

        if (b_bos_id && b_nama && b_no_rekekning && b_no_hp && b_password){
            return true;
        }else return false;
    }

    private void register(){
        String l_bos_id = g_register_et_bos_id.getText().toString();
        String l_nama = g_register_et_nama.getText().toString();
        String l_no_rekening = g_register_et_no_rekening.getText().toString();
        String l_no_hp = g_register_et_no_hp.getText().toString();
        l_no_hp = "+62" + l_no_hp;
        String l_password = g_register_et_password.getText().toString();

        VolleyClass.register(this, l_bos_id, l_nama, l_no_rekening, l_no_hp, l_password);
//       g_register_tv_error.setText(l_bos_id + "\n" + l_nama + "\n" + l_no_rekening + "\n" + l_no_hp + "\n" + l_password);
    }

    public void intentRegister(String p_bos_id, String p_no_hp){
        Intent tmp_register_intent = new Intent(RegisterActivity.this, OTPActivity.class);
        tmp_register_intent.putExtra("bos_id", p_bos_id);
        tmp_register_intent.putExtra("no_hp", p_no_hp);
        startActivity(tmp_register_intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void setError(String p_message){
        g_register_tv_error.setText(p_message);
    }
}
