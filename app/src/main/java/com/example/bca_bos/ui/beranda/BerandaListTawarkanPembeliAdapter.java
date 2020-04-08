package com.example.bca_bos.ui.beranda;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.R;
import com.example.bca_bos.dummy.ListPembeliDummy;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Buyer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BerandaListTawarkanPembeliAdapter extends RecyclerView.Adapter<BerandaListTawarkanPembeliAdapter.BerandaListTawarkanPembeliViewHolder> {

    private List<Buyer> g_list_pembeli;
    private HashMap<Buyer, Boolean> g_map_pembeli;

    public BerandaListTawarkanPembeliAdapter(List<Buyer> p_list){
        g_list_pembeli = p_list;
        g_map_pembeli = new HashMap<>();

        for(int i = 0; i < g_list_pembeli.size(); i++){
            g_map_pembeli.put(g_list_pembeli.get(i), false);
        }
    }

    @NonNull
    @Override
    public BerandaListTawarkanPembeliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_dialog_listitem_beranda, parent, false);

        BerandaListTawarkanPembeliViewHolder tmpHolder = new BerandaListTawarkanPembeliViewHolder(l_view);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaListTawarkanPembeliViewHolder holder, int position) {
        holder.setData(g_list_pembeli.get(position), g_map_pembeli.get(g_list_pembeli.get(position)));
    }

    @Override
    public int getItemCount() {
        return g_list_pembeli.size();
    }

    public void setDatasetPembeli(List<Buyer> p_list){
        g_list_pembeli = p_list;

        g_map_pembeli.clear();
        for(int i = 0; i < g_list_pembeli.size(); i++){
            g_map_pembeli.put(g_list_pembeli.get(i), false);
        }

        notifyDataSetChanged();
    }

    public String getNomorUntukTawarkan(){
        String tmpNomor = "";

        for (Map.Entry<Buyer, Boolean> tmpPembeli: g_map_pembeli.entrySet()) {
            if(tmpPembeli.getValue()) {
                if (!tmpNomor.isEmpty()) {
                    tmpNomor += ", " + tmpPembeli.getKey().getPhone();
                }else{
                    tmpNomor += tmpPembeli.getKey().getPhone();
                }
            }
        }

        return tmpNomor;
    }

    public class BerandaListTawarkanPembeliViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout l_ll_container_beranda_list_tawarkan_pembeli;

        private TextView l_tv_beranda_tawarkan_pembeli_nama, l_tv_beranda_petawarkan_mbeli_no_hp;
        private ImageView l_iv_beranda_tawarkan_pembeli_check;

        private Buyer l_pembeli;
        private boolean l_flag;

        public BerandaListTawarkanPembeliViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ll_container_beranda_list_tawarkan_pembeli = itemView.findViewById(R.id.item_dialog_listitem_beranda_layout);
            l_tv_beranda_tawarkan_pembeli_nama = itemView.findViewById(R.id.item_dialog_listitem_beranda_nama);
            l_tv_beranda_petawarkan_mbeli_no_hp = itemView.findViewById(R.id.item_dialog_listitem_beranda_subketerangan);
            l_iv_beranda_tawarkan_pembeli_check = itemView.findViewById(R.id.item_dialog_listitem_beranda_check);

            l_ll_container_beranda_list_tawarkan_pembeli.setOnClickListener(this);
        }

        public void setData(Buyer pembeli, boolean flag){
            l_pembeli = pembeli;
            l_flag = flag;

            l_tv_beranda_tawarkan_pembeli_nama.setText(pembeli.getBuyer_name());
            l_tv_beranda_petawarkan_mbeli_no_hp.setText(pembeli.getPhone());

            if(l_flag)
                l_iv_beranda_tawarkan_pembeli_check.setImageResource(R.drawable.ic_keyboard_check);
            else
                l_iv_beranda_tawarkan_pembeli_check.setImageResource(R.drawable.ic_keyboard_uncheck_24dp);


        }

        @Override
        public void onClick(View view) {

            l_flag = !l_flag;

            g_map_pembeli.put(l_pembeli, l_flag);
            Log.d("BOSVOLLEY", g_map_pembeli.size() + "");

            setData(l_pembeli, l_flag);
        }
    }
}
