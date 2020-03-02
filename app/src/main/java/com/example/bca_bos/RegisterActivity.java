package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    ImageButton g_register_ib_back;
    Button g_register_btn;
    EditText g_register_et_bos_id, g_register_et_no_kartu, g_register_et_no_hp, g_register_et_password;
    TextView g_register_tv_bos_id, g_register_tv_no_kartu, g_register_tv_no_hp, g_register_tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        g_register_ib_back = findViewById(R.id.register_back_button);
        g_register_ib_back.setOnClickListener(this);
        g_register_btn = findViewById(R.id.register_register_button);
        g_register_btn.setOnClickListener(this);

        g_register_et_bos_id = findViewById(R.id.register_et_bos_id);
        g_register_et_bos_id.setOnTouchListener(this);
        g_register_et_no_kartu = findViewById(R.id.register_et_no_kartu);
        g_register_et_no_kartu.setOnTouchListener(this);
        g_register_et_no_hp = findViewById(R.id.register_et_no_hp);
        g_register_et_no_hp.setOnTouchListener(this);
        g_register_et_password = findViewById(R.id.register_et_password);
        g_register_et_password.setOnTouchListener(this);

        g_register_tv_bos_id = findViewById(R.id.register_tv_bos_id);
        g_register_tv_no_kartu = findViewById(R.id.register_tv_no_kartu);
        g_register_tv_no_hp = findViewById(R.id.register_tv_no_hp);
        g_register_tv_password = findViewById(R.id.register_tv_password);

    }


    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.register_back_button:
                finish();
                overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
                break;
            case R.id.register_register_button:
                Intent tmp_register_intent = new Intent(RegisterActivity.this, ApplicationContainer.class);
                startActivity(tmp_register_intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
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
            case R.id.register_et_no_kartu:
                g_register_et_no_kartu.setHint("");
                g_register_tv_no_kartu.setVisibility(View.VISIBLE);
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
}
