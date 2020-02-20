package com.example.bca_bos.models;

public class TransaksiDetail {
    private int id;
    private int quantity;
    private Produk produk;
    private Transaksi transaksi;
    private int sell_price;

    public TransaksiDetail(){

    }

    public TransaksiDetail(int p_id, int p_quantity, Produk p_produk, Transaksi p_transaksi, int p_sell_price){
        this.setId(p_id);
        this.setQuantity(p_quantity);
        this.setProduk(p_produk);
        this.setTransaksi(p_transaksi);
        this.setSell_price(p_sell_price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }
}
