package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;

public class TransaksiFilterDetailFragment extends Fragment {

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private TransaksiAdapter g_transaksiadapter;

    private RecyclerView g_transaksi_fiturdetail_recyclerview;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();

        View l_view = inflater.inflate(R.layout.fragment_transaksi_filter_detail, container, false);

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapter = new TransaksiAdapter();

        g_transaksi_fiturdetail_recyclerview = l_view.findViewById(R.id.apps_transaksi_filterdetail_recyclerview);
        g_transaksi_fiturdetail_recyclerview.setLayoutManager(g_linearlayoutmanager);
        g_transaksi_fiturdetail_recyclerview.setAdapter(g_transaksiadapter);

//        g_transaksi_fragment_piechart = l_view.findViewById(R.id.apps_transaksi_fragment_piechart);
//        g_transaksi_fragment_piechart.setUsePercentValues(true);

//        List<PieEntry> tmpListDataEntry = new ArrayList<>();
//        tmpListDataEntry.add(new PieEntry(g_transaksiadapter.getPersentaseTransaksiSudahSelesai(), "Transaksi Selesai"));
//        tmpListDataEntry.add(new PieEntry(g_transaksiadapter.getPersentaseTransaksiSudahDikirim(), "Sudah Dikirim"));
//        tmpListDataEntry.add(new PieEntry(g_transaksiadapter.getPersentaseTransaksiSudahDiBayar(), "Sudah Dibayar"));
//        tmpListDataEntry.add(new PieEntry(g_transaksiadapter.getPersentaseTransaksiBaruMasuk(), "Baru masuk"));

//        PieDataSet l_transaksi_piechart_dataset = new PieDataSet(tmpListDataEntry,"");
//        PieData l_transaksi_piechart_data = new PieData(l_transaksi_piechart_dataset);
//        l_transaksi_piechart_data.setValueTextSize(12f);
//        l_transaksi_piechart_data.setValueFormatter(new PercentFormatter());

//        l_transaksi_piechart_dataset.setColors(ColorTemplate.createColors(getResources(),
//                new int[]{R.color.green, R.color.turqoise, R.color.yellow, R.color.pink}));
//
//        Description l_description = new Description();
//        l_description.setText("");
//
//        g_transaksi_fragment_piechart.setData(l_transaksi_piechart_data);
//        g_transaksi_fragment_piechart.setDescription(l_description);
//        g_transaksi_fragment_piechart.setHoleRadius(0f);
//        g_transaksi_fragment_piechart.setTransparentCircleRadius(0f);
//        g_transaksi_fragment_piechart.animateXY(700,700);

        return l_view;
    }



}
