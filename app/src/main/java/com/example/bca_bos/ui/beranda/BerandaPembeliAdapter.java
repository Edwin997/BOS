package com.example.bca_bos.ui.beranda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.Method;
import com.example.bca_bos.dummy.ListPembeliDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Pembeli;

import java.util.List;

public class BerandaPembeliAdapter extends RecyclerView.Adapter<BerandaPembeliAdapter.BerandaPembeliViewHolder> implements OnCallBackListener {

    private List<Pembeli> g_list_pembeli;
    private OnCallBackListener g_parent_oncallbacklistener;

    public BerandaPembeliAdapter(){
        g_list_pembeli = ListPembeliDummy.pembeliList;
    }

    @NonNull
    @Override
    public BerandaPembeliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_beranda_pembeli, parent, false);

        BerandaPembeliAdapter.BerandaPembeliViewHolder tmpHolder = new BerandaPembeliAdapter.BerandaPembeliViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaPembeliViewHolder holder, int position) {
        holder.setData(g_list_pembeli.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_pembeli.size();
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

    public class BerandaPembeliViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout l_ll_container_beranda_pembeli;

        private TextView l_tv_beranda_pembeli_nama, l_tv_beranda_pembeli_transaksi, l_tv_beranda_pembeli_nominal;
        private Pembeli l_pembeli;

        private OnCallBackListener l_parent_oncallbacklistener;

        public BerandaPembeliViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ll_container_beranda_pembeli = itemView.findViewById(R.id.ll_apps_beranda_pembeli_item);
            l_tv_beranda_pembeli_nama = itemView.findViewById(R.id.tv_apps_beranda_pembeli_nama_item);
            l_tv_beranda_pembeli_transaksi = itemView.findViewById(R.id.tv_apps_beranda_pembeli_transaksi_item);
            l_tv_beranda_pembeli_nominal = itemView.findViewById(R.id.tv_apps_beranda_pembeli_nominal_item);

            l_ll_container_beranda_pembeli.setOnClickListener(this);

        }

        public void setData(Pembeli pembeli){
            l_pembeli = pembeli;

            l_tv_beranda_pembeli_nama.setText(pembeli.getNama());
            l_tv_beranda_pembeli_transaksi.setText(String.valueOf(pembeli.getJumlahTransaksi()));
            l_tv_beranda_pembeli_nominal.setText(Method.getIndoCurrency(pembeli.getNominalTransaksi()));
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(view == l_ll_container_beranda_pembeli){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_pembeli);
                }
            }
        }
    }
}
