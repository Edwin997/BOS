package com.example.bca_bos.ui.template;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListTemplatedTextDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.TemplatedText;

import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder> implements OnCallBackListener{

    private List<TemplatedText> g_list_template;
    private OnCallBackListener g_parent_oncallbacklistener;

    public TemplateAdapter(){
        g_list_template = ListTemplatedTextDummy.templatedTextList;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater tmp_layout = LayoutInflater.from(parent.getContext());
        View l_view = tmp_layout.inflate(R.layout.item_apps_template, parent, false);

        TemplateViewHolder tmpHolder = new TemplateViewHolder(l_view);
        tmpHolder.setParentOnCallBack(this);
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

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack(p_obj);
        }
    }

    public class TemplateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView l_tv_template_label;
        private TextView l_tv_template_deskripsi;
        private ImageButton l_ib_template_edit;

        private TemplatedText l_template;
        private OnCallBackListener l_parent_oncallbacklistener;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);

            l_tv_template_label = itemView.findViewById(R.id.tv_apps_template_label_item);
            l_tv_template_deskripsi = itemView.findViewById(R.id.tv_apps_template_deskripsi_item);
            l_ib_template_edit = itemView.findViewById(R.id.btn_apps_template_edit_item);

            l_ib_template_edit.setOnClickListener(this);
        }

        public void setData(TemplatedText templatedText){
            l_template = templatedText;
            l_tv_template_label.setText(l_template.getTemplate_code());
            l_tv_template_deskripsi.setText(l_template.getText());
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }

        @Override
        public void onClick(View view) {
            if(view == l_ib_template_edit){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_template);
                }
            }
        }
    }
}
