package com.example.bca_bos.ui.template;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListProdukDummy;
import com.example.bca_bos.ListTemplatedTextDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;
import com.example.bca_bos.models.TemplatedText;

import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder> {

    private List<TemplatedText> g_list_template;

    public TemplateAdapter(){
        g_list_template = ListTemplatedTextDummy.templatedTextList;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_template, parent, false);

        TemplateViewHolder tmpHolder = new TemplateViewHolder(l_view);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        holder.setData(g_list_template.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_template.size();
    }

    public class TemplateViewHolder extends RecyclerView.ViewHolder{

        private TextView l_tv_template_label;
        private ImageButton l_ib_template_delete;

        private TemplatedText l_template;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);

            l_ib_template_delete = itemView.findViewById(R.id.btn_apps_template_delete_item);
            l_tv_template_label = itemView.findViewById(R.id.tv_apps_template_label_item);
        }

        public void setData(TemplatedText templatedText){
            l_template = templatedText;
            l_tv_template_label.setText(l_template.getLabel());
        }
    }
}
