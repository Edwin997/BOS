package com.example.bca_bos.ui.beranda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListPembeliDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.models.Pembeli;

import java.util.List;

public class BerandaPembeliAdapter extends RecyclerView.Adapter<BerandaPembeliAdapter.BerandaPembeliViewHolder> {

    private List<Pembeli> g_list_pembeli;

    public BerandaPembeliAdapter(){
        g_list_pembeli = ListPembeliDummy.pembeliList;
    }

    @NonNull
    @Override
    public BerandaPembeliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_beranda_pembeli, parent, false);

        BerandaPembeliAdapter.BerandaPembeliViewHolder tmpHolder = new BerandaPembeliAdapter.BerandaPembeliViewHolder(l_view);
//        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaPembeliViewHolder holder, int position) {
        holder.setData(g_list_pembeli.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_pembeli.size();
    }

    public class BerandaPembeliViewHolder extends RecyclerView.ViewHolder {

        private TextView l_tv_beranda_pembeli_nama, l_tv_beranda_pembeli_transaksi, l_tv_beranda_pembeli_nominal;

        public BerandaPembeliViewHolder(@NonNull View itemView) {
            super(itemView);

            l_tv_beranda_pembeli_nama = itemView.findViewById(R.id.tv_apps_beranda_pembeli_nama_item);
            l_tv_beranda_pembeli_transaksi = itemView.findViewById(R.id.tv_apps_beranda_pembeli_transaksi_item);
            l_tv_beranda_pembeli_nominal = itemView.findViewById(R.id.tv_apps_beranda_pembeli_nominal_item);

        }

        public void setData(Pembeli pembeli){
            l_tv_beranda_pembeli_nama.setText(pembeli.getNama());
            l_tv_beranda_pembeli_transaksi.setText(String.valueOf(pembeli.getJumlahTransaksi()));
            l_tv_beranda_pembeli_nominal.setText(String.valueOf(pembeli.getNominalTransaksi()));
        }
    }
}
