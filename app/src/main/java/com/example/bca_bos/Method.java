package com.example.bca_bos;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}
