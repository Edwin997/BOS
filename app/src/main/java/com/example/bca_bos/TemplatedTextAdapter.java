package com.example.bca_bos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.models.TemplatedText;

import java.util.List;

public class TemplatedTextAdapter extends RecyclerView.Adapter<TemplatedTextAdapter.TemplatedTextViewHolder> {

    private List<TemplatedText> g_list_templatedtext;
    private Context g_context;

    public TemplatedTextAdapter(){
        g_list_templatedtext = ListTemplatedTextDummy.templatedTextList;
    }

    @NonNull
    @Override
    public TemplatedTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        g_context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(g_context);
        View view = inflater.inflate(R.layout.item_templatedtext, parent, false);
        TemplatedTextViewHolder ttvh = new TemplatedTextViewHolder(view);
        return ttvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TemplatedTextViewHolder holder, int position) {
        holder.bind(g_list_templatedtext.get(position));
    }

    @Override
    public int getItemCount() {
        return g_list_templatedtext.size();
    }

    public class TemplatedTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button l_btn_templatedtext;

        public TemplatedTextViewHolder(@NonNull View itemView) {
            super(itemView);

            l_btn_templatedtext = itemView.findViewById(R.id.btn_templatedtext);
            l_btn_templatedtext.setOnClickListener(this);
        }

        public void bind(TemplatedText p_templatedtext){
            l_btn_templatedtext.setText(p_templatedtext.getLabel());
        }

        @Override
        public void onClick(View v) {
            if(v == l_btn_templatedtext){
                Button btn = (Button) v;
                Toast.makeText(g_context , btn.getText().toString() , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
