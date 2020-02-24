package com.example.bca_bos.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bca_bos.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ProfileFragment extends Fragment {

    private Context g_context;
    View g_view;

    private ProfileViewModel profileViewModel;
    private ImageButton g_profile_ib_edit;

    private BottomSheetDialog g_bottomsheet_dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_profile, container, false);

        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        g_profile_ib_edit = g_view.findViewById(R.id.profile_edit_button);
        g_profile_ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View l_bottomsheet_view_save = LayoutInflater.from(g_context).inflate(
                        R.layout.layout_bottom_sheet_edit_profile,
                        (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit_profile)
                );

                g_bottomsheet_dialog.setContentView(l_bottomsheet_view_save);
                g_bottomsheet_dialog.show();

            }
        });

        return g_view;
    }
}