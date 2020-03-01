package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListTransaksiDetailDummy;
import com.example.bca_bos.dummy.ListTransaksiDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Transaksi;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFragment extends Fragment implements View.OnClickListener, OnCallBackListener {

    private Context g_context;
    private View g_view;

    private Button g_btn_tab_semua, g_btn_tab_pesanan_baru, g_btn_tab_pesanan_dibayar,
            g_btn_tab_pesanan_dikirim, g_btn_tab_selesai;

    private RecyclerView g_transaksi_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager;
    private TransaksiAdapter g_transaksiadapter;

    private PieChart g_transaksi_fragment_piechart;
    private float g_percentage;

    private BottomSheetDialog g_bottomsheet_dialog;

    private List<Transaksi> g_list_transaksi;

    private int FLAG_FRAGMENT_TYPE;
    public final int KEY_STATUS_BARUMASUK = 0;
    public final int KEY_STATUS_SUDAHDIBAYAR = 1;
    public final int KEY_STATUS_SUDAHDIKIRIM = 2;
    public final int KEY_STATUS_SELESAI = 3;
    public final int KEY_STATUS_SEMUA = 4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();
        g_list_transaksi = ListTransaksiDummy.transaksiList;
        g_view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapter = new TransaksiAdapter(this);
        g_transaksiadapter.setParentOnCallBack(this);
        g_transaksiadapter.setListTransaksi(g_list_transaksi);
        FLAG_FRAGMENT_TYPE = KEY_STATUS_SEMUA;

        g_transaksi_fragment_recyclerview = g_view.findViewById(R.id.apps_transaksi_fragment_recyclerview);
        g_transaksi_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager);
        g_transaksi_fragment_recyclerview.setAdapter(g_transaksiadapter);

        g_transaksi_fragment_piechart = g_view.findViewById(R.id.apps_transaksi_filterdetail_piechart);
        g_percentage = g_list_transaksi.size();

        g_btn_tab_pesanan_baru = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_baru);
        g_btn_tab_pesanan_dibayar = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_dibayar);
        g_btn_tab_pesanan_dikirim = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_dikirim);
        g_btn_tab_selesai = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_transaksi_selesai);
        g_btn_tab_semua = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_semua_transaksi);

        g_btn_tab_pesanan_baru.setOnClickListener(this);
        g_btn_tab_pesanan_dikirim.setOnClickListener(this);
        g_btn_tab_pesanan_dibayar.setOnClickListener(this);
        g_btn_tab_selesai.setOnClickListener(this);
        g_btn_tab_semua.setOnClickListener(this);

        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        drawPieChart(new int[]{R.color.black, R.color.black}, g_transaksiadapter.getItemCount() + "\nTransaksi");
        setTabBar();

        return g_view;
    }

    @Override
    public void onClick(View v) {
        TransaksiAdapter tmpAdapter = g_transaksiadapter;
        switch (v.getId()){
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_baru:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_BARUMASUK;
                g_percentage = getPersentaseTransaksiBaruMasuk();
                drawPieChart(new int[]{R.color.red, R.color.black},
                        countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
                tmpAdapter.setListTransaksi(getListTransaksiByType(FLAG_FRAGMENT_TYPE));
                g_transaksi_fragment_recyclerview.setAdapter(tmpAdapter);
                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_dibayar:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SUDAHDIBAYAR;
                g_percentage = getPersentaseTransaksiSudahDiBayar();
                drawPieChart(new int[]{R.color.yellow, R.color.black},
                        countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
                tmpAdapter.setListTransaksi(getListTransaksiByType(FLAG_FRAGMENT_TYPE));
                g_transaksi_fragment_recyclerview.setAdapter(tmpAdapter);
                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_dikirim:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SUDAHDIKIRIM;
                g_percentage = getPersentaseTransaksiSudahDikirim();
                drawPieChart(new int[]{R.color.blue, R.color.black},
                        countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
                tmpAdapter.setListTransaksi(getListTransaksiByType(FLAG_FRAGMENT_TYPE));
                g_transaksi_fragment_recyclerview.setAdapter(tmpAdapter);
                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_transaksi_selesai:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SELESAI;
                g_percentage = getPersentaseTransaksiSudahSelesai();
                drawPieChart(new int[]{R.color.green, R.color.black},
                        countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
                tmpAdapter.setListTransaksi(getListTransaksiByType(FLAG_FRAGMENT_TYPE));
                g_transaksi_fragment_recyclerview.setAdapter(tmpAdapter);
                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_semua_transaksi:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SEMUA;
                g_percentage = 100;
                drawPieChart(new int[]{R.color.black, R.color.black},
                        g_list_transaksi.size() + "\nTransaksi");
                g_transaksi_fragment_recyclerview.setAdapter(tmpAdapter);
                tmpAdapter.setListTransaksi(getListTransaksiByType(FLAG_FRAGMENT_TYPE));
                setTabBar();
                break;
        }
    }

    private void setTabBar(){
        refreshButton();
        if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BARUMASUK){
            g_btn_tab_pesanan_baru.setTextColor(g_context.getResources().getColor(R.color.white));
            g_btn_tab_pesanan_baru.setBackground(g_context.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_red));
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIBAYAR){
            g_btn_tab_pesanan_dibayar.setTextColor(g_context.getResources().getColor(R.color.white));
            g_btn_tab_pesanan_dibayar.setBackground(g_context.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_yellow));
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIKIRIM){
            g_btn_tab_pesanan_dikirim.setTextColor(g_context.getResources().getColor(R.color.white));
            g_btn_tab_pesanan_dikirim.setBackground(g_context.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_blue));
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SELESAI){
            g_btn_tab_selesai.setTextColor(g_context.getResources().getColor(R.color.white));
            g_btn_tab_selesai.setBackground(g_context.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_green));
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SEMUA){
            g_btn_tab_semua.setTextColor(g_context.getResources().getColor(R.color.white));
            g_btn_tab_semua.setBackground(g_context.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_black));
        }
    }

    private void refreshButton(){
        g_btn_tab_pesanan_baru.setTextColor(g_context.getResources().getColor(R.color.red));
        g_btn_tab_pesanan_baru.setBackgroundColor(g_context.getResources().getColor(R.color.white));
        g_btn_tab_pesanan_dibayar.setTextColor(g_context.getResources().getColor(R.color.yellow));
        g_btn_tab_pesanan_dibayar.setBackgroundColor(g_context.getResources().getColor(R.color.white));
        g_btn_tab_pesanan_dikirim.setTextColor(g_context.getResources().getColor(R.color.blue));
        g_btn_tab_pesanan_dikirim.setBackgroundColor(g_context.getResources().getColor(R.color.white));
        g_btn_tab_selesai.setTextColor(g_context.getResources().getColor(R.color.green));
        g_btn_tab_selesai.setBackgroundColor(g_context.getResources().getColor(R.color.white));
        g_btn_tab_semua.setTextColor(g_context.getResources().getColor(R.color.black));
        g_btn_tab_semua.setBackgroundColor(g_context.getResources().getColor(R.color.white));
    }

    public void drawPieChart(int[] p_colortemplate, String p_centertext){
        List<PieEntry> tmpListDataEntry = new ArrayList<>();
        tmpListDataEntry.add(new PieEntry(g_percentage, ""));
        tmpListDataEntry.add(new PieEntry((100f-g_percentage), ""));

        PieDataSet l_transaksi_piechart_dataset = new PieDataSet(tmpListDataEntry,"");
        PieData l_transaksi_piechart_data = new PieData(l_transaksi_piechart_dataset);
        l_transaksi_piechart_data.setDrawValues(false);

        Description l_description = new Description();
        l_description.setText("");

        g_transaksi_fragment_piechart.setDrawCenterText(true);

        String[] tmpStringSplit = p_centertext.split("\n");
        int tmpLength = tmpStringSplit[0].length();

        SpannableString tmpCustomSizeText=  new SpannableString(p_centertext);
        tmpCustomSizeText.setSpan(new RelativeSizeSpan(2f), 0, tmpLength, 0);
        tmpCustomSizeText.setSpan(new ForegroundColorSpan(getResources().getColor(p_colortemplate[0])), 0, tmpLength, 0);
        tmpCustomSizeText.setSpan(new StyleSpan(Typeface.BOLD), 0, tmpLength, 0);

        l_transaksi_piechart_dataset.setColors(ColorTemplate.createColors(getResources(), p_colortemplate));
        g_transaksi_fragment_piechart.setCenterText(tmpCustomSizeText);
        g_transaksi_fragment_piechart.setCenterTextSize(18);
        g_transaksi_fragment_piechart.setCenterTextColor(getResources().getColor(R.color.black));

        g_transaksi_fragment_piechart.setData(l_transaksi_piechart_data);
        g_transaksi_fragment_piechart.setDrawEntryLabels(false);
        g_transaksi_fragment_piechart.setDrawHoleEnabled(true);
        g_transaksi_fragment_piechart.setHoleColor(Color.TRANSPARENT);

        g_transaksi_fragment_piechart.setDrawMarkers(false);
        g_transaksi_fragment_piechart.setDescription(l_description);
        g_transaksi_fragment_piechart.setHoleRadius(80f);
        g_transaksi_fragment_piechart.setTransparentCircleRadius(0f);
        g_transaksi_fragment_piechart.animateXY(1000,1000);
    }

    public List<Transaksi> getListTransaksiByType(int p_type){
        List<Transaksi> tmpListTransaksi = new ArrayList<>();

        if(p_type == KEY_STATUS_SEMUA){
            tmpListTransaksi = ListTransaksiDummy.transaksiList;
        }
        else{
            for(int i = 0; i < g_list_transaksi.size(); i++){
                if(g_list_transaksi.get(i).getStatus() == p_type)
                    tmpListTransaksi.add(g_list_transaksi.get(i));
            }
        }

        return tmpListTransaksi;
    }

    public float getPersentaseTransaksiSudahSelesai(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(KEY_STATUS_SELESAI)) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiSudahDikirim(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(KEY_STATUS_SUDAHDIKIRIM)) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiSudahDiBayar(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(KEY_STATUS_SUDAHDIBAYAR)) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiBaruMasuk(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(KEY_STATUS_BARUMASUK)) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public int countTransaksibyStatus(int p_type){
        int count = 0;

        for(int i = 0; i < g_list_transaksi.size(); i++){
            if(g_list_transaksi.get(i).getStatus() == p_type)
                count++;
        }

        return count;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Transaksi){
            Transaksi tmpTransaksi = (Transaksi) p_obj;

            if(tmpTransaksi.getStatus() == KEY_STATUS_BARUMASUK){
                showBottomSheetPesananBaru(tmpTransaksi);
            }
            else if(tmpTransaksi.getStatus() == KEY_STATUS_SUDAHDIBAYAR){
                showBottomSheetPesananDibayar(tmpTransaksi);
            }
            else if(tmpTransaksi.getStatus() == KEY_STATUS_SUDAHDIKIRIM){
                showBottomSheetPesananDikirim(tmpTransaksi);
            }
            else if(tmpTransaksi.getStatus() == KEY_STATUS_SELESAI){
                showBottomSheetPesananSelesai(tmpTransaksi);
            }
        }
    }

    private void showBottomSheetPesananBaru(Transaksi p_transaksi){
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananbaru,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananbaru)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesanbaru = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananbaru);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_nama);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_total);
//        TextView l_tv_bottom_sheet_transaksi_pesanbaru_totals = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_totals);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        l_tdAdapter.setListTransaksiDetail(ListTransaksiDetailDummy.getTransaksiDetailList(p_transaksi.getId()));
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesanbaru.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesanbaru.setLayoutManager(l_layoutmanager);

        l_tv_bottom_sheet_transaksi_pesanbaru_nama.setText(p_transaksi.getBuyer().getNama());
        l_tv_bottom_sheet_transaksi_pesanbaru_tanggal.setText(p_transaksi.getOrder_date());
        l_tv_bottom_sheet_transaksi_pesanbaru_total.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));
