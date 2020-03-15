package com.example.bca_bos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        Intent intent = null;
        if(preferences.contains(BOS_ID)){              //if user is currently logged in;
            intent = new Intent(this, PasswordActivity.class);
            finish();
        }else {                                                 //if user is not yet logged in;
            intent = new Intent(this, LoginActivity.class);
            finish();
        }
        startActivity(intent);
    }

}