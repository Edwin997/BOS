package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bca_bos.networks.NetworkUtil;
import com.example.bca_bos.networks.VolleyClass;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    //OTP number
    TextView g_otp_tv_first_digit, g_otp_tv_second_digit, g_otp_tv_third_digit, g_otp_tv_fourth_digit;
    View g_otp_view_first_digit, g_otp_view_second_digit, g_otp_view_third_digit, g_otp_view_fourth_digit;

    //Keyboard
    Button g_otp_btn_one, g_otp_btn_two, g_otp_btn_three,
            g_otp_btn_four, g_otp_btn_five, g_otp_btn_six,
            g_otp_btn_seven,  g_otp_btn_eight, g_otp_btn_nine,
            g_otp_btn_zero;
    ImageButton g_otp_btn_delete, g_otp_btn_refresh;
    String tmp_otp, g_otp_flag;
    public int g_counter;

    //Error
    TextView g_otp_tv_error;
    LinearLayout g_otp_ll_kirim_ulang_otp;

    //Data yang dilempar
    String g_bos_id, g_no_hp;

    public static OTPActivity g_instance = null;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String NO_HP = "NO_HP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //Connect Internet
        NetworkUtil.disableSSL();
        g_instance = this;

        g_otp_flag = "zero";
        tmp_otp = "";


        //Menerima data intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                g_bos_id = null;
                g_no_hp = null;
            } else {
                g_bos_id = extras.getString(BOS_ID);
                g_no_hp = extras.getString(NO_HP);

            }
        } else {
            g_bos_id = (String) savedInstanceState.getSerializable(BOS_ID);
            g_no_hp = (String) savedInstanceState.getSerializable(NO_HP);
        }

        //OTP number
        g_otp_tv_first_digit = findViewById(R.id.apps_otp_first_digit_text_view);
        g_otp_tv_second_digit = findViewById(R.id.apps_otp_second_digit_text_view);
        g_otp_tv_third_digit = findViewById(R.id.apps_otp_third_digit_text_view);
        g_otp_tv_fourth_digit = findViewById(R.id.apps_otp_fourth_digit_textview);
        g_otp_view_first_digit = findViewById(R.id.apps_otp_first_digit_view);
        g_otp_view_second_digit = findViewById(R.id.apps_otp_second_digit_view);
        g_otp_view_third_digit = findViewById(R.id.apps_otp_third_digit_view);
        g_otp_view_fourth_digit = findViewById(R.id.apps_otp_fourth_digit_view);

        //Keyboard
        g_otp_btn_one = findViewById(R.id.apps_otp_one_button);
        g_otp_btn_one.setOnClickListener(this);
        g_otp_btn_two = findViewById(R.id.apps_otp_two_button);
        g_otp_btn_two.setOnClickListener(this);
        g_otp_btn_three = findViewById(R.id.apps_otp_three_button);
        g_otp_btn_three.setOnClickListener(this);
        g_otp_btn_four = findViewById(R.id.apps_otp_four_button);
        g_otp_btn_four.setOnClickListener(this);
        g_otp_btn_five = findViewById(R.id.apps_otp_five_button);
        g_otp_btn_five.setOnClickListener(this);
        g_otp_btn_six = findViewById(R.id.apps_otp_six_button);
        g_otp_btn_six.setOnClickListener(this);
        g_otp_btn_seven = findViewById(R.id.apps_otp_seven_button);
        g_otp_btn_seven.setOnClickListener(this);
        g_otp_btn_eight = findViewById(R.id.apps_otp_eight_button);
        g_otp_btn_eight.setOnClickListener(this);
        g_otp_btn_nine = findViewById(R.id.apps_otp_nine_button);
        g_otp_btn_nine.setOnClickListener(this);
        g_otp_btn_zero = findViewById(R.id.apps_otp_zero_button);
        g_otp_btn_zero.setOnClickListener(this);
        g_otp_btn_delete = findViewById(R.id.apps_otp_delete_button);
        g_otp_btn_delete.setOnClickListener(this);
        g_otp_btn_refresh = findViewById(R.id.apps_otp_refresh_button);
        g_otp_btn_refresh.setOnClickListener(this);

        //Error
        g_otp_tv_error = findViewById(R.id.apps_otp_error_text_view);
        g_otp_tv_error.setText("");

        //Kirim ulang OTP
        g_otp_ll_kirim_ulang_otp = findViewById(R.id.apps_otp_kirim_ulang_linear_layout);
        g_otp_ll_kirim_ulang_otp.setVisibility(View.GONE);
        startCountDown();
        g_otp_ll_kirim_ulang_otp.setOnClickListener(this);

    }

    private void startCountDown(){
        g_counter = 60;
        new CountDownTimer(60000, 1000){
            public void onTick(long millisUntilFinished){
                g_otp_tv_error.setText("Kirim ulang OTP ("+String.valueOf(g_counter)+")");
                g_counter--;
            }
            public  void onFinish(){
                g_otp_tv_error.setText("SMS OTP belum masuk?");
                g_otp_ll_kirim_ulang_otp.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.apps_otp_one_button:
                saveOTPValue(g_otp_flag, "1");
                changeOTPToNumber("1");
                changeFlag();
                break;
            case R.id.apps_otp_two_button:
                saveOTPValue(g_otp_flag, "2");
                changeOTPToNumber("2");
                changeFlag();
                break;
            case R.id.apps_otp_three_button:
                saveOTPValue(g_otp_flag, "3");
                changeOTPToNumber("3");
                changeFlag();
                break;
            case R.id.apps_otp_four_button:
                saveOTPValue(g_otp_flag, "4");
                changeOTPToNumber("4");
                changeFlag();
                break;
            case R.id.apps_otp_five_button:
                saveOTPValue(g_otp_flag, "5");
                changeOTPToNumber("5");
                changeFlag();
                break;
            case R.id.apps_otp_six_button:
                saveOTPValue(g_otp_flag, "6");
                changeOTPToNumber("6");
                changeFlag();
                break;
            case R.id.apps_otp_seven_button:
                saveOTPValue(g_otp_flag, "7");
                changeOTPToNumber("7");
                changeFlag();
                break;
            case R.id.apps_otp_eight_button:
                saveOTPValue(g_otp_flag, "8");
                changeOTPToNumber("8");
                changeFlag();
                break;
            case R.id.apps_otp_nine_button:
                saveOTPValue(g_otp_flag, "9");
                changeOTPToNumber("9");
                changeFlag();
                break;
            case R.id.apps_otp_zero_button:
                saveOTPValue(g_otp_flag, "0");
                changeOTPToNumber("0");
                changeFlag();
                break;
            case R.id.apps_otp_delete_button:
                deleteOTPDigit();
                break;
            case R.id.apps_otp_refresh_button:
                refreshOTP();
                break;
            case R.id.apps_otp_kirim_ulang_linear_layout:
                g_otp_ll_kirim_ulang_otp.setVisibility(View.GONE);
                startCountDown();
                VolleyClass.resendOTP(this, g_no_hp);
                break;
        }
    }

    private void saveOTPValue(String p_otp_flag, String p_value){
        if (p_otp_flag.equals("zero")){
            tmp_otp = tmp_otp + p_value;
        }else if (p_otp_flag.equals("first")){
            tmp_otp = tmp_otp + p_value;
        }else if (p_otp_flag.equals("second")){
            tmp_otp = tmp_otp + p_value;
        }else if (p_otp_flag.equals("third")){
            tmp_otp = tmp_otp + p_value;
        }else if (p_otp_flag.equals("fourth")){

        }
    }

    private void changeFlag(){
        if (g_otp_flag.equals("zero")){
            g_otp_flag = "first";
        }else if (g_otp_flag.equals("first")){
            g_otp_flag = "second";
        }else if (g_otp_flag.equals("second")){
            g_otp_flag = "third";
        }else if (g_otp_flag.equals("third")){
            g_otp_flag = "fourth";
            //Send OTP
            VolleyClass.sendOTP(this, tmp_otp, g_bos_id);

        }else if (g_otp_flag.equals("fourth")){

        }
    }

    private void changeOTPToNumber(String p_value){
        if (g_otp_flag.equals("zero")){
            g_otp_tv_first_digit.setText(p_value);
            g_otp_view_first_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_box_blue));
        }else if (g_otp_flag.equals("first")){
            g_otp_tv_second_digit.setText(p_value);
            g_otp_view_second_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_box_blue));
        }else if (g_otp_flag.equals("second")){
            g_otp_tv_third_digit.setText(p_value);
            g_otp_view_third_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_box_blue));
        }else if (g_otp_flag.equals("third")){
            g_otp_tv_fourth_digit.setText(p_value);
            g_otp_view_fourth_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_box_blue));
        }else if (g_otp_flag.equals("fourth")){

        }
    }

    private void deleteOTPDigit(){
        if (g_otp_flag.equals("zero")){

        }else if (g_otp_flag.equals("first")){
            g_otp_view_first_digit.setBackground(this.getResources().getDrawable(R.color.grey));
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_tv_first_digit.setText("-");
            g_otp_tv_error.setText("");
            g_otp_flag = "zero";
        }else if (g_otp_flag.equals("second")){
            g_otp_view_second_digit.setBackground(this.getResources().getDrawable(R.color.grey));
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_tv_second_digit.setText("-");
            g_otp_tv_error.setText("");
            g_otp_flag = "first";
        }else if (g_otp_flag.equals("third")){
            g_otp_view_third_digit.setBackground(this.getResources().getDrawable(R.color.grey));
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_tv_third_digit.setText("-");
            g_otp_tv_error.setText("");
            g_otp_flag = "second";
        }else if (g_otp_flag.equals("fourth")){
            g_otp_view_fourth_digit.setBackground(this.getResources().getDrawable(R.color.grey));
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_tv_fourth_digit.setText("-");
            g_otp_tv_error.setText("");
            g_otp_flag = "third";
        }
    }

    private void refreshOTP() {
        g_otp_tv_first_digit.setText("-");
        g_otp_tv_second_digit.setText("-");
        g_otp_tv_third_digit.setText("-");
        g_otp_tv_fourth_digit.setText("-");
        g_otp_view_first_digit.setBackground(this.getResources().getDrawable(R.color.grey));
        g_otp_view_second_digit.setBackground(this.getResources().getDrawable(R.color.grey));
        g_otp_view_third_digit.setBackground(this.getResources().getDrawable(R.color.grey));
        g_otp_view_fourth_digit.setBackground(this.getResources().getDrawable(R.color.grey));
        g_otp_tv_error.setText("");
        g_otp_flag = "zero";
        tmp_otp = "";
    }

    private String deleteLastCharacter(String p_str) {
        if (p_str != null && p_str.length() > 0) {
            p_str = p_str.substring(0, p_str.length() - 1);
        }
        return p_str;
    }

    public void registerIntent(int p_seller_id, String p_bos_id){
        Intent tmp_register_intent = new Intent(OTPActivity.this, FillDataActivity.class);
        saveStringSharedPreference(BOS_ID, p_bos_id);
        saveIntegerSharedPreference(SELLER_ID, p_seller_id);
        startActivity(tmp_register_intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        closeRegisterActivity();
        finish();
    }

    public void setError(String p_error_message){
        g_otp_tv_error.setText(p_error_message);
    }

    private void closeRegisterActivity(){
        if(RegisterActivity.g_instance != null) {
            try {
                RegisterActivity.g_instance.finish();
            } catch (Exception e) {}
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    private void saveStringSharedPreference(String p_key, String p_value) {
        //Save Shared Preference
        SharedPreferences.Editor l_editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
        l_editor.putString(p_key, p_value);
        l_editor.commit();
    }

    private void saveIntegerSharedPreference(String p_key, int p_value) {
        //Save Shared Preference
        SharedPreferences.Editor l_editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
        l_editor.putInt(p_key, p_value);
        l_editor.commit();
    }
}
