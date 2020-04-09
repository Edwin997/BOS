package com.example.bca_bos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String NAMA_TOKO = "NAMA_TOKO";
    SharedPreferences g_preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                g_preference = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

                Intent intent = null;
                if(g_preference.contains(BOS_ID)){              //if user is currently logged in;
                    intent = new Intent(StartActivity.this, PasswordActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
                    finish();
                }else {                                                 //if user is not yet logged in;
                    intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
                    finish();
                }
            }
        },3500);




    }

}