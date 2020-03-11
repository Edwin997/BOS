package com.example.bca_bos.ui.produk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListProdukDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.products.Product;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> implements OnCallBackListener {

    private List<Product> g_list_product;
    private OnCallBackListener g_parent_oncallbacklistener;

    public ProdukAdapter(){


    }

    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_produk, parent, false);

        ProdukViewHolder tmpHolder = new ProdukViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder holder, int position) {
        holder.setData(g_list_product.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_product.size();
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

    public class ProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout ll_container_produk;
        private ImageView iv_produk;
        private TextView tv_nama_produk;
        private TextView tv_harga_produk;
        private TextView tv_stok_produk;

        private Product l_product;
        private OnCallBackListener l_parent_oncallbacklistener;

        public ProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_container_produk = itemView.findViewById(R.id.ll_apps_produk_item);

            iv_produk = itemView.findViewById(R.id.iv_apps_produk_item);
            tv_nama_produk = itemView.findViewById(R.id.tv_apps_produk_nama_item);
            tv_harga_produk = itemView.findViewById(R.id.tv_apps_produk_harga_item);
            tv_stok_produk = itemView.findViewById(R.id.tv_apps_produk_stok_item);

            ll_container_produk.setOnClickListener(this);
        }

        public void setData(Product product){
            l_product = product;
//            iv_produk.setImageResource((int)product.getImage_path());
            tv_nama_produk.setText(product.getProduct_name());
            tv_harga_produk.setText(Method.getIndoCurrency(product.getPrice()));
            tv_stok_produk.setText("Stok : " + product.getStock());
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(view == ll_container_produk){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_product);
                }
            }
        }
    }
}
