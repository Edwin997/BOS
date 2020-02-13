package com.example.bca_bos.models;

public class Produk {

    private int id;
    private String nama;
    private int stok;
    private int gambar;
    private int harga;

    public Produk(){

    }

    public Produk(int id, String nama, int stok, int gambar, int harga){
        this.setId(id);
        this.setNama(nama);
        this.setStok(stok);
        this.setGambar(gambar);
        this.setHarga(harga);
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

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

}
