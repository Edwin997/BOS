package com.example.bca_bos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListProdukDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StokProdukAdapter extends RecyclerView.Adapter<StokProdukAdapter.StokProdukItemViewHolder> implements OnCallBackListener {

    private List<Produk> g_list_produk;
    private OnCallBackListener g_parent_oncallbacklistener;

    public StokProdukAdapter(){
        g_list_produk = ListProdukDummy.produkList;
    }

    @NonNull
    @Override
    public StokProdukItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_keyboard_stokproduk, parent, false);

        StokProdukItemViewHolder tmpHolder = new StokProdukItemViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
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

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    @Override
    public void OnCallBack(String p_text) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack("STOK;"+p_text);
        }
    }

    public class StokProdukItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_stokproduk;
        private TextView tv_nama;
        private TextView tv_harga;
        private TextView tv_stok;
        private LinearLayout ll_stokproduk;

        private Produk l_produk;
        private OnCallBackListener l_parent_oncallbacklistener;

        public StokProdukItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_stokproduk = itemView.findViewById(R.id.layout_stokproduk_item);
            iv_stokproduk = itemView.findViewById(R.id.iv_stokProduk_item_foto);
            tv_nama = itemView.findViewById(R.id.et_stokproduk_item_nama);
            tv_harga = itemView.findViewById(R.id.et_stokproduk_item_harga);
            tv_stok = itemView.findViewById(R.id.et_stokproduk_item_stok);

            ll_stokproduk.setOnClickListener(this);
        }

        public void setData(Produk produk){
            l_produk = produk;
            iv_stokproduk.setImageResource(produk.getGambar());
            tv_nama.setText(produk.getNama());
            tv_harga.setText(String.valueOf(produk.getHarga()));
            tv_stok.setText(String.valueOf(produk.getStok()));
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(l_parent_oncallbacklistener != null)
            {
                l_parent_oncallbacklistener.OnCallBack(String.valueOf(l_produk.getStok()));
            }
        }
    }
}
