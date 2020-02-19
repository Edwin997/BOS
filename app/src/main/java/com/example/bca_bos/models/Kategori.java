package com.example.bca_bos.models;

public class Kategori {
    private int id;
    private String nama;

    public Kategori(){

    }

    public Kategori(int p_id, String p_nama){
        this.setId(p_id);
        this.setNama(p_nama);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
