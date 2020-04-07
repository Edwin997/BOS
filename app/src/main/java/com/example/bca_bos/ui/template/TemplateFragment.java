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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.TemplatedText;
import com.example.bca_bos.networks.VolleyClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TemplateFragment extends Fragment implements View.OnClickListener, OnCallBackListener, PopupMenu.OnMenuItemClickListener {

    //region DATA MEMBER

    //VIEW DATA MEMBER
    public static TemplateFragment g_instance;

    private Context g_context;
    private View g_view;
    private BottomSheetDialog g_bottomsheet_dialog_add;
    private BottomSheetDialog g_bottomsheet_dialog_edit;
    private TemplatedText g_template_onclick;
    private PopupMenu g_popup;

    //FRAGMENT SECTION DATA MEMBER
    private LinearLayout g_ll_template_fragment_add_button;
    private ConstraintLayout g_template_fragment_not_found;

    private RecyclerView g_templatefragment_recyclerview;
    private TemplateAdapter g_templateadapter;
    private LinearLayoutManager g_linearlayoutmanager;

    private ImageView g_templatedtext_fragment_sort_btn, g_templatedtext_fragment_search_btn;
    private EditText g_templatedtext_fragment_search_et;

    private TextView g_tv_not_found_judul;
    private LottieAnimationView g_iv_not_found_animation;

    //ADD BOTTOM SHEET PRODUK DATA MEMBER
    private EditText g_et_edit_templated_label_add, g_et_edit_templated_deskripsi_add;
    private Button g_btn_templated_simpan_add, g_btn_templated_batal_add;

    //EDIT BOTTOM SHEET TEMPLATED TEXT DATA MEMBER
    private EditText g_et_edit_templated_label_edit, g_et_edit_templated_deskripsi_edit;
    private Button g_btn_templated_simpan_edit, g_btn_templated_hapus_edit;
    //endregion

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //inisiasi view, choose dialog, bottom sheet dialog
        g_instance = this;
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_template, container, false);
        g_bottomsheet_dialog_add = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        g_bottomsheet_dialog_edit = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        //inisiasi layout
        g_ll_template_fragment_add_button = g_view.findViewById(R.id.ll_apps_template_tambah_btn);
        g_template_fragment_not_found = g_view.findViewById(R.id.apps_template_fragment_not_found);

        //inisiasi recyclerview
        g_templatefragment_recyclerview = g_view.findViewById(R.id.apps_template_fragment_recyclerview);

        //inisiasi imageview
        g_templatedtext_fragment_sort_btn = g_view.findViewById(R.id.apps_template_fragment_sort_btn);
        g_templatedtext_fragment_search_btn = g_view.findViewById(R.id.apps_template_fragment_search_btn);

        //inisiasi edittext
        g_templatedtext_fragment_search_et = g_view.findViewById(R.id.apps_template_fragment_search_et);

        //inisiasi textview
        g_tv_not_found_judul = g_view.findViewById(R.id.apps_tv_not_found_judul);

        //inisiasi lottie
        g_iv_not_found_animation = g_view.findViewById(R.id.apps_iv_not_found_animation);

        //config layout
        showLayout(0, false);
        g_ll_template_fragment_add_button.setOnClickListener(this);

        //config recyclerview
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_templateadapter = new TemplateAdapter();
        g_templateadapter.setParentOnCallBack(this);
        g_templatefragment_recyclerview.setAdapter(g_templateadapter);
        g_templatefragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        refreshData();

        //config imageview
        g_templatedtext_fragment_sort_btn.setOnClickListener(this);
        g_templatedtext_fragment_search_btn.setOnClickListener(this);

        //config edittext
        g_templatedtext_fragment_search_et.addTextChangedListener(new SearchTextWatcher(g_templatedtext_fragment_search_et));

        return g_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_apps_template_tambah_btn:
                showBottomSheetAddTemplatedText();
                break;
            case R.id.apps_template_fragment_sort_btn:
                showSortPopupMenu(v);
                break;
            case R.id.apps_template_fragment_search_btn:
                g_templateadapter.findTemplatedText(g_templatedtext_fragment_search_et.getText().toString());
                break;

            //region ADD PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_btn_simpan_add:
                Seller seller_add = new Seller();
                seller_add.setId_seller(3);

                TemplatedText tmpTemplate = new TemplatedText();
                tmpTemplate.setTemplate_code(g_et_edit_templated_label_add.getText().toString());
                tmpTemplate.setText(g_et_edit_templated_deskripsi_add.getText().toString());
                tmpTemplate.setSeller(seller_add);

                VolleyClass.insertTemplatedText(g_context, tmpTemplate, g_templateadapter);
                refreshData();

                g_bottomsheet_dialog_add.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_batal_add:
                g_bottomsheet_dialog_add.dismiss();
                break;
            //endregion

            //region EDIT PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_btn_simpan_edit:
                Seller seller_edit = new Seller();
                seller_edit.setId_seller(3);

                TemplatedText tmpTemplateEdit = new TemplatedText();
                tmpTemplateEdit.setId_template_text(g_template_onclick.getId_template_text());
                tmpTemplateEdit.setTemplate_code(g_et_edit_templated_label_edit.getText().toString());
                tmpTemplateEdit.setText(g_et_edit_templated_deskripsi_edit.getText().toString());
                tmpTemplateEdit.setSeller(seller_edit);

                VolleyClass.updateTemplatedText(g_context, tmpTemplateEdit, g_templateadapter);

                g_bottomsheet_dialog_edit.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_hapus_edit:
                VolleyClass.deleteTemplatedText(g_context, g_template_onclick.getId_template_text(), g_templateadapter);
                g_bottomsheet_dialog_edit.dismiss();
                break;
            //endregion
        }
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof TemplatedText){
            g_template_onclick = (TemplatedText) p_obj;
            showBottomSheetEditTemplatedText(g_template_onclick);
        }
    }

    public void refreshData(){
//        g_templateadapter.setListTemplate(ListTemplatedTextDummy.templatedTextList);
        VolleyClass.getTemplatedTextByName(g_context, 3, Method.ASC, g_templateadapter);
    }

    private void changeLayoutValue(int p_count){
        if(p_count > 0){
            g_templatefragment_recyclerview.setVisibility(View.VISIBLE);
            g_template_fragment_not_found.setVisibility(View.GONE);
        }
        else{
            g_tv_not_found_judul.setText(getText(R.string.TEMPLATETEXT_NOT_FOUND));
            g_iv_not_found_animation.setAnimation(R.raw.no_templatetext_animation);
            g_iv_not_found_animation.playAnimation();
            g_iv_not_found_animation.loop(true);

            g_templatefragment_recyclerview.setVisibility(View.GONE);
            g_template_fragment_not_found.setVisibility(View.VISIBLE);
        }
    }

    public void showLayout(int p_count, boolean p_connect){
        if(p_connect){
            changeLayoutValue(p_count);
        }
        else{
            g_tv_not_found_judul.setText(getText(R.string.INTERNET_NOT_FOUND));
            g_iv_not_found_animation.setAnimation(R.raw.no_internet_animation);
            g_iv_not_found_animation.playAnimation();
            g_iv_not_found_animation.loop(true);

            g_templatefragment_recyclerview.setVisibility(View.GONE);
            g_template_fragment_not_found.setVisibility(View.VISIBLE);
        }
    }

    //region BOTTOM SHEET METHOD
    private void showBottomSheetEditTemplatedText(final TemplatedText p_template){
        //inisiasi view
        View l_bottomsheet_view_edit = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_edit_template,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit)
        );

        //inisiasi edittext
        g_et_edit_templated_label_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_label_edit);
        g_et_edit_templated_deskripsi_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_deskripsi_edit);

        //inisiasi button
        g_btn_templated_simpan_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_simpan_edit);
        g_btn_templated_hapus_edit = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_hapus_edit);

        //config edittext
        g_et_edit_templated_label_edit.setText(p_template.getTemplate_code());
        g_et_edit_templated_deskripsi_edit.setText(p_template.getText());

        //config button
        g_btn_templated_simpan_edit.setOnClickListener(this);
        g_btn_templated_hapus_edit.setOnClickListener(this);

        //show bottomsheet
        g_bottomsheet_dialog_edit.setContentView(l_bottomsheet_view_edit);
        g_bottomsheet_dialog_edit.show();
    }

    private void showBottomSheetAddTemplatedText(){
        //inisiasi view
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_add_template,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_add)
        );

        //inisiasi edittext
        g_et_edit_templated_label_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_label_add);
        g_et_edit_templated_deskripsi_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_deskripsi_add);

        //inisiasi button
        g_btn_templated_simpan_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_simpan_add);
        g_btn_templated_batal_add = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_batal_add);

        //config button
        g_btn_templated_simpan_add.setOnClickListener(this);
        g_btn_templated_batal_add.setOnClickListener(this);

        //show bottomsheet
        g_bottomsheet_dialog_add.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog_add.show();
    }
    //endregion

    public void showSortPopupMenu(View p_view) {
        g_popup = new PopupMenu(g_context, p_view);
        MenuInflater inflater = g_popup.getMenuInflater();
        inflater.inflate(R.menu.templatetext_fragment_bar, g_popup.getMenu());
        g_popup.setOnMenuItemClickListener(this);
        g_popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.apps_templatetext_item_sort_asc:
                VolleyClass.getTemplatedTextByName(g_context, 3, Method.ASC, g_templateadapter);
                break;
            case R.id.apps_templatetext_item_sort_desc:
                VolleyClass.getTemplatedTextByName(g_context, 3, Method.DESC, g_templateadapter);
                break;
            case R.id.apps_templatetext_item_sort_date_asc:
                VolleyClass.getTemplatedTextByDate(g_context, 3, Method.ASC, g_templateadapter);
                break;
            case R.id.apps_templatetext_item_sort_date_desc:
                VolleyClass.getTemplatedTextByDate(g_context, 3, Method.DESC, g_templateadapter);
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