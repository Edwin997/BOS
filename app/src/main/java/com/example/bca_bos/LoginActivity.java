package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText g_login_et_username, g_login_et_password;
    Button g_login_btn_login;
    TextView g_login_tv_register, g_login_tv_username, g_login_tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        g_login_tv_username =findViewById(R.id.textViewUsername);
        g_login_tv_password = findViewById(R.id.textViewPassword);

        g_login_et_username = findViewById(R.id.editTextUsername);
        g_login_et_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                g_login_et_username.setHint("");
                g_login_tv_username.setVisibility(View.VISIBLE);
                return false;
            }
        });

        g_login_et_password = findViewById(R.id.editTextPassword);
        g_login_et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                g_login_et_password.setHint("");
                g_login_tv_password.setVisibility(View.VISIBLE);
                return false;
            }
        });

        g_login_btn_login = findViewById(R.id.buttonLogin);
        g_login_tv_register = findViewById(R.id.textViewRegister);

        g_login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp_login_intent = new Intent(LoginActivity.this, OTPActivity.class);
                startActivity(tmp_login_intent);
                finish();
            }
        });

        g_login_tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp_register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(tmp_register_intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }


}
