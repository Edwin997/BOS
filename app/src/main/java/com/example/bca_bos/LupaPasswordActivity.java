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

    EditText g_lupapassword_et_email;
    TextView g_lupapassword_tv_error, g_lupapassword_tv_email;
    Button g_lupapassword_btn_kirim;

    String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        g_lupapassword_et_email = findViewById(R.id.lupapassword_et_email);
        g_lupapassword_et_email.setOnTouchListener(this);
        g_lupapassword_et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editable).matches() && editable.length() > 0){
                    g_lupapassword_tv_error.setTextColor(Color.parseColor("#1CAD50"));
                    g_lupapassword_tv_error.setText("Email valid");
                }else {
                    g_lupapassword_tv_error.setTextColor(Color.parseColor("#7b1a1c"));
                    g_lupapassword_tv_error.setText("Email tidak valid");
                }
            }
        });

        g_lupapassword_tv_email = findViewById(R.id.lupapassword_tv_email);
        g_lupapassword_tv_error = findViewById(R.id.lupapasword_tv_error);

        g_lupapassword_btn_kirim = findViewById(R.id.lupapasword_kirim_button);
    }

    private Boolean isEmailValid(){
        String l_email = g_lupapassword_et_email.getText().toString().trim();

        if (l_email.isEmpty()){
            g_lupapassword_tv_error.setText("Email tidak boleh kosong");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean onTouch(View p_view, MotionEvent event) {
        switch (p_view.getId()){
            case R.id.lupapassword_et_email:
                g_lupapassword_et_email.setHint("");
                g_lupapassword_tv_email.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.lupapasword_kirim_button:

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
