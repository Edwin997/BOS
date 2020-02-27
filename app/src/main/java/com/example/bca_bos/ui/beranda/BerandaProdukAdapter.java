package com.example.bca_bos.ui.beranda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListProdukDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.models.Produk;

import java.util.List;

public class BerandaProdukAdapter extends RecyclerView.Adapter<BerandaProdukAdapter.BerandaProdukViewHolder> {

    private List<Produk> g_list_produk;

    public BerandaProdukAdapter(){
        g_list_produk = ListProdukDummy.produkList;
    }

    @NonNull
    @Override
    public BerandaProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_beranda_produk, parent, false);

        BerandaProdukAdapter.BerandaProdukViewHolder tmpHolder = new BerandaProdukAdapter.BerandaProdukViewHolder(l_view);
//        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaProdukViewHolder holder, int position) {
        holder.setData(g_list_produk.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_produk.size();
    }

    public class BerandaProdukViewHolder extends RecyclerView.ViewHolder {

        private ImageView l_iv_beranda_produk;
        private TextView l_tv_beranda_produk_nama, l_tv_beranda_produk_harga, l_tv_beranda_produk_stok;
        private Produk l_produk;

        public BerandaProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            l_iv_beranda_produk = itemView.findViewById(R.id.iv_apps_beranda_produk_item);
            l_tv_beranda_produk_nama = itemView.findViewById(R.id.tv_apps_beranda_produk_nama_item);
            l_tv_beranda_produk_harga = itemView.findViewById(R.id.tv_apps_beranda_produk_harga_item);
            l_tv_beranda_produk_stok = itemView.findViewById(R.id.tv_apps_beranda_produk_stok_item);

        }

        public void setData(Produk produk){
            l_iv_beranda_produk.setImageResource(produk.getGambar());
            l_tv_beranda_produk_nama.setText(produk.getNama());
            l_tv_beranda_produk_harga.setText(Method.getIndoCurrency(produk.getHarga()));
            l_tv_beranda_produk_stok.setText("Stok : " + produk.getStok());
        }
    }
}