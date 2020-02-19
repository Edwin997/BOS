package com.example.bca_bos.models;

public class Pembeli {
    private int id;
    private String nama;
    private int notelp;
    private String alamat;

    public Pembeli(){}

    public Pembeli(int p_id, String p_nama, int p_notelp, String p_alamat){
        this.setId(p_id);
        this.setNama(p_nama);
        this.setNotelp(notelp);
        this.setAlamat(p_alamat);
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

    public int getNotelp() {
        return notelp;
    }

    public void setNotelp(int notelp) {
        this.notelp = notelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
