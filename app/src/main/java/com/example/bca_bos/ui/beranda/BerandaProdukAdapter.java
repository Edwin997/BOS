package com.example.bca_bos.ui.beranda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListProdukDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.products.Product;

import java.util.List;

public class BerandaProdukAdapter extends RecyclerView.Adapter<BerandaProdukAdapter.BerandaProdukViewHolder> implements OnCallBackListener {

    private List<Product> g_list_product;
    private OnCallBackListener g_parent_oncallbacklistener;

    public BerandaProdukAdapter(){
        g_list_product = ListProdukDummy.productList;
    }

    @NonNull
    @Override
    public BerandaProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_beranda_produk, parent, false);

        BerandaProdukAdapter.BerandaProdukViewHolder tmpHolder = new BerandaProdukAdapter.BerandaProdukViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaProdukViewHolder holder, int position) {
        holder.setData(g_list_product.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_product.size();
    }

    public void setDatasetProduk(List<Product> p_list){
        g_list_product = p_list;

        BerandaFragment.g_instance.showLayoutProduk(p_list.size(), true);

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

    public class BerandaProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout l_ll_container_beranda_produk;

        private ImageView l_iv_beranda_produk;
        private TextView l_tv_beranda_produk_nama, l_tv_beranda_produk_harga, l_tv_beranda_produk_stok;
        private Product l_product;

        private OnCallBackListener l_parent_oncallbacklistener;

        public BerandaProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ll_container_beranda_produk = itemView.findViewById(R.id.ll_apps_beranda_produk_item);
            l_iv_beranda_produk = itemView.findViewById(R.id.iv_apps_beranda_produk_item);
            l_tv_beranda_produk_nama = itemView.findViewById(R.id.tv_apps_beranda_produk_nama_item);
            l_tv_beranda_produk_harga = itemView.findViewById(R.id.tv_apps_beranda_produk_prediksi_item);
            l_tv_beranda_produk_stok = itemView.findViewById(R.id.tv_apps_beranda_produk_stok_item);

            l_ll_container_beranda_produk.setOnClickListener(this);

        }

        public void setData(Product product){
            l_product = product;
            if(l_product.getBase64StringImage() == null || l_product.getBase64StringImage().isEmpty())
                l_iv_beranda_produk.setImageResource(R.drawable.ic_bos_mascot_default_product);
            else
                l_iv_beranda_produk.setImageBitmap(Method.convertToBitmap(product.getBase64StringImage()));

            l_tv_beranda_produk_nama.setText(product.getProduct_name());
            l_tv_beranda_produk_stok.setText("Terjual " + product.getQty()+" kali");

        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(view == l_ll_container_beranda_produk){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_product);
                }
            }
        }
    }
}
