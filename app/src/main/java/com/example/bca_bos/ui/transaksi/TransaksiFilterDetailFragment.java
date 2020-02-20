package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Transaksi;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFilterDetailFragment extends Fragment implements View.OnClickListener {

    private Context g_context;

    private TextView g_tv_transaksi_filterdetail;
    private ImageButton g_btn_transaksi_filterdetail_back, g_btn_transaksi_filterdetail_next;

    private RecyclerView g_transaksi_fiturdetail_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager;
    private TransaksiAdapter g_transaksiadapter;

    private PieChart g_transaksi_fragment_piechart;
    private float g_percentage;

    private List<Transaksi> g_list_transaksi;

    private OnCallBackListener g_transaksi_filterdetail_oncallback;

    private int TRANSAKSI_FRAGMENT_TYPE;

    public TransaksiFilterDetailFragment(List<Transaksi> p_list, int p_type, float p_persen, OnCallBackListener p_parent){
        g_list_transaksi = p_list;
        g_percentage = p_persen;
        TRANSAKSI_FRAGMENT_TYPE = p_type;
        g_transaksi_filterdetail_oncallback = p_parent;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();

        View l_view = inflater.inflate(R.layout.fragment_transaksi_filter_detail, container, false);

        g_tv_transaksi_filterdetail = l_view.findViewById(R.id.apps_transaksi_filterdetail_judul_tv);
        g_btn_transaksi_filterdetail_back = l_view.findViewById(R.id.apps_transaksi_filterdetail_back_btn);
        g_btn_transaksi_filterdetail_next = l_view.findViewById(R.id.apps_transaksi_filterdetail_next_btn);

        g_transaksi_fragment_piechart = l_view.findViewById(R.id.apps_transaksi_filterdetail_piechart);

        g_transaksi_fiturdetail_recyclerview = l_view.findViewById(R.id.apps_transaksi_filterdetail_recyclerview);
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapter = new TransaksiAdapter();

        g_transaksi_fiturdetail_recyclerview.setLayoutManager(g_linearlayoutmanager);
        g_transaksi_fiturdetail_recyclerview.setAdapter(g_transaksiadapter);

        g_transaksiadapter.setListTransaksi(g_list_transaksi);

        g_btn_transaksi_filterdetail_next.setOnClickListener(this);
        g_btn_transaksi_filterdetail_back.setOnClickListener(this);

        refreshLayout();

        return l_view;
    }

    public void refreshLayout(){
        switch (TRANSAKSI_FRAGMENT_TYPE){
            case TransaksiFilterDetail.KEY_STATUS_BARUMASUK:
                g_tv_transaksi_filterdetail.setText("BARU MASUK");
                drawPieChart(
                        new int[]{R.color.pink, R.color.dark_grey},
                        g_list_transaksi.size() + "\nBARU MASUK");
                break;
            case TransaksiFilterDetail.KEY_STATUS_SUDAHDIBAYAR:
                g_tv_transaksi_filterdetail.setText("SUDAH DIBAYAR");
                drawPieChart(
                        new int[]{R.color.yellow, R.color.dark_grey},
                        g_list_transaksi.size() + "\nSUDAH DIBAYAR");
                break;
            case TransaksiFilterDetail.KEY_STATUS_SUDAHDIKIRIM:
                g_tv_transaksi_filterdetail.setText("PROSES PENGIRIMAN");
                drawPieChart(
                        new int[]{R.color.turqoise, R.color.dark_grey},
                        g_list_transaksi.size() + "\nPROSES PENGIRIMAN");

                break;
            case TransaksiFilterDetail.KEY_STATUS_SELESAI:
                g_tv_transaksi_filterdetail.setText("TRANSAKSI SELESAI");
                drawPieChart(
                        new int[]{R.color.green, R.color.dark_grey},
                        g_list_transaksi.size() + "\nTRANSAKSI SELESAI");
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

        l_transaksi_piechart_dataset.setColors(ColorTemplate.createColors(getResources(), p_colortemplate));
        g_transaksi_fragment_piechart.setCenterText(p_centertext);
        g_transaksi_fragment_piechart.setCenterTextSize(25);

        g_transaksi_fragment_piechart.setData(l_transaksi_piechart_data);
        g_transaksi_fragment_piechart.setDrawEntryLabels(false);
        g_transaksi_fragment_piechart.setDrawHoleEnabled(true);
        g_transaksi_fragment_piechart.setDrawMarkers(false);
        g_transaksi_fragment_piechart.setDescription(l_description);
        g_transaksi_fragment_piechart.setHoleRadius(80f);
        g_transaksi_fragment_piechart.setTransparentCircleRadius(100f);
        g_transaksi_fragment_piechart.animateXY(700,700);
    }


    @Override
    public void onClick(View v) {
        if(v == g_btn_transaksi_filterdetail_next){
            g_transaksi_filterdetail_oncallback.OnCallBack("NEXT");
        }
        else if(v == g_btn_transaksi_filterdetail_back){
            g_transaksi_filterdetail_oncallback.OnCallBack("BACK");
        }
    }
}
