package com.example.bca_bos.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Produk implements Parcelable {

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

    protected Produk(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        stok = in.readInt();
        gambar = in.readInt();
        harga = in.readInt();
    }

    public static final Creator<Produk> CREATOR = new Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel in) {
            return new Produk(in);
        }

        @Override
        public Produk[] newArray(int size) {
            return new Produk[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeInt(stok);
        dest.writeInt(gambar);
        dest.writeInt(harga);
    }
}