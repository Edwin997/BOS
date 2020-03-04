package com.example.bca_bos.ui.beranda;

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

public class BerandaFragment extends Fragment {

    private BerandaViewModel berandaViewModel;
    private Context g_context;
    private View g_view;

    private RecyclerView g_beranda_produk_fragment_recyclerview, g_beranda_pembeli_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager_produk, g_linearlayoutmanager_pembeli;
    private BerandaProdukAdapter g_beranda_produk_adapter;
    private BerandaPembeliAdapter g_beranda_pembeli_adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_beranda, container, false);

        //Set Product RecyclerView
        g_beranda_produk_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_produk_fragment_recyclerview);
        g_linearlayoutmanager_produk = new LinearLayoutManager(g_context, RecyclerView.HORIZONTAL, true);
        g_beranda_produk_adapter = new BerandaProdukAdapter();

        g_beranda_produk_fragment_recyclerview.setAdapter(g_beranda_produk_adapter);
        g_beranda_produk_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_produk);

        //Set Buyer RecyclerView
        g_beranda_pembeli_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_pembeli_fragment_recyclerview);
        g_linearlayoutmanager_pembeli = new LinearLayoutManager(g_context);
        g_beranda_pembeli_adapter = new BerandaPembeliAdapter();

        g_beranda_pembeli_fragment_recyclerview.setAdapter(g_beranda_pembeli_adapter);
        g_beranda_pembeli_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_pembeli);

        return g_view;
    }
}