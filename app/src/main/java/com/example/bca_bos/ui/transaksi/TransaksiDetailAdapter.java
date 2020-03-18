package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.models.transactions.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

public class TransaksiDetailAdapter extends RecyclerView.Adapter<TransaksiDetailAdapter.TransaksiDetailViewHolder>  {

    private Context g_context;

    private List<TransactionDetail> g_list_transaksidetail;

    public TransaksiDetailAdapter(){
        g_list_transaksidetail = new ArrayList<>();
    }

    @NonNull
    @Override
    public TransaksiDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater tmp_layout = LayoutInflater.from(g_context);
        View l_view = tmp_layout.inflate(R.layout.item_apps_transaksi_bottom_sheet_detail, parent, false);

        TransaksiDetailViewHolder tmpHolder = new TransaksiDetailViewHolder(l_view);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiDetailViewHolder holder, int position) {
        holder.setData(g_list_transaksidetail.get(position));
    }

    @Override
    public int getItemCount() {
        if(g_list_transaksidetail == null)
            return 0;

        return g_list_transaksidetail.size();
    }

    public void setListTransaksiDetail(List<TransactionDetail> p_list){
        g_list_transaksidetail = p_list;
        notifyDataSetChanged();
    }

    public class TransaksiDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nama_transaksi;
        private TextView tv_jumlah_transaksi;
        private TextView tv_subtotal_transaksi;

        private TransactionDetail l_transaksidetail;

        public TransaksiDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nama_transaksi = itemView.findViewById(R.id.item_apps_transaksi_bottom_sheet_detail_nama);
            tv_jumlah_transaksi = itemView.findViewById(R.id.item_apps_transaksi_bottom_sheet_detail_jumlah);
            tv_subtotal_transaksi = itemView.findViewById(R.id.item_apps_transaksi_bottom_sheet_detail_subharga);
        }

        public void setData(TransactionDetail transaksidetail) {
            l_transaksidetail = transaksidetail;

            tv_nama_transaksi.setText(l_transaksidetail.getProduct().getProduct_name());
            tv_jumlah_transaksi.setText("x" + l_transaksidetail.getQuantity());
            tv_subtotal_transaksi.setText(Method.getIndoCurrency(l_transaksidetail.getQuantity() * Double.parseDouble(l_transaksidetail.getSell_price())));

        }
    }
}
