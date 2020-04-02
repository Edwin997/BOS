package com.example.bca_bos.ui.produk;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;

public class ChooseImageFromDialog implements View.OnClickListener {

    private final String KEY_OPEN_CAMERA = "From Camera";
    private final String KEY_OPEN_GALLERY = "From Gallery";

    public final static int CODE_CAMERA_ADD_PRODUK = 11;
    public final static int CODE_GALLERY_ADD_PRODUK = 12;
    public final static int CODE_CAMERA_EDIT_PRODUK = 21;
    public final static int CODE_GALLERY_EDIT_PRODUK = 22;
    public final static int CODE_CAMERA_EDIT_PROFILE = 31;
    public final static int CODE_GALLERY_EDIT_PROFILE = 32;

    //DIALOG ADD PHOTO IN ADD PRODUCT
    private Dialog g_dialog_add;

    private LinearLayout g_dialog_dari_camera_layout_add, g_dialog_dari_gallery_layout_add;
    private Button g_btn_cancel_add;

    //DIALOG ADD PHOTO IN EDIT PRODUCT
    private Dialog g_dialog_edit;

    private LinearLayout g_dialog_dari_camera_layout_edit, g_dialog_dari_gallery_layout_edit;
    private Button g_btn_cancel_edit;

    //DIALOG ADD PHOTO IN EDIT PROFILE
    private Dialog g_dialog_edit_profile;

    private LinearLayout g_dialog_dari_camera_layout_edit_profile, g_dialog_dari_gallery_layout_edit_profile;
    private Button g_btn_cancel_edit_profile;

    private OnCallBackListener g_callback_parent_listener;

    public ChooseImageFromDialog(OnCallBackListener p_parent){
        g_callback_parent_listener = p_parent;
    }

    public void showChooseDialogAdd(final Context p_context){
        g_dialog_add = new Dialog(p_context);
        g_dialog_add.setContentView(R.layout.layout_dialog_choose_image_produk);

        g_dialog_dari_camera_layout_add = g_dialog_add.findViewById(R.id.item_dialog_dari_camera_layout);
        g_dialog_dari_gallery_layout_add = g_dialog_add.findViewById(R.id.item_dialog_dari_gallery_layout);

        g_btn_cancel_add = g_dialog_add.findViewById(R.id.btn_dialog_choose_cancel);

        g_dialog_dari_camera_layout_add.setOnClickListener(this);
        g_dialog_dari_gallery_layout_add.setOnClickListener(this);

        g_btn_cancel_add.setOnClickListener(this);

        g_dialog_add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_dialog_add.show();
    }

    public void showChooseDialogEdit(final Context p_context){
        g_dialog_edit = new Dialog(p_context);
        g_dialog_edit.setContentView(R.layout.layout_dialog_choose_image_produk);

        g_dialog_dari_camera_layout_edit = g_dialog_edit.findViewById(R.id.item_dialog_dari_camera_layout);
        g_dialog_dari_gallery_layout_edit = g_dialog_edit.findViewById(R.id.item_dialog_dari_gallery_layout);

        g_btn_cancel_edit = g_dialog_edit.findViewById(R.id.btn_dialog_choose_cancel);

        g_dialog_dari_camera_layout_edit.setOnClickListener(this);
        g_dialog_dari_gallery_layout_edit.setOnClickListener(this);

        g_btn_cancel_edit.setOnClickListener(this);

        g_dialog_edit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_dialog_edit.show();
    }

    public void showChooseDialogEditProfile(final Context p_context){
        g_dialog_edit_profile = new Dialog(p_context);
        g_dialog_edit_profile.setContentView(R.layout.layout_dialog_choose_image_produk);

        g_dialog_dari_camera_layout_edit_profile = g_dialog_edit_profile.findViewById(R.id.item_dialog_dari_camera_layout);
        g_dialog_dari_gallery_layout_edit_profile = g_dialog_edit_profile.findViewById(R.id.item_dialog_dari_gallery_layout);

        g_btn_cancel_edit_profile = g_dialog_edit_profile.findViewById(R.id.btn_dialog_choose_cancel);

        g_dialog_dari_camera_layout_edit_profile.setOnClickListener(this);
        g_dialog_dari_gallery_layout_edit_profile.setOnClickListener(this);

        g_btn_cancel_edit_profile.setOnClickListener(this);

        g_dialog_edit_profile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_dialog_edit_profile.show();
    }

    @Override
    public void onClick(View v) {
        //DIALOG ADD PHOTO IN ADD PRODUCT
        if(v == g_dialog_dari_gallery_layout_add){
            if(g_callback_parent_listener != null) {
                g_dialog_add.dismiss();
                g_callback_parent_listener.OnCallBack(CODE_GALLERY_ADD_PRODUK);
            }
        }
        else if (v == g_dialog_dari_camera_layout_add){
            if(g_callback_parent_listener != null) {
                g_dialog_add.dismiss();
                g_callback_parent_listener.OnCallBack(CODE_CAMERA_ADD_PRODUK);
            }
        }
        else if (v == g_btn_cancel_add){
            g_dialog_add.dismiss();
        }

        //DIALOG ADD PHOTO IN EDIT PRODUCT
        else if(v == g_dialog_dari_gallery_layout_edit){
            if(g_callback_parent_listener != null) {
                g_dialog_edit.dismiss();
                g_callback_parent_listener.OnCallBack(CODE_GALLERY_EDIT_PRODUK);
            }
        }
        else if (v == g_dialog_dari_camera_layout_edit){
            if(g_callback_parent_listener != null) {
                g_dialog_edit.dismiss();
                g_callback_parent_listener.OnCallBack(CODE_CAMERA_EDIT_PRODUK);
            }
        }
        else if (v == g_btn_cancel_edit){
            g_dialog_edit.dismiss();
        }

        //DIALOG ADD PHOTO IN EDIT PROFILE
        else if(v == g_dialog_dari_gallery_layout_edit_profile){
            if(g_callback_parent_listener != null) {
                g_dialog_edit_profile.dismiss();
                g_callback_parent_listener.OnCallBack(CODE_GALLERY_EDIT_PROFILE);
            }
        }
        else if (v == g_dialog_dari_camera_layout_edit_profile){
            if(g_callback_parent_listener != null) {
                g_dialog_edit_profile.dismiss();
                g_callback_parent_listener.OnCallBack(CODE_CAMERA_EDIT_PROFILE);
            }
        }
        else if (v == g_btn_cancel_edit_profile){
            g_dialog_edit_profile.dismiss();
        }
    }
}
