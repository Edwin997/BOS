package com.example.bca_bos.ui.produk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;

public class ProdukFragment extends Fragment implements OnCallBackListener, View.OnClickListener {

    public final static String KEY_PRODUK = "produk";

    private Context g_context;

    private LinearLayoutManager g_linearlayoutmanager;
    private ProdukAdapter g_produkadapter;
    private RecyclerView g_produkfragment_recyclerview;
    private ImageButton g_produk_fragment_btn_search, g_produk_fragment_btn_add, g_produk_fragment_btn_filter;
    private EditText g_produk_fragment_et_search;
    private TextView g_produk_fragment_tv_title;

    private boolean IS_NOT_LINEAR = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        View l_view = inflater.inflate(R.layout.fragment_produk, container, false);

        g_produk_fragment_btn_add = l_view.findViewById(R.id.apps_produk_fragment_add_btn);
        g_produk_fragment_btn_filter = l_view.findViewById(R.id.apps_produk_fragment_filter_btn);
        g_produk_fragment_btn_search = l_view.findViewById(R.id.apps_produk_fragment_search_btn);
        g_produk_fragment_et_search = l_view.findViewById(R.id.apps_produk_fragment_search_et);
        g_produk_fragment_tv_title = l_view.findViewById(R.id.apps_produk_fragment_judul);
        g_produkfragment_recyclerview = l_view.findViewById(R.id.apps_produk_fragment_recyclerview);

        g_produk_fragment_btn_add.setOnClickListener(this);
        g_produk_fragment_btn_filter.setOnClickListener(this);
        g_produk_fragment_btn_search.setOnClickListener(this);

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_produkadapter = new ProdukAdapter();
        g_produkadapter.setParentOnCallBack(this);

        g_produk_fragment_tv_title.setText("Semua Produk (" + g_produkadapter.getItemCount() + ")");
        g_produkfragment_recyclerview.setAdapter(g_produkadapter);
        g_produkfragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        return l_view;
    }

    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Produk) {
            Intent intent = new Intent(getContext(), EditProdukActivity.class);
            Produk tmpProduk = (Produk) p_obj;
            intent.putExtra(KEY_PRODUK, tmpProduk);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apps_produk_fragment_add_btn:
                Intent intent = new Intent(getContext(), AddProdukActivity.class);
                startActivity(intent);
                break;
            case R.id.apps_produk_fragment_filter_btn:
                break;
            case R.id.apps_produk_fragment_search_btn:
                break;
            default:
                Toast.makeText(g_context, "OBJECT NOT FOUND", Toast.LENGTH_SHORT).show();
        }
    }
}