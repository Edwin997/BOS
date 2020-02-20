package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;

public class TransaksiFragment extends Fragment implements View.OnClickListener {

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private TransaksiAdapter g_transaksiadapter;

    private RecyclerView g_transaksi_fragment_recyclerview;

    private Button g_transaksi_fragment_btn_barumasuk, g_transaksi_fragment_btn_sudahdibayar,
            g_transaksi_fragment_btn_sudahdikirim, g_transaksi_fragment_btn_selesai;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();

        View l_view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapter = new TransaksiAdapter();

        g_transaksi_fragment_recyclerview = l_view.findViewById(R.id.apps_transaksi_fragment_recyclerview);
        g_transaksi_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager);
        g_transaksi_fragment_recyclerview.setAdapter(g_transaksiadapter);


        g_transaksi_fragment_btn_barumasuk = l_view.findViewById(R.id.apps_transaksi_fragment_baru_masuk_btn);
        g_transaksi_fragment_btn_sudahdibayar = l_view.findViewById(R.id.apps_transaksi_fragment_sudah_bayar_btn);
        g_transaksi_fragment_btn_sudahdikirim = l_view.findViewById(R.id.apps_transaksi_fragment_sudah_kirim_btn);
        g_transaksi_fragment_btn_selesai = l_view.findViewById(R.id.apps_transaksi_fragment_sudah_selesai_btn);

        g_transaksi_fragment_btn_barumasuk.setOnClickListener(this);
        g_transaksi_fragment_btn_sudahdibayar.setOnClickListener(this);
        g_transaksi_fragment_btn_sudahdikirim.setOnClickListener(this);
        g_transaksi_fragment_btn_selesai.setOnClickListener(this);

        return l_view;
    }

    @Override
    public void onClick(View v) {
        Intent tmpIntent = new Intent(g_context, TransaksiFilterDetail.class);
        switch(v.getId()){
            case R.id.apps_transaksi_fragment_baru_masuk_btn:
                tmpIntent.putExtra("TRANSAKSI_PAGE", 0);
                break;
            case R.id.apps_transaksi_fragment_sudah_bayar_btn:
                tmpIntent.putExtra("TRANSAKSI_PAGE", 1);
                break;
            case R.id.apps_transaksi_fragment_sudah_kirim_btn:
                tmpIntent.putExtra("TRANSAKSI_PAGE", 2);
                break;
            case R.id.apps_transaksi_fragment_sudah_selesai_btn:
                tmpIntent.putExtra("TRANSAKSI_PAGE", 3);
                break;
        }
        startActivity(tmpIntent);
    }
}