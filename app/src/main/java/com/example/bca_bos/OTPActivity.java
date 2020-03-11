package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    //Masked Password
    ImageView g_otp_iv_first_digit, g_otp_iv_second_digit, g_otp_iv_third_digit, g_otp_iv_fourth_digit;

    //Keyboard
    Button g_otp_btn_one, g_otp_btn_two, g_otp_btn_three,
            g_otp_btn_four, g_otp_btn_five, g_otp_btn_six,
            g_otp_btn_seven,  g_otp_btn_eight, g_otp_btn_nine,
            g_otp_btn_zero;
    ImageButton g_otp_btn_delete;

    String tmp_otp, g_otp_flag;

    TextView g_otp_tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        g_otp_flag = "zero";
        tmp_otp = "";

        //Masked Password
        g_otp_iv_first_digit = findViewById(R.id.apps_otp_first_digit_image_view);
        g_otp_iv_second_digit = findViewById(R.id.apps_otp_second_digit_image_view);
        g_otp_iv_third_digit = findViewById(R.id.apps_otp_third_digit_image_view);
        g_otp_iv_fourth_digit = findViewById(R.id.apps_otp_fourth_digit_image_view);

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

        //Error
        g_otp_tv_error = findViewById(R.id.apps_otp_error_text_view);
        g_otp_tv_error.setText("");


    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.apps_otp_one_button:
                saveOTPValue(g_otp_flag, "1");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_two_button:
                saveOTPValue(g_otp_flag, "2");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_three_button:
                saveOTPValue(g_otp_flag, "3");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_four_button:
                saveOTPValue(g_otp_flag, "4");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_five_button:
                saveOTPValue(g_otp_flag, "5");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_six_button:
                saveOTPValue(g_otp_flag, "6");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_seven_button:
                saveOTPValue(g_otp_flag, "7");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_eight_button:
                saveOTPValue(g_otp_flag, "8");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_nine_button:
                saveOTPValue(g_otp_flag, "9");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_zero_button:
                saveOTPValue(g_otp_flag, "0");
                changeMaskedPasswordBackground();
                changeFlag();
                break;
            case R.id.apps_otp_delete_button:
                deleteOTPDigit();
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
            if (tmp_otp.equals("1234")){
                moveToMainMenuActivity();
            }else {
                g_otp_tv_error.setText("Wrong Password");
            }
        }else if (g_otp_flag.equals("fourth")){

        }
    }

    private void changeMaskedPasswordBackground(){
        if (g_otp_flag.equals("zero")){
            g_otp_iv_first_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_blue));
        }else if (g_otp_flag.equals("first")){
            g_otp_iv_second_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_blue));
        }else if (g_otp_flag.equals("second")){
            g_otp_iv_third_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_blue));
        }else if (g_otp_flag.equals("third")){
            g_otp_iv_fourth_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_blue));
        }else if (g_otp_flag.equals("fourth")){

        }
    }

    private void deleteOTPDigit(){
        if (g_otp_flag.equals("zero")){

        }else if (g_otp_flag.equals("first")){
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_iv_first_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_outline));
            g_otp_tv_error.setText("");
            g_otp_flag = "zero";
        }else if (g_otp_flag.equals("second")){
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_iv_second_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_outline));
            g_otp_tv_error.setText("");
            g_otp_flag = "first";
        }else if (g_otp_flag.equals("third")){
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_iv_third_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_outline));
            g_otp_tv_error.setText("");
            g_otp_flag = "second";
        }else if (g_otp_flag.equals("fourth")){
            tmp_otp = deleteLastCharacter(tmp_otp);
            g_otp_iv_fourth_digit.setBackground(this.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_outline));
            g_otp_tv_error.setText("");
            g_otp_flag = "third";
        }
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
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finish();
    }

}
