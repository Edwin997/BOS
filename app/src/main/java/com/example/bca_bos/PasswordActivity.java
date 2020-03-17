package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bca_bos.networks.NetworkUtil;
import com.example.bca_bos.networks.VolleyClass;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //Password digit
    ImageView g_password_iv_first_digit, g_password_iv_second_digit, g_password_iv_third_digit, g_password_iv_fourth_digit,
            g_password_iv_fivth_digit, g_password_iv_sixth_digit;


    //Keyboard
    Button g_password_btn_one, g_password_btn_two, g_password_btn_three,
            g_password_btn_four, g_password_btn_five, g_password_btn_six,
            g_password_btn_seven,  g_password_btn_eight, g_password_btn_nine,
            g_password_btn_zero;
    ImageButton g_password_btn_delete;
    TextView g_password_tv_error;
    String tmp_password, g_password_flag;

    //Data yang dilempar
    String g_bos_id;

    public static PasswordActivity g_instance;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        NetworkUtil.disableSSL();
        g_instance = this;

        g_password_flag = "zero";
        tmp_password = "";

        //Menerima data
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                g_bos_id= null;
            } else {
                g_bos_id= extras.getString(BOS_ID);
            }
        } else {
            g_bos_id= (String) savedInstanceState.getSerializable(BOS_ID);
        }

        //Password digit
        g_password_iv_first_digit = findViewById(R.id.apps_password_first_digit_image_view);
        g_password_iv_second_digit = findViewById(R.id.apps_password_second_digit_image_view);
        g_password_iv_third_digit = findViewById(R.id.apps_password_third_digit_image_view);
        g_password_iv_fourth_digit = findViewById(R.id.apps_password_fourth_digit_image_view);
        g_password_iv_fivth_digit = findViewById(R.id.apps_password_fivth_digit_image_view);
        g_password_iv_sixth_digit = findViewById(R.id.apps_password_sixth_digit_image_view);

        //Keyboard
        g_password_btn_one = findViewById(R.id.apps_password_one_button);
        g_password_btn_one.setOnClickListener(this);
        g_password_btn_two = findViewById(R.id.apps_password_two_button);
        g_password_btn_two.setOnClickListener(this);
        g_password_btn_three = findViewById(R.id.apps_password_three_button);
        g_password_btn_three.setOnClickListener(this);
        g_password_btn_four = findViewById(R.id.apps_password_four_button);
        g_password_btn_four.setOnClickListener(this);
        g_password_btn_five = findViewById(R.id.apps_password_five_button);
        g_password_btn_five.setOnClickListener(this);
        g_password_btn_six = findViewById(R.id.apps_password_six_button);
        g_password_btn_six.setOnClickListener(this);
        g_password_btn_seven = findViewById(R.id.apps_password_seven_button);
        g_password_btn_seven.setOnClickListener(this);
        g_password_btn_eight = findViewById(R.id.apps_password_eight_button);
        g_password_btn_eight.setOnClickListener(this);
        g_password_btn_nine = findViewById(R.id.apps_password_nine_button);
        g_password_btn_nine.setOnClickListener(this);
        g_password_btn_zero = findViewById(R.id.apps_password_zero_button);
        g_password_btn_zero.setOnClickListener(this);
        g_password_btn_delete = findViewById(R.id.apps_password_delete_button);
        g_password_btn_delete.setOnClickListener(this);

        //Error
        g_password_tv_error = findViewById(R.id.apps_password_error_text_view);
        g_password_tv_error.setText("");

    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.apps_password_one_button:
                savepasswordValue(g_password_flag, "1");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_two_button:
                savepasswordValue(g_password_flag, "2");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_three_button:
                savepasswordValue(g_password_flag, "3");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_four_button:
                savepasswordValue(g_password_flag, "4");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_five_button:
                savepasswordValue(g_password_flag, "5");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_six_button:
                savepasswordValue(g_password_flag, "6");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_seven_button:
                savepasswordValue(g_password_flag, "7");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_eight_button:
                savepasswordValue(g_password_flag, "8");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_nine_button:
                savepasswordValue(g_password_flag, "9");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_zero_button:
                savepasswordValue(g_password_flag, "0");
                changeGreyToBlue();
                changeFlag();
                break;
            case R.id.apps_password_delete_button:
                deletepasswordDigit();
                break;
        }
    }

    private void savepasswordValue(String p_password_flag, String p_value){
        if (p_password_flag.equals("zero")){
            tmp_password = tmp_password + p_value;
        }else if (p_password_flag.equals("first")){
            tmp_password = tmp_password + p_value;
        }else if (p_password_flag.equals("second")){
            tmp_password = tmp_password + p_value;
        }else if (p_password_flag.equals("third")){
            tmp_password = tmp_password + p_value;
        }else if (p_password_flag.equals("fourth")){
            tmp_password = tmp_password + p_value;
        }else if (p_password_flag.equals("fivth")){
            tmp_password = tmp_password + p_value;
        }else if (p_password_flag.equals("sixth")){

        }
    }

    private void changeFlag(){
        if (g_password_flag.equals("zero")){
            g_password_flag = "first";
        }else if (g_password_flag.equals("first")){
            g_password_flag = "second";
        }else if (g_password_flag.equals("second")){
            g_password_flag = "third";
        }else if (g_password_flag.equals("third")){
            g_password_flag = "fourth";
        }else if (g_password_flag.equals("fourth")){
            g_password_flag = "fivth";
        }else if (g_password_flag.equals("fivth")){
            g_password_flag = "sixth";
            SharedPreferences l_preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
            if (l_preferences.contains(BOS_ID)){
                String l_bos_id = l_preferences.getString(BOS_ID,"");
                VolleyClass.loginByPassword(this, l_bos_id, tmp_password);
            }else {
                VolleyClass.loginByPassword(this, g_bos_id, tmp_password);
                saveStringSharedPreference(BOS_ID, g_bos_id);
            }
        }else if (g_password_flag.equals("sixth")){

        }
    }


    private void changeGreyToBlue(){
        if (g_password_flag.equals("zero")){
            g_password_iv_first_digit.setImageResource(R.drawable.style_gradient_color_circle_blue);
        }else if (g_password_flag.equals("first")){
            g_password_iv_second_digit.setImageResource(R.drawable.style_gradient_color_circle_blue);
        }else if (g_password_flag.equals("second")){
            g_password_iv_third_digit.setImageResource(R.drawable.style_gradient_color_circle_blue);
        }else if (g_password_flag.equals("third")){
            g_password_iv_fourth_digit.setImageResource(R.drawable.style_gradient_color_circle_blue);
        }else if (g_password_flag.equals("fourth")){
            g_password_iv_fivth_digit.setImageResource(R.drawable.style_gradient_color_circle_blue);
        }else if (g_password_flag.equals("fivth")){
            g_password_iv_sixth_digit.setImageResource(R.drawable.style_gradient_color_circle_blue);
        }else if (g_password_flag.equals("sixth")){

        }
    }

    private void deletepasswordDigit(){
        if (g_password_flag.equals("zero")){

        }else if (g_password_flag.equals("first")){
            g_password_iv_first_digit.setImageResource(R.drawable.style_grey_color_circle_stroke);
            tmp_password = deleteLastCharacter(tmp_password);
            g_password_flag = "zero";
        }else if (g_password_flag.equals("second")){
            g_password_iv_second_digit.setImageResource(R.drawable.style_grey_color_circle_stroke);
            tmp_password = deleteLastCharacter(tmp_password);
            g_password_flag = "first";
        }else if (g_password_flag.equals("third")){
            g_password_iv_third_digit.setImageResource(R.drawable.style_grey_color_circle_stroke);
            tmp_password = deleteLastCharacter(tmp_password);
            g_password_flag = "second";
        }else if (g_password_flag.equals("fourth")){
            g_password_iv_fourth_digit.setImageResource(R.drawable.style_grey_color_circle_stroke);
            tmp_password = deleteLastCharacter(tmp_password);
            g_password_flag = "third";
        }else if (g_password_flag.equals("fivth")){
            g_password_iv_fivth_digit.setImageResource(R.drawable.style_grey_color_circle_stroke);
            tmp_password = deleteLastCharacter(tmp_password);
            g_password_flag = "fourth";
        }else if (g_password_flag.equals("sixth")){
            g_password_iv_sixth_digit.setImageResource(R.drawable.style_grey_color_circle_stroke);
            tmp_password = deleteLastCharacter(tmp_password);
            g_password_tv_error.setText("");
            g_password_flag = "fivth";
        }
    }

    private String deleteLastCharacter(String p_str) {
        if (p_str != null && p_str.length() > 0) {
            p_str = p_str.substring(0, p_str.length() - 1);
        }
        return p_str;
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

    public void intentLogin(int p_id_seller){
        Intent tmp_login_intent = new Intent(PasswordActivity.this, ApplicationContainer.class);

        //menyimpan id seller dalam Shared Preference
        saveIntegerSharedPreference(SELLER_ID, p_id_seller);

        startActivity(tmp_login_intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }

    public void setErrorPasswordSalah(){
        g_password_tv_error.setText("Password Salah");
    }

    public void setErrorIDSalah(){
        g_password_tv_error.setText("ID Salah");
    }

}
