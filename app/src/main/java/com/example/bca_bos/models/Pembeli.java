package com.example.bca_bos.models;

public class Pembeli {
    private int id;
    private String nama;
    private int notelp;
    private String alamat;
    private int jumlahTransaksi;
    private int nominalTransaksi;

    public Pembeli(){}

    public Pembeli(int p_id, String p_nama, int p_notelp, String p_alamat, int p_jumlahTransaksi, int p_nominalTransaksi){
        this.setId(p_id);
        this.setNama(p_nama);
        this.setNotelp(p_notelp);
        this.setAlamat(p_alamat);
        this.setJumlahTransaksi(p_jumlahTransaksi);
        this.setNominalTransaksi(p_nominalTransaksi);
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

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(int jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public int getNominalTransaksi() {
        return nominalTransaksi;
    }

    public void setNominalTransaksi(int nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }
}
