package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bca_bos.networks.Login;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    ConstraintLayout g_login_cl;
    LinearLayout g_login_ll;
    EditText g_login_et_bos_id, g_login_et_password;
    Button g_login_btn_login;
    TextView g_login_tv_register, g_login_tv_bos_id, g_login_tv_password;

    public static LoginActivity g_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        disableSSL();

        g_instance = this;

        g_login_cl = findViewById(R.id.constraintLayoutLogin);
        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_in);
        g_login_cl.startAnimation(slide_down);

        g_login_ll = findViewById(R.id.linearLayoutLogin);
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_in);
        g_login_ll.startAnimation(slide_up);

        g_login_tv_bos_id =findViewById(R.id.login_tv_bos_id);
        g_login_tv_password = findViewById(R.id.login_tv_password);

        g_login_et_bos_id = findViewById(R.id.login_et_bos_id);
        g_login_et_bos_id.setOnTouchListener(this);

        g_login_et_password = findViewById(R.id.login_et_password);
        g_login_et_password.setOnTouchListener(this);

        g_login_btn_login = findViewById(R.id.login_login_button);
        g_login_btn_login.setOnClickListener(this);

        g_login_tv_register = findViewById(R.id.login_register_button);
        g_login_tv_register.setOnClickListener(this);

    }


    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.login_login_button:
                String tmp_bos_id = g_login_et_bos_id.getText().toString();
                String tmp_password = g_login_et_password.getText().toString();
                Login.postUserPass(this, tmp_bos_id, tmp_password);
                break;
            case R.id.login_register_button:
                Intent tmp_register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(tmp_register_intent);
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
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
            case R.id.login_et_password:
                g_login_et_password.setHint("");
                g_login_tv_password.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }

    public void intentLogin(){
        Intent tmp_login_intent = new Intent(LoginActivity.this, ApplicationContainer.class);
        startActivity(tmp_login_intent);
        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
    }

    public static void disableSSL() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

}


