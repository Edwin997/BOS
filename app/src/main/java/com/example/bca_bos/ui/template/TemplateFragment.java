package com.example.bca_bos.ui.template;

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
import com.example.bca_bos.adapters.TemplatedTextAdapter;
import com.example.bca_bos.ui.produk.ProdukAdapter;

public class TemplateFragment extends Fragment {

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private TemplateAdapter g_templateadapter;
    private RecyclerView g_templatefragment_recyclerview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        View l_view = inflater.inflate(R.layout.fragment_template, container, false);

        g_templatefragment_recyclerview = l_view.findViewById(R.id.apps_template_fragment_recyclerview);
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_templateadapter = new TemplateAdapter();

        g_templatefragment_recyclerview.setAdapter(g_templateadapter);
        g_templatefragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        return l_view;
    }
}