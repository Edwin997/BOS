package com.example.bca_bos;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class Method {

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
}
