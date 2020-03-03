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
import com.example.bca_bos.models.Transaksi;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> implements OnCallBackListener {

    private Context g_context;

    private TransaksiFragment g_parent;

    private OnCallBackListener g_parent_oncallbacklistener;

    private List<Transaksi> g_list_transaksi;

    public TransaksiAdapter(TransaksiFragment p_parent){
        g_parent = p_parent;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater tmp_layout = LayoutInflater.from(g_context);
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

        private LinearLayout ll_transaksi, ll_transaksi_left;

        private TextView tv_nama_transaksi;
        private TextView tv_tanggal_transaksi;
        private TextView tv_total_transaksi;
        private TextView tv_status_transaksi;

        private Transaksi l_transaksi;
        private OnCallBackListener l_parent_oncallbacklistener;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_transaksi = itemView.findViewById(R.id.ll_apps_transaksi_item_all);
            ll_transaksi_left = itemView.findViewById(R.id.ll_apps_transaksi_item_left);

            tv_nama_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_nama);
            tv_tanggal_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_tanggal);
            tv_total_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_total);
            tv_status_transaksi = itemView.findViewById(R.id.tv_apps_transaksi_status);

            ll_transaksi.setOnClickListener(this);
        }

        public void setData(Transaksi transaksi){
            l_transaksi = transaksi;

            tv_nama_transaksi.setText(l_transaksi.getBuyer().getNama());
            tv_tanggal_transaksi.setText(l_transaksi.getOrder_date());
            tv_total_transaksi.setText(Method.getIndoCurrency(l_transaksi.getTotal_payment()));

            if(l_transaksi.getStatus() == g_parent.KEY_STATUS_SELESAI){
                ll_transaksi_left.setBackground(g_context.getResources().getDrawable(R.drawable.style_transaction_gradient_green_layered));
                tv_total_transaksi.setTextColor(g_context.getResources().getColor(R.color.green));
                tv_status_transaksi.setText("Transaksi Selesai");
            }
            else if(l_transaksi.getStatus() == g_parent.KEY_STATUS_SUDAHDIKIRIM){
                ll_transaksi_left.setBackground(g_context.getResources().getDrawable(R.drawable.style_transaction_gradient_blue_layered));
                tv_total_transaksi.setTextColor(g_context.getResources().getColor(R.color.blue));
                tv_status_transaksi.setText("Pesanan Dikirim");
            }
            else if(l_transaksi.getStatus() == g_parent.KEY_STATUS_SUDAHDIBAYAR){
                ll_transaksi_left.setBackground(g_context.getResources().getDrawable(R.drawable.style_transaction_gradient_yellow_layered));
                tv_total_transaksi.setTextColor(g_context.getResources().getColor(R.color.yellow));
                tv_status_transaksi.setText("Pesanan Dibayar");
            }
            else if(l_transaksi.getStatus() == g_parent.KEY_STATUS_BARUMASUK){
                ll_transaksi_left.setBackground(g_context.getResources().getDrawable(R.drawable.style_transaction_gradient_red_layered));
                tv_total_transaksi.setTextColor(g_context.getResources().getColor(R.color.red));
                tv_status_transaksi.setText("Pesanan Baru");
            } else {
                ll_transaksi_left.setBackground(g_context.getResources().getDrawable(R.drawable.style_gradient_color_rounded_box_black));
                tv_total_transaksi.setTextColor(g_context.getResources().getColor(R.color.black));
                tv_status_transaksi.setText("NULL");
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
