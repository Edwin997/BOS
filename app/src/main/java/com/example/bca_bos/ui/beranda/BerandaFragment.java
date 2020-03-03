package com.example.bca_bos.ui.beranda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ApplicationContainer;
import com.example.bca_bos.R;

public class BerandaFragment extends Fragment implements View.OnClickListener {

    private Context g_context;
    private View g_view;

    private RecyclerView g_beranda_produk_fragment_recyclerview, g_beranda_pembeli_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager_produk, g_linearlayoutmanager_pembeli;
    private BerandaProdukAdapter g_beranda_produk_adapter;
    private BerandaPembeliAdapter g_beranda_pembeli_adapter;
    private Button g_beranda_pesanan_belum_dikirim_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_beranda, container, false);

        //Set Produk RecyclerView
        g_beranda_produk_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_produk_fragment_recyclerview);
        g_linearlayoutmanager_produk = new LinearLayoutManager(g_context, RecyclerView.HORIZONTAL, false);
        g_beranda_produk_adapter = new BerandaProdukAdapter();

        g_beranda_produk_fragment_recyclerview.setAdapter(g_beranda_produk_adapter);
        g_beranda_produk_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_produk);

        //Set Pembeli RecyclerView
        g_beranda_pembeli_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_pembeli_fragment_recyclerview);
        g_linearlayoutmanager_pembeli = new LinearLayoutManager(g_context);
        g_beranda_pembeli_adapter = new BerandaPembeliAdapter();

        g_beranda_pembeli_fragment_recyclerview.setAdapter(g_beranda_pembeli_adapter);
        g_beranda_pembeli_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_pembeli);

        g_beranda_pesanan_belum_dikirim_button = g_view.findViewById(R.id.beranda_btn_pesanan_belum_dikirim);
        g_beranda_pesanan_belum_dikirim_button.setOnClickListener(this);

        return g_view;
    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.beranda_btn_pesanan_belum_dikirim:
                Bundle tmp_bundle = new Bundle();
                tmp_bundle.putInt("flag",1);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_transaksi, tmp_bundle);
                break;
        }
    }
}