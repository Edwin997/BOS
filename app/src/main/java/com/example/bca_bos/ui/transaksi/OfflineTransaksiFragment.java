package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.transactions.Transaction;
import com.example.bca_bos.networks.VolleyClass;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class OfflineTransaksiFragment extends Fragment implements View.OnClickListener, OnCallBackListener {
    private Context g_context;
    private View g_view;
    public static OfflineTransaksiFragment g_instance;

    //Tombol tab status transaksi
//    private Button g_btn_tab_semua, g_btn_tab_pesanan_baru, g_btn_tab_pesanan_dibayar,
//            g_btn_tab_pesanan_dikirim, g_btn_tab_selesai;
//    private View g_view_tab_semua, g_view_tab_pesanan_baru, g_view_tab_pesanan_dibayar,
//            g_view_tab_pesanan_dikirim, g_view_tab_selesai;

    private ImageView g_transaksi_iv;

    private RecyclerView g_transaksi_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager;
    private OfflineTransaksiAdapter g_transaksiadapteroffline;

    private PieChart g_transaksi_fragment_piechart;
    private float g_percentage;

    private BottomSheetDialog g_bottomsheet_dialog;
//
//    private List<Transaction> g_list_transaction;

    private int FLAG_FRAGMENT_TYPE;
    public final int KEY_STATUS_BARUMASUK = 0;
    public final int KEY_STATUS_SUDAHDIBAYAR = 1;
    public final int KEY_STATUS_SUDAHDIKIRIM = 2;
    public final int KEY_STATUS_SELESAI = 3;
    public final int KEY_STATUS_SEMUA = 4;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();

        g_view = inflater.inflate(R.layout.fragment_offline_transaction, container, false);

        g_instance = this;

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapteroffline = new OfflineTransaksiAdapter(this);
        g_transaksiadapteroffline.setParentOnCallBack(this);

        g_transaksi_fragment_recyclerview = g_view.findViewById(R.id.apps_transaksi_fragment_recyclerview_off);
        g_transaksi_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        g_transaksi_fragment_piechart = g_view.findViewById(R.id.apps_transaksi_filterdetail_piechart_off);
        g_percentage = g_transaksiadapteroffline.getItemCount();

        //Inisialisasi Tab
//        g_btn_tab_pesanan_baru = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_baru);
//        g_btn_tab_pesanan_dibayar = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_dibayar);
//        g_btn_tab_pesanan_dikirim = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_dikirim);
//        g_btn_tab_selesai = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_transaksi_selesai);
//        g_btn_tab_semua = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_semua_transaksi);
//        g_transaksi_iv = g_view.findViewById(R.id.apps_transaksi_image_view);
//        g_view_tab_pesanan_baru = g_view.findViewById(R.id.apps_transaksi_underline_pesanan_baru);
//        g_view_tab_pesanan_dibayar = g_view.findViewById(R.id.apps_transaksi_underline_pesanan_dibayar);
//        g_view_tab_pesanan_dikirim = g_view.findViewById(R.id.apps_transaksi_underline_pesanan_dikirim);
//        g_view_tab_selesai = g_view.findViewById(R.id.apps_transaksi_underline_transaksi_selesai);
//        g_view_tab_semua = g_view.findViewById(R.id.apps_transaksi_underline_semua_transaksi);
//
//
//        g_btn_tab_pesanan_baru.setOnClickListener(this);
//        g_btn_tab_pesanan_dikirim.setOnClickListener(this);
//        g_btn_tab_pesanan_dibayar.setOnClickListener(this);
//        g_btn_tab_selesai.setOnClickListener(this);
//        g_btn_tab_semua.setOnClickListener(this);

        firstLoad();

        VolleyClass.getTransaksiOffline(g_context, 3, g_transaksiadapteroffline);

        return g_view;
    }

    public void firstLoad(){
        g_percentage = 100;
        drawPieChart(new int[]{R.color.white, R.color.white},
                g_transaksiadapteroffline.getItemMasterCount() + "\nTransaksi");
        g_transaksi_fragment_recyclerview.setAdapter(g_transaksiadapteroffline);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_baru:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_BARUMASUK;
                g_percentage = g_transaksiadapteroffline.getPersentaseTransaksiBaruMasuk();
                drawPieChart(new int[]{R.color.red, R.color.white},
                        g_transaksiadapteroffline.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaction");
                g_transaksiadapteroffline.setListTransaksiFiltered(g_transaksiadapteroffline.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_dibayar:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SUDAHDIBAYAR;
                g_percentage = g_transaksiadapteroffline.getPersentaseTransaksiSudahDiBayar();
                drawPieChart(new int[]{R.color.yellow, R.color.white},
                        g_transaksiadapteroffline.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaction");
                g_transaksiadapteroffline.setListTransaksiFiltered(g_transaksiadapteroffline.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
//                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_dikirim:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SUDAHDIKIRIM;
                g_percentage = g_transaksiadapteroffline.getPersentaseTransaksiSudahDikirim();
                drawPieChart(new int[]{R.color.blue, R.color.white},
                        g_transaksiadapteroffline.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaction");
                g_transaksiadapteroffline.setListTransaksiFiltered(g_transaksiadapteroffline.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
//                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_transaksi_selesai:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SELESAI;
                g_percentage = g_transaksiadapteroffline.getPersentaseTransaksiSudahSelesai();
                drawPieChart(new int[]{R.color.green, R.color.white},
                        g_transaksiadapteroffline.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaction");
                g_transaksiadapteroffline.setListTransaksiFiltered(g_transaksiadapteroffline.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
//                setTabBar();
                break;
            case R.id.apps_transaksi_fragment_tab_btn_semua_transaksi:
                FLAG_FRAGMENT_TYPE = KEY_STATUS_SEMUA;
                g_percentage = 100;
                drawPieChart(new int[]{R.color.white, R.color.white},
                        g_transaksiadapteroffline.getItemMasterCount() + "\nTransaction");
                g_transaksiadapteroffline.setListTransaksiFiltered(g_transaksiadapteroffline.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
//                setTabBar();
                break;
        }
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
//        tmpCustomSizeText.setSpan(new ForegroundColorSpan(getResources().getColor(p_colortemplate[0])), 0, tmpLength, 0);
        tmpCustomSizeText.setSpan(new StyleSpan(Typeface.BOLD), 0, tmpLength, 0);

        l_transaksi_piechart_dataset.setColors(ColorTemplate.createColors(getResources(), p_colortemplate));
        g_transaksi_fragment_piechart.setCenterText(tmpCustomSizeText);
        g_transaksi_fragment_piechart.setCenterTextSize(18);
        g_transaksi_fragment_piechart.setCenterTextColor(getResources().getColor(R.color.white));



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

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Transaction){
            Transaction tmpTransaction = (Transaction) p_obj;
            VolleyClass.getTransaksiDetail(g_context, tmpTransaction.getId_transaction(), tmpTransaction.getStatus());
        }
    }

    public void showBottomSheetPesananBaru(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananbaru,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananbaru)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesanbaru = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananbaru);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_nama);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_total);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_phone);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_alamat);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesanbaru.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesanbaru.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesanbaru_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesanbaru_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesanbaru_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesanbaru_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesanbaru_alamat.setText(p_transaction.getAddress());

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananDikirim(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesanandikirim,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesanandikirim)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesandikirim = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesanandikirim);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_nama);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_total);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_phone);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_alamat);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesandikirim.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesandikirim.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesandikirim_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesandikirim_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesandikirim_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesandikirim_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesandikirim_alamat.setText(p_transaction.getAddress());

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananSelesai(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananselesai,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananselesai)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesanselesai = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananselesai);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_nama);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_total);
        TextView l_tv_bottom_sheet_transaksi_pesananselesai_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_phone);
        TextView l_tv_bottom_sheet_transaksi_pesananselesai_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_alamat);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesanselesai.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesanselesai.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesanselesai_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesanselesai_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesanselesai_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesananselesai_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesananselesai_alamat.setText(p_transaction.getAddress());

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananDibayar(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesanandibayar,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesanandibayar)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesandibayar = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesanandibayar);

        TextView l_tv_bottom_sheet_transaksi_dibayar_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_nama);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_total);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_phone);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_alamat);

        final EditText l_et_bottom_sheet_transaksi_pesandibayar_resi = l_bottomsheet_view_add.findViewById(R.id.et_apps_bottom_sheet_transaksi_pesanandibayar_resi);
        Button l_btn_bottom_sheet_transaksi_pesandibayar_kirimresi = l_bottomsheet_view_add.findViewById(R.id.btn_apps_bottom_sheet_transaksi_pesanandibayar_sendresi);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesandibayar.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesandibayar.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_dibayar_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesandibayar_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesandibayar_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesandibayar_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesandibayar_alamat.setText(p_transaction.getAddress());

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
