package com.example.bca_bos.keyboardadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class KirimFormProdukAdapter extends RecyclerView.Adapter<KirimFormProdukAdapter.KirimFormProdukItemViewHolder> implements OnCallBackListener {

    private List<Product> g_list_product;
    private OnCallBackListener g_parent_oncallbacklistener;

    public KirimFormProdukAdapter(){
        g_list_product = ListProdukDummy.productList;
    }

    @NonNull
    @Override
    public KirimFormProdukItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_keyboard_kirimform_produk, parent, false);

        KirimFormProdukItemViewHolder tmpHolder = new KirimFormProdukItemViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KirimFormProdukItemViewHolder holder, int position) {
        holder.setData(g_list_product.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_product.size();
    }

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack("SUBTOTAL;"+p_obj.toString());
        }
    }

    public class KirimFormProdukItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout ll_kirimformproduk;

        private ImageView iv_kirimformproduk;
        private TextView tv_nama;
        private TextView tv_harga;
        private TextView tv_jumlah;

        private ImageButton btn_add_count;
        private ImageButton btn_minus_count;

        private Product l_product;
        private OnCallBackListener l_parent_oncallbacklistener;

        public KirimFormProdukItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_kirimformproduk = itemView.findViewById(R.id.layout_kirimform_produk_item);
            iv_kirimformproduk = itemView.findViewById(R.id.iv_kirimform_produk_item_foto);
            tv_nama = itemView.findViewById(R.id.et_kirimform_produk_item_nama);
            tv_harga = itemView.findViewById(R.id.et_kirimform_produk_item_harga);
            tv_jumlah = itemView.findViewById(R.id.tv_kirimform_produk_item_show_count);
            btn_add_count = itemView.findViewById(R.id.btn_kirimform_produk_item_add_count);
            btn_minus_count = itemView.findViewById(R.id.btn_kirimform_produk_item_minus_count);

            btn_add_count.setOnClickListener(this);
            btn_minus_count.setOnClickListener(this);



            ll_kirimformproduk.setOnClickListener(this);
        }

        public void setData(Product product){
            l_product = product;
//            iv_kirimformproduk.setImageResource(product.getBase64StringImage());
            tv_nama.setText(product.getProduct_name());
            tv_harga.setText(Method.getIndoCurrency(product.getPrice()));

            if(Integer.parseInt(tv_jumlah.getText().toString()) == 0){
                btn_minus_count.setVisibility(View.INVISIBLE);
                btn_minus_count.setEnabled(false);
            }

            if(Integer.parseInt(tv_jumlah.getText().toString()) == l_product.getStock()){
                btn_add_count.setVisibility(View.INVISIBLE);
                btn_add_count.setEnabled(false);
            }
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            int countawal = Integer.parseInt(tv_jumlah.getText().toString());
            int count = 0;
            if(view == btn_add_count){
                btn_minus_count.setVisibility(View.VISIBLE);
                btn_minus_count.setEnabled(true);
                count = Integer.parseInt(tv_jumlah.getText().toString()) + 1;
                if(count <= l_product.getStock()){
                    tv_jumlah.setText(String.valueOf(count));
                }
                if(count >= l_product.getStock()){
                    btn_add_count.setVisibility(View.INVISIBLE);
                    btn_add_count.setEnabled(false);
                }
                l_parent_oncallbacklistener.OnCallBack((countawal * l_product.getPrice()) + ";" +
                        (count * l_product.getPrice()));
            }
            else if(view == btn_minus_count){
                btn_add_count.setVisibility(View.VISIBLE);
                btn_add_count.setEnabled(true);
                count = Integer.parseInt(tv_jumlah.getText().toString()) - 1;
                if(count >= 0){
                    tv_jumlah.setText(String.valueOf(count));
                }
                if(count <= 0){
                    btn_minus_count.setVisibility(View.INVISIBLE);
                    btn_minus_count.setEnabled(false);
                }
                l_parent_oncallbacklistener.OnCallBack((countawal * l_product.getPrice()) + ";" +
                        (count * l_product.getPrice()));
            }


        }
    }
}
