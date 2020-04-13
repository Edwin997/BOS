package com.example.bca_bos;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogBoxWarning {

    static Dialog g_dialog;
    public static void showWarning(Context p_context, String p_judul,
                                   View.OnClickListener p_event_yes, View.OnClickListener p_event_no){
        g_dialog = new Dialog(p_context);
        g_dialog.setContentView(R.layout.layout_dialog_warning);

        Button g_btn_yes = g_dialog.findViewById(R.id.btn_dialog_warning_yes);
        Button g_btn_no = g_dialog.findViewById(R.id.btn_dialog_warning_no);

        TextView g_tv_judul = g_dialog.findViewById(R.id.tv_dialog_warning_judul);

        g_tv_judul.setText(p_judul);

        g_btn_yes.setOnClickListener(p_event_yes);
        g_btn_no.setOnClickListener(p_event_no);

        g_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_dialog.show();
    }

    public static void dismissWarning(){
        if(g_dialog != null){
            g_dialog.dismiss();
            g_dialog = null;
        }
    }
}
