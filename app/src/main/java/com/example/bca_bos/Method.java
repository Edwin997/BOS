package com.example.bca_bos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Method {

    public static String ASC = "asc";
    public static String DESC = "desc";

    private static Locale tmpLocale = new Locale("in", "ID");
    private static NumberFormat l_currencyformat = NumberFormat.getCurrencyInstance(tmpLocale);

    public static void callback(final AppCompatActivity p_activity){

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                p_activity.finish();
            }
        };
        p_activity.getOnBackPressedDispatcher().addCallback(callback);

    }

    public static String getIndoCurrency(int p_number){
        return l_currencyformat.format(p_number);
    }

    public static String getIndoCurrency(double p_number){
        return l_currencyformat.format(p_number);
    }

    public static String formatDate(String p_date){
        try {
            SimpleDateFormat tmpFormatStringToDate = new SimpleDateFormat("yyyy-MM-dd");
            Date tmpDate = tmpFormatStringToDate.parse(p_date);

            tmpFormatStringToDate = new SimpleDateFormat("dd MMMM yyyy", tmpLocale);
            return tmpFormatStringToDate.format(tmpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap convertToBitmap(String p_string){
        byte[] decodedString = Base64.decode(p_string, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }

    public static boolean cekValidasi(List<EditText> p_isi, List<TextView> p_error){
        boolean tmpCheck = true;

        for(int i = 0; i < p_isi.size(); i++){
            if(p_isi.get(i).getText().toString().isEmpty()){
                tmpCheck = false;
                p_error.get(i).setText("Mohon diisi terlebih dahulu");
            }
            else{
                p_error.get(i).setText("");
            }
        }

        return tmpCheck;
    }
}