//        l_tv_bottom_sheet_transaksi_pesanbaru_totals.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    private void showBottomSheetPesananDikirim(Transaksi p_transaksi){
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesanandikirim,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesanandikirim)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesandikirim = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesanandikirim);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_nama);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_total);
//        TextView l_tv_bottom_sheet_transaksi_pesandikirim_totals = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_totals);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        l_tdAdapter.setListTransaksiDetail(ListTransaksiDetailDummy.getTransaksiDetailList(p_transaksi.getId()));
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesandikirim.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesandikirim.setLayoutManager(l_layoutmanager);

        l_tv_bottom_sheet_transaksi_pesandikirim_nama.setText(p_transaksi.getBuyer().getNama());
        l_tv_bottom_sheet_transaksi_pesandikirim_tanggal.setText(p_transaksi.getOrder_date());
        l_tv_bottom_sheet_transaksi_pesandikirim_total.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));
//        l_tv_bottom_sheet_transaksi_pesandikirim_totals.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    private void showBottomSheetPesananSelesai(Transaksi p_transaksi){
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananselesai,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananselesai)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesanselesai = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananselesai);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_nama);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_total);
//        TextView l_tv_bottom_sheet_transaksi_pesanselesai_totals = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_totals);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        l_tdAdapter.setListTransaksiDetail(ListTransaksiDetailDummy.getTransaksiDetailList(p_transaksi.getId()));
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesanselesai.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesanselesai.setLayoutManager(l_layoutmanager);

        l_tv_bottom_sheet_transaksi_pesanselesai_nama.setText(p_transaksi.getBuyer().getNama());
        l_tv_bottom_sheet_transaksi_pesanselesai_tanggal.setText(p_transaksi.getOrder_date());
        l_tv_bottom_sheet_transaksi_pesanselesai_total.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));
