package com.example.bca_bos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListMutasiRekeningDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.MutasiRekening;
import com.example.bca_bos.models.TemplatedText;

import java.util.List;

public class MutasiRekeningAdapter extends RecyclerView.Adapter<MutasiRekeningAdapter.MutasiRekeningViewHolder> {

    private List<MutasiRekening> g_list_mutasirekening;
    private Context g_context;
    private OnCallBackListener g_parent_oncallbacklistener;

    public class MutasiRekeningViewHolder extends RecyclerView.ViewHolder {

        private TextView l_mutasi_tv_pengirim, l_mutasi_tv_tanggal, l_mutasi_tv_nominal, l_mutasi_tv_status;

        public MutasiRekeningViewHolder(@NonNull View itemView) {
            super(itemView);

            l_mutasi_tv_pengirim = itemView.findViewById(R.id.bcabos_mutasi_pengirim_text);
            l_mutasi_tv_tanggal = itemView.findViewById(R.id.bcabos_mutasi_tanggal_text);
            l_mutasi_tv_nominal = itemView.findViewById(R.id.bcabos_mutasi_nominal_text);
            l_mutasi_tv_status = itemView.findViewById(R.id.bcabos_mutasi_status_text);

        }
    }

    public MutasiRekeningAdapter(){
        g_list_mutasirekening = ListMutasiRekeningDummy.mutasiRekeningList;
    }

    @NonNull
    @Override
    public MutasiRekeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(g_context);
        View view = inflater.inflate(R.layout.item_mutasirekening, parent, false);
        MutasiRekeningAdapter.MutasiRekeningViewHolder mrvh = new MutasiRekeningAdapter.MutasiRekeningViewHolder(view);

        return mrvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MutasiRekeningViewHolder holder, int position) {
        holder.l_mutasi_tv_pengirim.setText(g_list_mutasirekening.get(position).getPengirim());
        holder.l_mutasi_tv_tanggal.setText(g_list_mutasirekening.get(position).getTanggal());
        holder.l_mutasi_tv_nominal.setText(Method.getIndoCurrency(Integer.parseInt(g_list_mutasirekening.get(position).getNominal())));
        holder.l_mutasi_tv_status.setText(g_list_mutasirekening.get(position).getStatusTransaksi());
    }

    @Override
    public int getItemCount() {
        return g_list_mutasirekening.size();
    }
}
