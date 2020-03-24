package com.example.bca_bos.ui.template;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.dummy.ListTemplatedTextDummy;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.TemplatedText;
import com.example.bca_bos.networks.VolleyClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TemplateFragment extends Fragment implements View.OnClickListener, OnCallBackListener, PopupMenu.OnMenuItemClickListener {

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private TemplateAdapter g_templateadapter;
    private RecyclerView g_templatefragment_recyclerview;

    private LinearLayout g_ll_template_fragment_add_button;
    private View g_view;

    private PopupMenu g_popup;

    private ImageView g_templatedtext_fragment_sort_btn, g_templatedtext_fragment_search_btn;
    private EditText g_templatedtext_fragment_search_et;

    private BottomSheetDialog g_bottomsheet_dialog_add;
    private BottomSheetDialog g_bottomsheet_dialog_edit;

    public static TemplateFragment g_instance;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_template, container, false);
        g_instance = this;

        g_templatefragment_recyclerview = g_view.findViewById(R.id.apps_template_fragment_recyclerview);
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_templateadapter = new TemplateAdapter();
        g_templateadapter.setParentOnCallBack(this);

        refreshData();

        g_templatefragment_recyclerview.setAdapter(g_templateadapter);
        g_templatefragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        g_ll_template_fragment_add_button = g_view.findViewById(R.id.ll_apps_template_tambah_btn);
        g_ll_template_fragment_add_button.setOnClickListener(this);

        //inisiasi imageview
        g_templatedtext_fragment_sort_btn = g_view.findViewById(R.id.apps_template_fragment_sort_btn);
        g_templatedtext_fragment_search_btn = g_view.findViewById(R.id.apps_template_fragment_search_btn);

        //inisiasi edittext
        g_templatedtext_fragment_search_et = g_view.findViewById(R.id.apps_template_fragment_search_et);

        //config imageview
        g_templatedtext_fragment_sort_btn.setOnClickListener(this);
        g_templatedtext_fragment_search_btn.setOnClickListener(this);

        //config edittext
        g_templatedtext_fragment_search_et.addTextChangedListener(new SearchTextWatcher(g_templatedtext_fragment_search_et));

        g_bottomsheet_dialog_add = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        g_bottomsheet_dialog_edit = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

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
                    g_bottomsheet_dialog_add.dismiss();
                }
            });

            l_btn_templated_batal_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    g_bottomsheet_dialog_add.dismiss();
                }
            });

            g_bottomsheet_dialog_add.setContentView(l_bottomsheet_view_add);
            g_bottomsheet_dialog_add.show();
        }
        else if(v == g_templatedtext_fragment_search_btn){
            g_templateadapter.findTemplatedText(g_templatedtext_fragment_search_et.getText().toString());
        }
        else if(v == g_templatedtext_fragment_sort_btn){
            showSortPopupMenu(v);
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

                    g_bottomsheet_dialog_edit.dismiss();
                }
            });

            l_btn_templated_hapus_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolleyClass.deleteTemplatedText(g_context, l_template.getId_template_text(), g_templateadapter);
                    g_bottomsheet_dialog_edit.dismiss();
                }
            });

            g_bottomsheet_dialog_edit.setContentView(l_bottomsheet_view_edit);
            g_bottomsheet_dialog_edit.show();
        }
    }

    public void refreshData(){
        g_templateadapter.setListTemplate(ListTemplatedTextDummy.templatedTextList);
//        VolleyClass.getTemplatedText(g_context, 3, g_templateadapter);
    }

    public void showSortPopupMenu(View p_view) {
        g_popup = new PopupMenu(g_context, p_view);
        MenuInflater inflater = g_popup.getMenuInflater();
        inflater.inflate(R.menu.produk_fragment_bar, g_popup.getMenu());
        g_popup.setOnMenuItemClickListener(this);
        g_popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.apps_produk_item_sort_asc:
                g_templateadapter.sortTemplatedText("ASC");
                break;
            case R.id.apps_produk_item_sort_desc:
                g_templateadapter.sortTemplatedText("DESC");
                break;
        }
        return true;
    }

    private class SearchTextWatcher implements TextWatcher {

        private EditText l_edittext;

        private int l_delay = 1000;
        private long l_time_last_editted = 0;
        private Handler l_handler;

        private Runnable l_thread_show_search_result = new Runnable() {
            public void run() {
                if (l_edittext != null) {
                    if (System.currentTimeMillis() > (l_time_last_editted + l_delay - 500)) {
                        g_templateadapter.findTemplatedText(l_edittext.getText().toString());
                    }
                }
            }
        };

        public SearchTextWatcher(EditText p_edittext){
            l_edittext = p_edittext;
            l_handler = new Handler();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(final Editable editable) {
            if(l_edittext != null){
                l_time_last_editted = System.currentTimeMillis();
                l_handler.postDelayed(l_thread_show_search_result, l_delay);
            }
        }
    }
}