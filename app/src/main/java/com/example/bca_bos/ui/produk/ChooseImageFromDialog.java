package com.example.bca_bos.ui.produk;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;

public class ChooseImageFromDialog {

    private final String KEY_OPEN_CAMERA = "From Camera";
    private final String KEY_OPEN_GALLERY = "From Gallery";

    public final static int CODE_CAMERA_ADD_PRODUK = 11;
    public final static int CODE_GALLERY_ADD_PRODUK = 12;
    public final static int CODE_CAMERA_EDIT_PRODUK = 21;
    public final static int CODE_GALLERY_EDIT_PRODUK = 22;

    private OnCallBackListener g_callback_parent_listener;

    public ChooseImageFromDialog(OnCallBackListener p_parent){
        g_callback_parent_listener = p_parent;
    }

    public void showChooseDialogAdd(final Context p_context){
        AlertDialog.Builder tmpAlertDialogBuilder = new AlertDialog.Builder(p_context);
        tmpAlertDialogBuilder.setTitle("Choose photo from:");

        final ArrayAdapter<String> chooseDialogAdapter = new ArrayAdapter(p_context, android.R.layout.select_dialog_item);
        chooseDialogAdapter.add(KEY_OPEN_CAMERA);
        chooseDialogAdapter.add(KEY_OPEN_GALLERY);

        tmpAlertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        tmpAlertDialogBuilder.setAdapter(chooseDialogAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String tmpChoice = chooseDialogAdapter.getItem(position);
                if(g_callback_parent_listener != null){
                    switch (tmpChoice){
                        case KEY_OPEN_CAMERA:
                            g_callback_parent_listener.OnCallBack(CODE_CAMERA_ADD_PRODUK);
                            break;
                        case KEY_OPEN_GALLERY:
                            g_callback_parent_listener.OnCallBack(CODE_GALLERY_ADD_PRODUK);
                            break;
                    }
                }
            }
        });
        tmpAlertDialogBuilder.show();
    }

    public void showChooseDialogEdit(final Context p_context){
        AlertDialog.Builder tmpAlertDialogBuilder = new AlertDialog.Builder(p_context);
        tmpAlertDialogBuilder.setTitle("Choose photo from:");

        final ArrayAdapter<String> chooseDialogAdapter = new ArrayAdapter(p_context, android.R.layout.select_dialog_item);
        chooseDialogAdapter.add(KEY_OPEN_CAMERA);
        chooseDialogAdapter.add(KEY_OPEN_GALLERY);

        tmpAlertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        tmpAlertDialogBuilder.setAdapter(chooseDialogAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String tmpChoice = chooseDialogAdapter.getItem(position);
                if(g_callback_parent_listener != null){
                    switch (tmpChoice){
                        case KEY_OPEN_CAMERA:
                            g_callback_parent_listener.OnCallBack(CODE_CAMERA_EDIT_PRODUK);
                            break;
                        case KEY_OPEN_GALLERY:
                            g_callback_parent_listener.OnCallBack(CODE_GALLERY_EDIT_PRODUK);
                            break;
                    }
                }
            }
        });
        tmpAlertDialogBuilder.show();
    }
}
