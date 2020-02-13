package com.example.bca_bos.models;

public class TemplatedText {
    private int id;
    private String label;
    private String description;

    public TemplatedText(){

    }

    public TemplatedText(int p_id, String p_label, String p_description){
        this.setId(p_id);
        this.setLabel(p_label);
        this.setDescription(p_description);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
