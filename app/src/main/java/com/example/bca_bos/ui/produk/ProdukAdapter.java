package com.example.bca_bos.ui.produk;

import android.os.Build;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> implements OnCallBackListener {

    private List<Product> g_list_product_master;
    private List<Product> g_list_temp_product;
    private OnCallBackListener g_parent_oncallbacklistener;

    public ProdukAdapter(){
        g_list_product_master = new ArrayList<>();
        g_list_temp_product = new ArrayList<>();
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
        holder.setData(g_list_temp_product.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_temp_product.size();
    }

    public void setDatasetProduk(List<Product> p_list){
        g_list_product_master = p_list;
        setDatasetProdukFiltered(g_list_product_master);
        notifyDataSetChanged();
    }

    public void setDatasetProdukFiltered(List<Product> p_list){
        g_list_temp_product = p_list;

        ProdukFragment.g_instance.showLayout(p_list.size(), true);

        notifyDataSetChanged();
    }

    public void getProductFiltered(int id_category){
        if(id_category == 0){
            setDatasetProdukFiltered(g_list_product_master);
        }
        else {
            List<Product> tempList = new ArrayList<>();
            for (int i = 0; i < g_list_product_master.size(); i++){
                if(g_list_product_master.get(i).getPrdCategory().getId_prd_category() == id_category){
                    tempList.add(g_list_product_master.get(i));
                }
            }
            setDatasetProdukFiltered(tempList);
        }
    }

    public void findProduct(String p_name){
        if(p_name.equals("")){
            setDatasetProdukFiltered(g_list_product_master);
        }
        else {
            List<Product> tempList = new ArrayList<>();
            for (int i = 0; i < g_list_product_master.size(); i++){
                if(g_list_product_master.get(i).getProduct_name().toLowerCase().contains(p_name.toLowerCase())){
                    tempList.add(g_list_product_master.get(i));
                }
            }
            setDatasetProdukFiltered(tempList);
        }
    }

    public void sortProduct(String p_type){
        List<Product> tempList = g_list_product_master;
        if(p_type.equals("ASC"))
        {
            Collections.sort(tempList);
        }
        else if(p_type.equals("DESC")){
            Collections.reverse(tempList);
        }
        setDatasetProdukFiltered(tempList);
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
            if(l_product.getBase64StringImage().equals(""))
            {
                iv_produk.setImageResource(R.drawable.ic_bos_mascot);
            }
            else {
                iv_produk.setImageBitmap(Method.convertToBitmap(l_product.getBase64StringImage()));
            }

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
