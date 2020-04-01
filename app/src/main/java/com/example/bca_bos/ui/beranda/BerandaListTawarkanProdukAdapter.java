package com.example.bca_bos.ui.beranda;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.dummy.ListProdukDummy;
import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.products.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BerandaListTawarkanProdukAdapter extends RecyclerView.Adapter<BerandaListTawarkanProdukAdapter.BerandaListTawarkanProdukViewHolder> {

    private List<Product> g_list_product;
    private HashMap<Product, Boolean> g_map_pembeli;

    public BerandaListTawarkanProdukAdapter(){
        g_list_product = ListProdukDummy.productList;
        g_map_pembeli = new HashMap<>();

        for(int i = 0; i < g_list_product.size(); i++){
            g_map_pembeli.put(g_list_product.get(i), false);
        }
    }

    @NonNull
    @Override
    public BerandaListTawarkanProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_dialog_listitem_beranda, parent, false);

        BerandaListTawarkanProdukViewHolder tmpHolder = new BerandaListTawarkanProdukViewHolder(l_view);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaListTawarkanProdukViewHolder holder, int position) {
        holder.setData(g_list_product.get(position), g_map_pembeli.get(g_list_product.get(position)));
    }

    @Override
    public int getItemCount() {
        return g_list_product.size();
    }

    public void setDatasetProduct(List<Product> p_list){
        g_list_product = p_list;

        g_map_pembeli.clear();
        for(int i = 0; i < g_list_product.size(); i++){
            g_map_pembeli.put(g_list_product.get(i), false);
        }

        notifyDataSetChanged();
    }

    public String getProdukUntukTawarkan(){
        String tmpHasil = "";

        for (Map.Entry<Product, Boolean> tmpProduk: g_map_pembeli.entrySet()) {
            if(tmpProduk.getValue()) {
                tmpHasil += "\n- " + tmpProduk.getKey().getProduct_name() + ", Harga : " + Method.getIndoCurrency(tmpProduk.getKey().getPrice());
            }
        }

        return tmpHasil;
    }

    public class BerandaListTawarkanProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout l_ll_container_beranda_list_tawarkan_produk;

        private TextView l_tv_beranda_tawarkan_produk_nama, l_tv_beranda_tawarkan_produk_harga;
        private ImageView l_iv_beranda_tawarkan_produk_check;

        private Product l_product;
        private boolean l_flag;

        public BerandaListTawarkanProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ll_container_beranda_list_tawarkan_produk = itemView.findViewById(R.id.item_dialog_listitem_beranda_layout);
            l_tv_beranda_tawarkan_produk_nama = itemView.findViewById(R.id.item_dialog_listitem_beranda_nama);
            l_tv_beranda_tawarkan_produk_harga = itemView.findViewById(R.id.item_dialog_listitem_beranda_subketerangan);
            l_iv_beranda_tawarkan_produk_check = itemView.findViewById(R.id.item_dialog_listitem_beranda_check);

            l_ll_container_beranda_list_tawarkan_produk.setOnClickListener(this);
        }

        public void setData(Product product, boolean flag){
            l_product = product;
            l_flag = flag;

            l_tv_beranda_tawarkan_produk_nama.setText(product.getProduct_name());
            l_tv_beranda_tawarkan_produk_harga.setText(Method.getIndoCurrency(product.getPrice()));

            if(l_flag)
                l_iv_beranda_tawarkan_produk_check.setImageResource(R.drawable.ic_keyboard_check);
            else
                l_iv_beranda_tawarkan_produk_check.setImageResource(R.drawable.ic_keyboard_uncheck_24dp);


        }

        @Override
        public void onClick(View view) {

            l_flag = !l_flag;

            g_map_pembeli.put(l_product, l_flag);
            Log.d("BOSVOLLEY", g_map_pembeli.size() + "");

            setData(l_product, l_flag);
        }
    }
}
