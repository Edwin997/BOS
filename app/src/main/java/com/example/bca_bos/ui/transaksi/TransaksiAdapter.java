package com.example.bca_bos.ui.transaksi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListTransaksiDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Transaksi;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> implements OnCallBackListener {

    private List<Transaksi> g_list_transaksi;
    private OnCallBackListener g_parent_oncallbacklistener;

    public final int KEY_STATUS_BARUMASUK = 0;
    public final int KEY_STATUS_SUDAHDIBAYAR = 1;
    public final int KEY_STATUS_SUDAHDIKIRIM = 2;
    public final int KEY_STATUS_SELESAI = 3;

    public TransaksiAdapter(){
        g_list_transaksi = ListTransaksiDummy.transaksiList;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_transaksi, parent, false);

        TransaksiViewHolder tmpHolder = new TransaksiViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        holder.setData(g_list_transaksi.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_transaksi.size();
    }

    public void setListTransaksi(List<Transaksi> p_list){
        g_list_transaksi = p_list;
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

    public class TransaksiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_nama_transaksi;
        private TextView tv_tanggal_transaksi;
        private TextView tv_total_transaksi;
        private TextView tv_status_transaksi;

        private Transaksi l_transaksi;
        private OnCallBackListener l_parent_oncallbacklistener;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nama_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_nama);
            tv_tanggal_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_tanggal);
            tv_total_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_total);
            tv_status_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_status);
        }

        public void setData(Transaksi transaksi){
            l_transaksi = transaksi;

            tv_nama_transaksi.setText(l_transaksi.getBuyer().getNama());
            tv_tanggal_transaksi.setText(l_transaksi.getOrder_date());
            tv_total_transaksi.setText(Method.getIndoCurrency(l_transaksi.getTotal_payment()));
            if(l_transaksi.getStatus() == KEY_STATUS_SELESAI){
                tv_status_transaksi.setText("TRANSAKSI SELESAI");
            }
            else if(l_transaksi.getStatus() == KEY_STATUS_SUDAHDIKIRIM){
                tv_status_transaksi.setText("PROSES PENGIRIMAN");
            }
            else if(l_transaksi.getStatus() == KEY_STATUS_SUDAHDIBAYAR){
                tv_status_transaksi.setText("SUDAH DIBAYAR");
            }
            else{
                tv_status_transaksi.setText("BARU MASUK");
            }

        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(l_parent_oncallbacklistener != null)
            {
                l_parent_oncallbacklistener.OnCallBack(l_transaksi);
            }
        }
    }
}
