package com.example.bca_bos.models.transactions;

import com.example.bca_bos.models.Produk;

public class TransaksiDetail {
    private int id;
    private int quantity;
    private Produk produk;
    private Transaction transaction;
    private int sell_price;

    public TransaksiDetail(){

    }

    public TransaksiDetail(int p_id, int p_quantity, Produk p_produk, Transaction p_transaction, int p_sell_price){
        this.setId(p_id);
        this.setQuantity(p_quantity);
        this.setProduk(p_produk);
        this.setTransaction(p_transaction);
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }
}
