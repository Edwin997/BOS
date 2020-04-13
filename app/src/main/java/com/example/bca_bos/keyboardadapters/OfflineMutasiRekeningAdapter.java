package com.example.bca_bos.keyboardadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.KeyboardBOSnew;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OfflineMutasiRekeningAdapter extends RecyclerView.Adapter<OfflineMutasiRekeningAdapter.OfflineMutasiRekeningViewHolder> implements OnCallBackListener {

    private Context g_context;
    private OnCallBackListener g_parent_oncallbacklistener;

    private List<Transaction> g_list_transaction_master;
    private Transaction l_transaction;

    public OfflineMutasiRekeningAdapter(){
        g_list_transaction_master = new ArrayList<>();
    }

    @NonNull
    @Override
    public OfflineMutasiRekeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(g_context);
        View view = inflater.inflate(R.layout.item_keyboard_mutasirekening, parent, false);
        OfflineMutasiRekeningAdapter.OfflineMutasiRekeningViewHolder mrvh = new OfflineMutasiRekeningAdapter.OfflineMutasiRekeningViewHolder(view);

        return mrvh;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineMutasiRekeningViewHolder holder, int position) {
        holder.setData(g_list_transaction_master.get(position));

    }

    @Override
    public int getItemCount() {
        return g_list_transaction_master.size();
    }

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    public void setListTransaksi(List<Transaction> p_list){
        g_list_transaction_master = p_list;
        KeyboardBOSnew.g_instance.showLayoutMutasi(g_list_transaction_master.size(), true);
        notifyDataSetChanged();
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack(p_obj);
        }
    }

    public class OfflineMutasiRekeningViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout l_mutasi_ll;
        private TextView l_mutasi_tv_pembeli, l_mutasi_tv_tanggal, l_mutasi_tv_nominal, l_mutasi_tv_status;

        public OfflineMutasiRekeningViewHolder(@NonNull View itemView) {
            super(itemView);

            l_mutasi_ll = itemView.findViewById(R.id.bcabos_mutasi_liniear_layout);
            l_mutasi_tv_pembeli = itemView.findViewById(R.id.bcabos_mutasi_pembeli_text);
            l_mutasi_tv_pembeli.setTextColor(g_context.getResources().getColor(R.color.black));
            l_mutasi_tv_tanggal = itemView.findViewById(R.id.bcabos_mutasi_tanggal_text);
            l_mutasi_tv_tanggal.setTextColor(g_context.getResources().getColor(R.color.black));
            l_mutasi_tv_nominal = itemView.findViewById(R.id.bcabos_mutasi_nominal_text);
            l_mutasi_tv_nominal.setTextColor(g_context.getResources().getColor(R.color.black));
            l_mutasi_tv_status = itemView.findViewById(R.id.bcabos_mutasi_status_text);
            l_mutasi_tv_status.setTextColor(g_context.getResources().getColor(R.color.black));

        }

        public void setData(Transaction transaction){
            l_transaction = transaction;

            l_mutasi_tv_pembeli.setText("Transaksi Offline");
            l_mutasi_tv_tanggal.setText(l_transaction.getOrder_time());
            l_mutasi_tv_nominal.setText(Method.getIndoCurrency(Double.parseDouble(l_transaction.getTotal_payment())));
            l_mutasi_tv_status.setVisibility(View.GONE);
            l_mutasi_ll.setBackground(g_context.getResources().getDrawable(R.drawable.style_transaction_gradient_white));

        }
    }

}
