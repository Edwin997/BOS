package com.example.bca_bos.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bca_bos.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Context g_context;
    private Boolean IS_CHOOSE_JNE = false, IS_CHOOSE_TIKI = false, IS_CHOOSE_POS = false;
    View g_view;

    private ImageButton g_profile_ib_edit;
    private Button g_profile_btn_change_password, g_profile_button_jne, g_profile_button_tiki, g_profile_button_pos;

    //BottomSheet
    private BottomSheetDialog g_bottomsheet_dialog_edit_profile, g_bottomsheet_dialog_change_password;
    private Button g_bottomsheet_simpan_profil, g_bottomsheet_simpan_password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_profile, container, false);

        g_bottomsheet_dialog_edit_profile = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        g_bottomsheet_dialog_change_password = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        g_profile_ib_edit = g_view.findViewById(R.id.profile_edit_button);
        g_profile_ib_edit.setOnClickListener(this);

        g_profile_btn_change_password = g_view.findViewById(R.id.profile_change_password_button);
        g_profile_btn_change_password.setOnClickListener(this);


        return g_view;
    }

    @Override
    public void onClick(View p_view) {

        switch (p_view.getId()){
            case R.id.profile_edit_button:
                View l_bottomsheet_view_edit_profile = LayoutInflater.from(g_context).inflate(
                        R.layout.layout_bottom_sheet_edit_profile,
                        (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit_profile)
                );

                g_profile_button_jne = l_bottomsheet_view_edit_profile.findViewById(R.id.profile_jne_button);
                g_profile_button_tiki = l_bottomsheet_view_edit_profile.findViewById(R.id.profile_tiki_button);
                g_profile_button_pos = l_bottomsheet_view_edit_profile.findViewById(R.id.profile_pos_button);
                g_bottomsheet_simpan_profil = l_bottomsheet_view_edit_profile.findViewById(R.id.apps_bottom_sheet_btn_simpan_profil);

                g_profile_button_jne.setOnClickListener(this);
                g_profile_button_tiki.setOnClickListener(this);
                g_profile_button_pos.setOnClickListener(this);
                g_bottomsheet_simpan_profil.setOnClickListener(this);

                g_bottomsheet_dialog_edit_profile.setContentView(l_bottomsheet_view_edit_profile);
                g_bottomsheet_dialog_edit_profile.show();
                break;
            case R.id.profile_change_password_button:
                View l_bottomsheet_view_change_password = LayoutInflater.from(g_context).inflate(
                        R.layout.layout_bottom_sheet_change_password,
                        (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_change_password)
                );

                g_bottomsheet_simpan_password = l_bottomsheet_view_change_password.findViewById(R.id.apps_bottom_sheet_btn_simpan_password);
                g_bottomsheet_simpan_password.setOnClickListener(this);

                g_bottomsheet_dialog_change_password.setContentView(l_bottomsheet_view_change_password);
                g_bottomsheet_dialog_change_password.show();
                break;
            case R.id.profile_jne_button:
                IS_CHOOSE_JNE = !IS_CHOOSE_JNE;
                configChooseCourierButton(p_view, IS_CHOOSE_JNE);
                break;
            case R.id.profile_tiki_button:
                IS_CHOOSE_TIKI = !IS_CHOOSE_TIKI;
                configChooseCourierButton(p_view, IS_CHOOSE_TIKI);
                break;
            case R.id.profile_pos_button:
                IS_CHOOSE_POS = !IS_CHOOSE_POS;
                configChooseCourierButton(p_view, IS_CHOOSE_POS);
                break;
            case R.id.apps_bottom_sheet_btn_simpan_profil:
                g_bottomsheet_dialog_edit_profile.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_simpan_password:
                g_bottomsheet_dialog_change_password.dismiss();
                break;
        }
    }

    private void configChooseCourierButton(View p_view, boolean p_choose){
        if (p_choose){
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box);
        }else {
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        }
    }

}