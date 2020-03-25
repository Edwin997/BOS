package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    TextView g_otp_tv_error;
    String tmp_otp, g_otp_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        g_otp_flag = "zero";
        tmp_otp = "";

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
            if (tmp_otp.equals("1111")){
                moveToMainMenuActivity();
            }else {
                g_otp_tv_error.setText("Wrong OTP");
            }
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

    private void moveToMainMenuActivity(){
        Intent tmp_register_intent = new Intent(OTPActivity.this, FillDataActivity.class);
        startActivity(tmp_register_intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        closeRegisterActivity();
        finish();
    }

    private void closeRegisterActivity(){
        if(RegisterActivity.registerInstance != null) {
            try {
                RegisterActivity.registerInstance.finish();
            } catch (Exception e) {}
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent tmp_back_intent = new Intent(OTPActivity.this, RegisterActivity.class);
        startActivity(tmp_back_intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        finish();
    }
}
