package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bca_bos.models.Seller;
import com.example.bca_bos.networks.NetworkUtil;
import com.example.bca_bos.networks.VolleyClass;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    ConstraintLayout g_login_cl;
    LinearLayout g_login_ll;
    EditText g_login_et_bos_id;
    Button g_login_btn_login;
    TextView g_login_tv_register, g_login_tv_bos_id, g_login_tv_error, g_login_tv_lupa_password;

    public static LoginActivity g_instance;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    String bos_id_status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connect Internet
        NetworkUtil.disableSSL();
        g_instance = this;

        g_login_cl = findViewById(R.id.constraintLayoutLogin);
        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_in);
        g_login_cl.startAnimation(slide_down);

        g_login_ll = findViewById(R.id.linearLayoutLogin);
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_in);
        g_login_ll.startAnimation(slide_up);

        g_login_tv_bos_id =findViewById(R.id.login_tv_bos_id);
//        g_login_tv_password = findViewById(R.id.login_tv_password);

        g_login_et_bos_id = findViewById(R.id.login_et_bos_id);
        g_login_et_bos_id.setOnTouchListener(this);
        g_login_et_bos_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 8){
                    g_login_tv_error.setTextColor(Color.parseColor("#7b1a1c"));
                    g_login_tv_error.setText("BOS ID minimal 8 karakter");
                } else if (editable.length() > 20){
                    g_login_tv_error.setTextColor(Color.parseColor("#7b1a1c"));
                    g_login_tv_error.setText("BOS ID maksimal 20 karakter");
                } else{
                    g_login_tv_error.setTextColor(Color.parseColor("#1CAD50"));
                    g_login_tv_error.setText("BOS ID valid");
                }
            }
        });

//        g_login_et_password = findViewById(R.id.login_et_password);
//        g_login_et_password.setOnTouchListener(this);

        g_login_btn_login = findViewById(R.id.login_login_button);
        g_login_btn_login.setOnClickListener(this);

        g_login_tv_register = findViewById(R.id.login_register_button);
        g_login_tv_register.setOnClickListener(this);

        g_login_tv_error = findViewById(R.id.login_tv_error);
        g_login_tv_error.setText("");

        g_login_tv_lupa_password = findViewById(R.id.login_lupa_password_button);
        g_login_tv_lupa_password.setOnClickListener(this);

    }


    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.login_login_button:
                String tmp_bos_id = g_login_et_bos_id.getText().toString();
                isBOSIdValid(tmp_bos_id);
                if (bos_id_status.equals("valid")){
                    VolleyClass.loginByID(this, tmp_bos_id, "");
                    bos_id_status = "";
                }
                break;
            case R.id.login_register_button:
                Intent tmp_register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(tmp_register_intent);
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                finish();
                break;
            case R.id.login_lupa_password_button:
                Intent tmp_lupa_password_intent = new Intent(LoginActivity.this, LupaPasswordActivity.class);
                startActivity(tmp_lupa_password_intent);
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                finish();
                break;
        }
    }

    @Override
    public boolean onTouch(View p_view, MotionEvent motionEvent) {
        switch (p_view.getId()){
            case R.id.login_et_bos_id:
                g_login_et_bos_id.setHint("");
                g_login_tv_bos_id.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }

    private void isBOSIdValid(String p_bos_id){
        if (p_bos_id.isEmpty()){
            g_login_tv_error.setText("BOS ID harus diisi");
        }else if (!p_bos_id.isEmpty() && p_bos_id.length()<8){
            g_login_tv_error.setText("BOS ID minimal 8 karakter");
        }else if (!p_bos_id.isEmpty() && p_bos_id.length()>20){
            g_login_tv_error.setText("BOS ID maksimal 20 karakter");
        }else if (!p_bos_id.isEmpty() && p_bos_id.length()<=20 && p_bos_id.length()>=8){
            g_login_tv_error.setText("");
            bos_id_status = "valid";
        }
    }

    public void moveToPasswordActivity(String p_bos_id){
        //save BOS ID
//        SharedPreferences.Editor l_editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
//        l_editor.putString(BOS_ID, p_bos_id);
//        l_editor.commit();
        Intent tmp_login_intent = new Intent(LoginActivity.this, PasswordActivity.class);
        tmp_login_intent.putExtra("BOS_ID",p_bos_id);
        startActivity(tmp_login_intent);
        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
        finish();
    }

    public void getProfile(int p_seller_id){
        VolleyClass.getProfileLogin(this, p_seller_id);

    }

    public void generateOTP(Seller p_seller){
        String l_bos_id = p_seller.getUsername();
        String l_no_hp = p_seller.getPhone();
        VolleyClass.getOTPLogin(this, l_no_hp, l_bos_id);
    }

    public void moveToOTPActivity(String p_bos_id, String p_no_hp){
        Intent tmp_login_intent = new Intent(LoginActivity.this, OTPActivity.class);
        tmp_login_intent.putExtra("BOS_ID",p_bos_id);
        tmp_login_intent.putExtra("NO_HP",p_no_hp);
        startActivity(tmp_login_intent);
        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
        finish();
    }

    public void setError(String p_message){
        g_login_tv_error.setText(p_message);
    }

}


