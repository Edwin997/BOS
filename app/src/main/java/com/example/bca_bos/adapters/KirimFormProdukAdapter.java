package com.example.bca_bos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListProdukDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class KirimFormProdukAdapter extends RecyclerView.Adapter<KirimFormProdukAdapter.KirimFormProdukItemViewHolder> implements OnCallBackListener {

    private List<Produk> g_list_produk;
    private OnCallBackListener g_parent_oncallbacklistener;

    public KirimFormProdukAdapter(){
        g_list_produk = ListProdukDummy.produkList;
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
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack("SUBTOTAL;"+p_obj.toString());
        }
    }

    public class KirimFormProdukItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout ll_kirimformproduk;

        private RoundedImageView iv_kirimformproduk;
        private TextView tv_nama;
        private TextView tv_harga;
        private TextView tv_jumlah;

        private ImageButton btn_add_count;
        private ImageButton btn_minus_count;

        private Produk l_produk;
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

        public void setData(Produk produk){
            l_produk = produk;
            iv_kirimformproduk.setImageResource(produk.getGambar());
            tv_nama.setText(produk.getNama());
            tv_harga.setText(String.valueOf(produk.getHarga()));

            if(Integer.parseInt(tv_jumlah.getText().toString()) == 0){
                btn_minus_count.setVisibility(View.INVISIBLE);
                btn_minus_count.setEnabled(false);
            }

            if(Integer.parseInt(tv_jumlah.getText().toString()) == l_produk.getStok()){
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
                if(count <= l_produk.getStok()){
                    tv_jumlah.setText(String.valueOf(count));
                }
                if(count >= l_produk.getStok()){
                    btn_add_count.setVisibility(View.INVISIBLE);
                    btn_add_count.setEnabled(false);
                }
                l_parent_oncallbacklistener.OnCallBack((countawal * l_produk.getHarga()) + ";" +
                        (count * l_produk.getHarga()));
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
                l_parent_oncallbacklistener.OnCallBack((countawal * l_produk.getHarga()) + ";" +
                        (count * l_produk.getHarga()));
            }


        }
    }
}
