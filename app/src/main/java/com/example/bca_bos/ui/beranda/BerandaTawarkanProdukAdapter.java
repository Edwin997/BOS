package com.example.bca_bos.ui.beranda;

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
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.products.Product;

import java.util.ArrayList;
import java.util.List;

public class BerandaTawarkanProdukAdapter extends RecyclerView.Adapter<BerandaTawarkanProdukAdapter.BerandaTawarkanProdukViewHolder> implements OnCallBackListener {

    private List<Product> g_list_product;
    private OnCallBackListener g_parent_oncallbacklistener;

    public BerandaTawarkanProdukAdapter(){
        g_list_product = new ArrayList<>();
    }

    public BerandaTawarkanProdukAdapter(List<Product> p_list){
        g_list_product = p_list;
    }

    @NonNull
    @Override
    public BerandaTawarkanProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_beranda_tawarkan_produk, parent, false);

        BerandaTawarkanProdukAdapter.BerandaTawarkanProdukViewHolder tmpHolder = new BerandaTawarkanProdukAdapter.BerandaTawarkanProdukViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaTawarkanProdukViewHolder holder, int position) {
        holder.setData(g_list_product.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_product.size();
    }

    public List<Product> getListProductFavorit(){
        return g_list_product;
    }

    public void setDatasetProduk(List<Product> p_list){
        g_list_product = p_list;
        notifyDataSetChanged();
    }

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack(p_obj);
        }
    }

    public class BerandaTawarkanProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout l_ll_container_beranda_tawarkan_produk;

        private ImageView l_iv_beranda_tawarkan_produk;
        private TextView l_tv_beranda_tawarkan_produk_nama, l_tv_beranda_tawarkan_produk_harga, l_tv_beranda_tawarkan_produk_stok;
        private Product l_product;

        private OnCallBackListener l_parent_oncallbacklistener;

        public BerandaTawarkanProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ll_container_beranda_tawarkan_produk = itemView.findViewById(R.id.ll_apps_beranda_tawarkan_produk_item);
            l_iv_beranda_tawarkan_produk = itemView.findViewById(R.id.iv_apps_beranda_tawarkan_produk_item);
            l_tv_beranda_tawarkan_produk_nama = itemView.findViewById(R.id.tv_apps_beranda_tawarkan_produk_nama_item);
            l_tv_beranda_tawarkan_produk_harga = itemView.findViewById(R.id.tv_apps_beranda_tawarkan_produk_harga_item);
            l_tv_beranda_tawarkan_produk_stok = itemView.findViewById(R.id.tv_apps_beranda_tawarkan_produk_stok_item);

            l_ll_container_beranda_tawarkan_produk.setOnClickListener(this);

        }

        public void setData(Product product){
            l_product = product;
            if(l_product.getBase64StringImage() == null || l_product.getBase64StringImage().isEmpty())
                l_iv_beranda_tawarkan_produk.setImageResource(R.drawable.ic_bos_mascot);
            else
                l_iv_beranda_tawarkan_produk.setImageBitmap(Method.convertToBitmap(l_product.getBase64StringImage()));

            l_tv_beranda_tawarkan_produk_nama.setText(product.getProduct_name());
            l_tv_beranda_tawarkan_produk_harga.setText(Method.getIndoCurrency(product.getPrice()));
            l_tv_beranda_tawarkan_produk_stok.setText("Stok : " + product.getStock());

        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(view == l_ll_container_beranda_tawarkan_produk){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_product);
                }
            }
        }
    }
}