//        l_tv_bottom_sheet_transaksi_pesanselesai_totals.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    private void showBottomSheetPesananDibayar(Transaksi p_transaksi){
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesanandibayar,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesanandibayar)
        );

        TextView l_tv_bottom_sheet_transaksi_dibayar_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_nama);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_total);
        final EditText  l_et_bottom_sheet_transaksi_pesandibayar_resi = l_bottomsheet_view_add.findViewById(R.id.et_apps_bottom_sheet_transaksi_pesanandibayar_resi);
        Button l_btn_bottom_sheet_transaksi_pesandibayar_kirimresi = l_bottomsheet_view_add.findViewById(R.id.btn_apps_bottom_sheet_transaksi_pesanandibayar_sendresi);

        l_tv_bottom_sheet_transaksi_dibayar_nama.setText(p_transaksi.getBuyer().getNama());
        l_tv_bottom_sheet_transaksi_pesandibayar_tanggal.setText(p_transaksi.getOrder_date());
        l_tv_bottom_sheet_transaksi_pesandibayar_total.setText(Method.getIndoCurrency(p_transaksi.getTotal_payment()));

        l_btn_bottom_sheet_transaksi_pesandibayar_kirimresi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!l_et_bottom_sheet_transaksi_pesandibayar_resi.getText().equals("")){
                    Toast.makeText(g_context, l_et_bottom_sheet_transaksi_pesandibayar_resi.getText(), Toast.LENGTH_SHORT).show();
                    g_bottomsheet_dialog.dismiss();
                }
            }
        });

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }
}