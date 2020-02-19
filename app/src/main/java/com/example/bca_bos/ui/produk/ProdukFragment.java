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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;

public class ProdukFragment extends Fragment implements OnCallBackListener {

    public final static String KEY_PRODUK = "produk";

    private Context g_context;

    private GridLayoutManager g_gridlayoutmanager;
    private LinearLayoutManager g_linearlayoutmanager;
    private ProdukAdapter g_produkadapter;

    private RecyclerView g_produkfragment_recyclerview;

    private OnCallBackListener g_parent_oncallbacklistener;

    private boolean IS_NOT_LINEAR = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();

        View l_view = inflater.inflate(R.layout.fragment_produk, container, false);
        setHasOptionsMenu(true);
        g_gridlayoutmanager = new GridLayoutManager(g_context, 2);
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_produkadapter = new ProdukAdapter();
        g_produkadapter.setParentOnCallBack(this);

        g_produkfragment_recyclerview = l_view.findViewById(R.id.apps_produk_fragment_recyclerview);
        g_produkfragment_recyclerview.setLayoutManager(g_gridlayoutmanager);
        g_produkfragment_recyclerview.setAdapter(g_produkadapter);

        return l_view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.produk_fragment_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.produk_fragment_menu_layout:
                if(IS_NOT_LINEAR){
                    g_produkfragment_recyclerview.setLayoutManager(g_gridlayoutmanager);
                    item.setIcon(getResources().getDrawable(R.drawable.ic_apps_view_grid_white));
                }else{
                    g_produkfragment_recyclerview.setLayoutManager(g_linearlayoutmanager);
                    item.setIcon(getResources().getDrawable(R.drawable.ic_apps_view_list_white));
                }
                IS_NOT_LINEAR = !IS_NOT_LINEAR;
                return true;
            case R.id.produk_fragment_menu_add:
                Intent intent = new Intent(g_context, AddProdukActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Produk){
            Intent intent = new Intent(getContext(), EditProdukActivity.class);
            Produk tmpProduk = (Produk) p_obj;
            intent.putExtra(KEY_PRODUK, tmpProduk);
            startActivity(intent);
        }
    }
}