package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OfflineTransaksiAdapter extends RecyclerView.Adapter<OfflineTransaksiAdapter.TransaksiViewHolder> implements OnCallBackListener {

    private Context g_context;

    private OfflineTransaksiFragment g_parent_offline;

    private OnCallBackListener g_parent_oncallbacklistener;

    private List<Transaction> g_list_transaction_master;
    private List<Transaction> g_list_transaction_temp;

    public OfflineTransaksiAdapter(OfflineTransaksiFragment p_parent){
        g_parent_offline = p_parent;
        g_list_transaction_master = new ArrayList<>();
        g_list_transaction_temp = new ArrayList<>();
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater tmp_layout = LayoutInflater.from(g_context);
        View l_view = tmp_layout.inflate(R.layout.item_apps_transaksi_offline, parent, false);

        TransaksiViewHolder tmpHolder = new TransaksiViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        holder.setData(g_list_transaction_temp.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_transaction_temp.size();
    }

    public int getItemMasterCount() {
        return g_list_transaction_master.size();
    }

    public List<Transaction> getListTransaction(){
        return g_list_transaction_master;
    }

    public void setListTransaksi(List<Transaction> p_list){
        g_list_transaction_master = p_list;
        setListTransaksiFiltered(g_list_transaction_master);
        notifyDataSetChanged();
    }

    public void setListTransaksiFiltered(List<Transaction> p_list){
        g_list_transaction_temp = p_list;
        notifyDataSetChanged();
    }

    public List<Transaction> getListTransaksiByType(int p_type){
        List<Transaction> tmpListTransaction = new ArrayList<>();
        tmpListTransaction = g_list_transaction_master;

        return tmpListTransaction;
    }

    public float getPersentaseTransaksiSudahSelesai(){
        float tmpHasil = 0f;
        if(g_list_transaction_master.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(g_parent_offline.KEY_STATUS_SELESAI)) / g_list_transaction_master.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiSudahDikirim(){
        float tmpHasil = 0f;
        if(g_list_transaction_master.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(g_parent_offline.KEY_STATUS_SUDAHDIKIRIM)) / g_list_transaction_master.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiSudahDiBayar(){
        float tmpHasil = 0f;
        if(g_list_transaction_master.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(g_parent_offline.KEY_STATUS_SUDAHDIBAYAR)) / g_list_transaction_master.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiBaruMasuk(){
        float tmpHasil = 0f;
        if(g_list_transaction_master.size() > 0){
            tmpHasil = (((float)countTransaksibyStatus(g_parent_offline.KEY_STATUS_BARUMASUK)) / g_list_transaction_master.size()) * 100;
        }
        return tmpHasil;
    }

    public int countTransaksibyStatus(int p_type){
        int count = 0;

        for(int i = 0; i < g_list_transaction_master.size(); i++){
            if(g_list_transaction_master.get(i).getStatus() == p_type)
                count++;
        }

        return count;
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

    public class TransaksiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout ll_transaksi;

        private TextView tv_tanggal_transaksi;
        private TextView tv_total_transaksi;

        private Transaction l_transaction;
        private OnCallBackListener l_parent_oncallbacklistener;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_transaksi = itemView.findViewById(R.id.ll_apps_transaksi_item_off);

            tv_tanggal_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_tanggal_off);
            tv_total_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_total_off);

            ll_transaksi.setOnClickListener(this);
        }

        public void setData(Transaction transaction){
            l_transaction = transaction;

            tv_tanggal_transaksi.setText(Method.formatDate(l_transaction.getOrder_time()));
            tv_total_transaksi.setText(Method.getIndoCurrency(Double.parseDouble(l_transaction.getTotal_payment())));
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(l_parent_oncallbacklistener != null)
            {
                l_parent_oncallbacklistener.OnCallBack(l_transaction);
            }
        }
    }
}
