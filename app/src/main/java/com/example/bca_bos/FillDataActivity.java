package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FillDataActivity extends AppCompatActivity implements View.OnClickListener {

    Button g_filldata_btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_data);

        g_filldata_btn_submit = findViewById(R.id.filldata_submit_button);
        g_filldata_btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.filldata_submit_button:
                Intent tmp_filldata_intent = new Intent(FillDataActivity.this, ApplicationContainer.class);
                startActivity(tmp_filldata_intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                break;
        }

    }
}