package com.example.bca_bos.keyboardadapters;

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
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;

import java.util.ArrayList;
import java.util.List;

public class StokProdukAdapter extends RecyclerView.Adapter<StokProdukAdapter.StokProdukItemViewHolder> implements OnCallBackListener {

    private List<Product> g_list_product_master;
    private List<Product> g_list_temp_product;
    private OnCallBackListener g_parent_oncallbacklistener;

    public StokProdukAdapter(){
        g_list_product_master = new ArrayList<>();
        g_list_temp_product = new ArrayList<>();
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
        notifyDataSetChanged();
    }

    public void getProductFiltered(int id_category){
        if(id_category == 0){
            setDatasetProdukFiltered(g_list_product_master);
        }
        else {
            List<Product> tempList = new ArrayList<>();
//            tempList.add(g_list_product_master.get(0));
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
            for (int i = 1; i < g_list_product_master.size(); i++){
                if(g_list_product_master.get(i).getProduct_name().toLowerCase().contains(p_name.toLowerCase())){
                    tempList.add(g_list_product_master.get(i));
                }
            }
            setDatasetProdukFiltered(tempList);
        }
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

    public class StokProdukItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_stokproduk;
        private TextView tv_nama;
        private TextView tv_harga;
        private TextView tv_stok;
        private LinearLayout ll_stokproduk;

        private Product l_product;
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

        public void setData(Product product){
            l_product = product;
            if(product.getBase64StringImage().isEmpty())
                iv_stokproduk.setImageResource(R.drawable.ic_bos_mascot);
            else
                iv_stokproduk.setImageBitmap(Method.convertToBitmap(l_product.getBase64StringImage()));

            tv_nama.setText(product.getProduct_name());
            tv_harga.setText(Method.getIndoCurrency(product.getPrice()));
            tv_stok.setText("Stok : " + product.getStock());
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(l_parent_oncallbacklistener != null)
            {
                l_parent_oncallbacklistener.OnCallBack(l_product);
            }
        }
    }
}
