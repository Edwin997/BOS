package com.example.bca_bos.ui.beranda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.dummy.ListPembeliDummy;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BerandaTawarkanPembeliAdapter extends RecyclerView.Adapter<BerandaTawarkanPembeliAdapter.BerandaTawarkanPembeliViewHolder> implements OnCallBackListener {

    private List<Buyer> g_list_pembeli;
    private OnCallBackListener g_parent_oncallbacklistener;

    public BerandaTawarkanPembeliAdapter(){
        g_list_pembeli = new ArrayList<>();
    }

    public BerandaTawarkanPembeliAdapter(List<Buyer> p_list){
        g_list_pembeli = p_list;
    }

    @NonNull
    @Override
    public BerandaTawarkanPembeliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_beranda_tawarkan_pembeli, parent, false);

        BerandaTawarkanPembeliAdapter.BerandaTawarkanPembeliViewHolder tmpHolder = new BerandaTawarkanPembeliAdapter.BerandaTawarkanPembeliViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaTawarkanPembeliAdapter.BerandaTawarkanPembeliViewHolder holder, int position) {
        holder.setData(g_list_pembeli.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_pembeli.size();
    }

    public void setDatasetPembeli(List<Buyer> p_list){
        g_list_pembeli = p_list;
        notifyDataSetChanged();
    }

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    public List<Buyer> getListPembeliSetia(){
        return g_list_pembeli;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack(p_obj);
        }
    }

    public class BerandaTawarkanPembeliViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout l_ll_container_beranda_tawarkan_pembeli;

        private TextView l_tv_beranda_tawarkan_pembeli_nama, l_tv_beranda_petawarkan_mbeli_no_hp;
        private Buyer l_pembeli;

        private OnCallBackListener l_parent_oncallbacklistener;

        public BerandaTawarkanPembeliViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ll_container_beranda_tawarkan_pembeli = itemView.findViewById(R.id.ll_apps_beranda_tawarkan_pembeli_item);
            l_tv_beranda_tawarkan_pembeli_nama = itemView.findViewById(R.id.tv_apps_beranda_tawarkan_pembeli_nama_item);
            l_tv_beranda_petawarkan_mbeli_no_hp = itemView.findViewById(R.id.tv_apps_beranda_tawarkan_pembeli_no_hp_item);
            l_ll_container_beranda_tawarkan_pembeli.setOnClickListener(this);

        }

        public void setData(Buyer pembeli){
            l_pembeli = pembeli;

            l_tv_beranda_tawarkan_pembeli_nama.setText(pembeli.getBuyer_name());
            l_tv_beranda_petawarkan_mbeli_no_hp.setText(pembeli.getPhone());
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(view == l_ll_container_beranda_tawarkan_pembeli){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_pembeli);
                }
            }
        }
    }
}
