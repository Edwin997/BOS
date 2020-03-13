package com.example.bca_bos.keyboardadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListTemplatedTextDummy;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.TemplatedText;

import java.util.ArrayList;
import java.util.List;

public class TemplatedTextAdapter extends RecyclerView.Adapter<TemplatedTextAdapter.TemplatedTextViewHolder> implements OnCallBackListener {

    private List<TemplatedText> g_list_templatedtext;
    private Context g_context;
    private OnCallBackListener g_parent_oncallbacklistener;

    public TemplatedTextAdapter(){
        g_list_templatedtext = new ArrayList<>();
    }

    @NonNull
    @Override
    public TemplatedTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(g_context);
        View view = inflater.inflate(R.layout.item_keyboard_templatedtext, parent, false);
        TemplatedTextViewHolder ttvh = new TemplatedTextViewHolder(view);
        ttvh.setParentOnCallBack(this);
        return ttvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TemplatedTextViewHolder holder, int position) {
        if(position == g_list_templatedtext.size()-1){
            holder.bind(g_list_templatedtext.get(position), false);
        }else{
            holder.bind(g_list_templatedtext.get(position), true);
        }

    }

    @Override
    public int getItemCount() {
        return g_list_templatedtext.size();
    }

    public void setParentOnCallBack(OnCallBackListener p_oncallback){
        this.g_parent_oncallbacklistener = p_oncallback;
    }

    public void setListTemplate(List<TemplatedText> p_list){
        this.g_list_templatedtext = p_list;
        notifyDataSetChanged();
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(g_parent_oncallbacklistener != null){
            g_parent_oncallbacklistener.OnCallBack("TEXT;" + p_obj.toString());
        }
    }

    public class TemplatedTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View l_view_template;

        private Button l_btn_templatedtext;
        private OnCallBackListener l_parent_oncallbacklistener;
        private TemplatedText l_templatedtext;

        public TemplatedTextViewHolder(@NonNull View itemView) {
            super(itemView);

            l_view_template = itemView.findViewById(R.id.keyboard_template_divider);

            l_btn_templatedtext = itemView.findViewById(R.id.btn_templatedtext);
            l_btn_templatedtext.setOnClickListener(this);
        }

        public void bind(TemplatedText p_templatedtext, Boolean p_showdivider){
            l_templatedtext = p_templatedtext;
            l_btn_templatedtext.setText(l_templatedtext.getTemplate_code().toUpperCase());
            if(p_showdivider)
                l_view_template.setVisibility(View.VISIBLE);
            else
                l_view_template.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            if(v == l_btn_templatedtext){
                if(l_parent_oncallbacklistener != null){
                    l_parent_oncallbacklistener.OnCallBack(l_templatedtext.getText());
                }
            }
        }

        public void setParentOnCallBack(OnCallBackListener p_oncallback){
            this.l_parent_oncallbacklistener = p_oncallback;
        }
    }
}
