package com.example.bca_bos.ui.template;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.TemplatedText;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TemplateFragment extends Fragment implements View.OnClickListener, OnCallBackListener {

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private TemplateAdapter g_templateadapter;
    private RecyclerView g_templatefragment_recyclerview;

    private ImageButton g_ib_template_fragment_add;
    private View g_view;

    private BottomSheetDialog g_bottomsheet_dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_template, container, false);

        g_templatefragment_recyclerview = g_view.findViewById(R.id.apps_template_fragment_recyclerview);
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_templateadapter = new TemplateAdapter();
        g_templateadapter.setParentOnCallBack(this);

        g_templatefragment_recyclerview.setAdapter(g_templateadapter);
        g_templatefragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        g_ib_template_fragment_add = g_view.findViewById(R.id.apps_template_fragment_add_btn);
        g_ib_template_fragment_add.setOnClickListener(this);

        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        return g_view;
    }

    @Override
    public void onClick(View v) {
        if(v == g_ib_template_fragment_add){
            View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                    R.layout.layout_bottom_sheet_add_template,
                    (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_add)
            );

            EditText l_et_edit_templated_label_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_label_add);
            EditText l_et_edit_templated_deskripsi_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_deskripsi_add);
            Button l_btn_templated_simpan_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_simpan_add);

            l_btn_templated_simpan_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(g_context, "YOOOOO", Toast.LENGTH_SHORT).show();
                    g_bottomsheet_dialog.dismiss();
                }
            });

            g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
            g_bottomsheet_dialog.show();
        }
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof TemplatedText){
            TemplatedText l_template = (TemplatedText) p_obj;

            View l_bottomsheet_view_edit = LayoutInflater.from(g_context).inflate(
                    R.layout.layout_bottom_sheet_edit_template,
                    (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit)
            );

            EditText l_et_edit_templated_label_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_label_edit);
            EditText l_et_edit_templated_deskripsi_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_deskripsi_edit);
            Button l_btn_templated_simpan_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_simpan_edit);

            l_et_edit_templated_label_edit.setText(l_template.getLabel());
            l_et_edit_templated_deskripsi_edit.setText(l_template.getDescription());

            l_btn_templated_simpan_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(g_context, "YOOOOO", Toast.LENGTH_SHORT).show();
                    g_bottomsheet_dialog.dismiss();
                }
            });

            g_bottomsheet_dialog.setContentView(l_bottomsheet_view_edit);
            g_bottomsheet_dialog.show();
        }
    }
}