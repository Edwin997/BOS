package com.example.bca_bos.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TemplatedText implements Serializable, Comparable<TemplatedText> {

    private int id_template_text;
    private String template_code; //20
    private String text; //200
    private Seller seller;

    public TemplatedText(int p_id, String p_label, String p_description){
        this.setId_template_text(p_id);
        this.setTemplate_code(p_label);
        this.setText(p_description);
    }

    @Override
    public int compareTo(TemplatedText p_template) {
        if(getTemplate_code() == null || p_template.getTemplate_code() == null)
            return 0;

        return getTemplate_code().toLowerCase().compareTo(p_template.getTemplate_code().toLowerCase());
    }
}
