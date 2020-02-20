package com.example.bca_bos.models;

public class MutasiRekening {
    private int id;
    private String tanggal;
    private String nomorTransaksi;
    private String pengirim;
    private String nominal;
    private String statusTransaksi;

    public MutasiRekening(){

    }

    public MutasiRekening(int p_id, String p_tanggal, String p_nomorTransaksi, String p_pengirim, String p_nominal, String p_statusTransaksi){
        this.setId(p_id);
        this.setTanggal(p_tanggal);
        this.setNomorTransaksi(p_nomorTransaksi);
        this.setPengirim(p_pengirim);
        this.setNominal(p_nominal);
        this.setStatusTransaksi(p_statusTransaksi);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNomorTransaksi() {
        return nomorTransaksi;
    }

    public void setNomorTransaksi(String nomorTransaksi) {
        this.nomorTransaksi = nomorTransaksi;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }

    public void setStatusTransaksi(String statusTransaksi) {
        this.statusTransaksi = statusTransaksi;
    }
}
