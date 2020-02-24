package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OTPActivity extends AppCompatActivity {

    EditText g_otp_et_otp;
    Button g_otp_btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        g_otp_btn_login = findViewById(R.id.buttonLogin);

        g_otp_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tmp_login_intent = new Intent(OTPActivity.this, ApplicationContainer.class);
                startActivity(tmp_login_intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();

            }
        });
    }
}
