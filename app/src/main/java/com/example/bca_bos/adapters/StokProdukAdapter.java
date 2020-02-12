package com.example.bca_bos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListProdukDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.models.Produk;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StokProdukAdapter extends RecyclerView.Adapter<StokProdukAdapter.StokProdukItemViewHolder> {

    private List<Produk> g_list_produk;

    public StokProdukAdapter(){
        g_list_produk = ListProdukDummy.produkList;
    }

    @NonNull
    @Override
    public StokProdukItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_keyboard_stokproduk, parent, false);

        StokProdukItemViewHolder tmpHolder = new StokProdukItemViewHolder(l_view);

        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StokProdukItemViewHolder holder, int position) {
        holder.setData(g_list_produk.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_produk.size();
    }

    public class StokProdukItemViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_stokproduk;
        TextView tv_nama;
        TextView tv_harga;
        TextView tv_stok;

        public StokProdukItemViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_stokproduk = itemView.findViewById(R.id.iv_stokProduk_item_foto);
            tv_nama = itemView.findViewById(R.id.et_stokproduk_item_nama);
            tv_harga = itemView.findViewById(R.id.et_stokproduk_item_harga);
            tv_stok = itemView.findViewById(R.id.et_stokproduk_item_stok);
        }

        public void setData(Produk produk){
            iv_stokproduk.setImageResource(produk.getGambar());
            tv_nama.setText(produk.getNama());
            tv_harga.setText(String.valueOf(produk.getHarga()));
            tv_stok.setText(String.valueOf(produk.getStok()));
        }
    }
}
