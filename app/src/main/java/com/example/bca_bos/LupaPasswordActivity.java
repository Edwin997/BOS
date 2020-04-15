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

public class LupaPasswordActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    EditText g_lupapassword_et_bos_id;
    TextView g_lupapassword_tv_error, g_lupapassword_tv_bos_id;
    Button g_lupapassword_btn_kirim;

    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        g_lupapassword_et_bos_id = findViewById(R.id.lupapassword_et_bos_id);
        g_lupapassword_et_bos_id.setOnTouchListener(this);
        g_lupapassword_et_bos_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 8){
                    g_lupapassword_tv_error.setTextColor(Color.parseColor("#7b1a1c"));
                    g_lupapassword_tv_error.setText("BOS ID minimal 8 karakter");
                } else if (editable.length() > 20){
                    g_lupapassword_tv_error.setTextColor(Color.parseColor("#7b1a1c"));
                    g_lupapassword_tv_error.setText("BOS ID maksimal 20 karakter");
                } else{
                    g_lupapassword_tv_error.setTextColor(Color.parseColor("#1CAD50"));
                    g_lupapassword_tv_error.setText("BOS ID valid");
                }
            }
        });

        g_lupapassword_tv_bos_id = findViewById(R.id.lupapassword_tv_bos_id);
        g_lupapassword_tv_error = findViewById(R.id.lupapasword_tv_error);

        g_lupapassword_btn_kirim = findViewById(R.id.lupapasword_kirim_button);
    }

    private Boolean isBOSIDValid(){
        String l_bos_id = g_lupapassword_et_bos_id.getText().toString().trim();

        if (l_bos_id.isEmpty()){
            g_lupapassword_tv_error.setText("BOS ID tidak boleh kosong");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean onTouch(View p_view, MotionEvent event) {
        switch (p_view.getId()){
            case R.id.lupapassword_et_bos_id:
                g_lupapassword_et_bos_id.setHint("");
                g_lupapassword_tv_bos_id.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.lupapasword_kirim_button:
                if (isBOSIDValid()){

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent tmp_back_intent = new Intent(LupaPasswordActivity.this, LoginActivity.class);
        startActivity(tmp_back_intent);
        overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
        finish();
    }
}
