package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText g_login_et_username, g_login_et_password;
    Button g_login_btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        g_login_et_username = findViewById(R.id.editTextUsername);
        g_login_et_password = findViewById(R.id.editTextPassword);
        g_login_btn_login = findViewById(R.id.buttonLogin);

        g_login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp_login_intent = new Intent(LoginActivity.this, OTPActivity.class);
                startActivity(tmp_login_intent);
                finish();
            }
        });
    }
}
