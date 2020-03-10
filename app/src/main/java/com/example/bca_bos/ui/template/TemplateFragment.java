package com.example.bca_bos.ui.template;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.TemplatedText;
import com.example.bca_bos.networks.VolleyClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class TemplateFragment extends Fragment implements View.OnClickListener, OnCallBackListener {

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private TemplateAdapter g_templateadapter;
    private RecyclerView g_templatefragment_recyclerview;

    private LinearLayout g_ll_template_fragment_add_button;
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

        refreshData();

        g_templatefragment_recyclerview.setAdapter(g_templateadapter);
        g_templatefragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        g_ll_template_fragment_add_button = g_view.findViewById(R.id.ll_apps_template_tambah_btn);
        g_ll_template_fragment_add_button.setOnClickListener(this);

        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        return g_view;
    }

    @Override
    public void onClick(View v) {
        if(v == g_ll_template_fragment_add_button){
            View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                    R.layout.layout_bottom_sheet_add_template,
                    (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_add)
            );

            final EditText l_et_edit_templated_label_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_label_add);
            final EditText l_et_edit_templated_deskripsi_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_deskripsi_add);
            Button l_btn_templated_simpan_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_simpan_add);
            Button l_btn_templated_batal_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_batal_add);

            l_btn_templated_simpan_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Seller s = new Seller();
                    s.setId_seller(3);
                    TemplatedText tmpTemplate = new TemplatedText();
                    tmpTemplate.setTemplate_code(l_et_edit_templated_label_add.getText().toString());
                    tmpTemplate.setText(l_et_edit_templated_deskripsi_add.getText().toString());
                    tmpTemplate.setSeller(s);
                    VolleyClass.insertTemplatedText(g_context, tmpTemplate, g_templateadapter);
                    refreshData();
                    g_bottomsheet_dialog.dismiss();
                }
            });

            l_btn_templated_batal_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            final TemplatedText l_template = (TemplatedText) p_obj;

            View l_bottomsheet_view_edit = LayoutInflater.from(g_context).inflate(
                    R.layout.layout_bottom_sheet_edit_template,
                    (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit)
            );

            final EditText l_et_edit_templated_label_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_label_edit);
            final EditText l_et_edit_templated_deskripsi_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_deskripsi_edit);
            Button l_btn_templated_simpan_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_simpan_edit);
            Button l_btn_templated_hapus_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_hapus_edit);

            l_et_edit_templated_label_edit.setText(l_template.getTemplate_code());
            l_et_edit_templated_deskripsi_edit.setText(l_template.getText());



            l_btn_templated_simpan_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Seller s = new Seller();
                    s.setId_seller(3);
                    TemplatedText tmpTemplate = new TemplatedText();
                    tmpTemplate.setId_template_text(l_template.getId_template_text());
                    tmpTemplate.setTemplate_code(l_et_edit_templated_label_edit.getText().toString());
                    tmpTemplate.setText(l_et_edit_templated_deskripsi_edit.getText().toString());
                    tmpTemplate.setSeller(s);
                    VolleyClass.updateTemplatedText(g_context, tmpTemplate, g_templateadapter);
                    refreshData();
                    g_bottomsheet_dialog.dismiss();
                }
            });

            l_btn_templated_hapus_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolleyClass.deleteTemplatedText(g_context, l_template.getId_template_text(), g_templateadapter);
                    g_bottomsheet_dialog.dismiss();
                }
            });

            g_bottomsheet_dialog.setContentView(l_bottomsheet_view_edit);
            g_bottomsheet_dialog.show();
        }
    }

    public void refreshData(){
        VolleyClass.getTemplatedText(g_context, 1, g_templateadapter);
    }
}