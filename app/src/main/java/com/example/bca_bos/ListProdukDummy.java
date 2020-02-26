package com.example.bca_bos;

import com.example.bca_bos.models.Kategori;
import com.example.bca_bos.models.Produk;

import java.util.ArrayList;
import java.util.List;

public class ListProdukDummy {

    private static List<Kategori> list = ListKategoriDummy.kategoriList;

    public static List<Produk> produkList = new ArrayList<Produk>() {{
        add(new Produk(1, "Jordan 1 High - Bred Toe", 20, R.drawable.shoes_red_jordan, 5500000, list.get(0)));
        add(new Produk(2, "Balenciaga Triple S - Triple Black", 10, R.drawable.shoes_black_balenciaga, 13000000, list.get(2)));
        add(new Produk(3, "Jordan 1 High - UNC", 0, R.drawable.shoes_light_blue_jordan, 4300000, list.get(0)));
        add(new Produk(4, "Jordan 1 High - Royal Blue", 30, R.drawable.shoes_dark_blue_dark_blue, 8900000, list.get(0)));
        add(new Produk(5, "Yeezy - Frozen Yellow", 30, R.drawable.shoes_neon_yeezy, 7700000, list.get(1)));
        add(new Produk(6, "Balenciaga Triple S - Black Red", 30, R.drawable.shoes_black_red_balenciaga, 9000000, list.get(2)));
    }};

    public static List<Produk> getProdukByKategory(String p_kategori){
        List<Produk> tmpResult = new ArrayList<>();

        if(p_kategori.equals("Semua Produk")){
            tmpResult = produkList;
        }
        else{
            for(int i = 0; i < produkList.size(); i++){
                if(produkList.get(i).getKategori().getNama().equals(p_kategori)){
                    tmpResult.add(produkList.get(i));
                }
            }
        }
        return tmpResult;
    }
}
