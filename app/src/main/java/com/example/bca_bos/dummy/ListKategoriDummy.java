package com.example.bca_bos.dummy;

import com.example.bca_bos.models.Kategori;

import java.util.ArrayList;
import java.util.List;

public class ListKategoriDummy {
    public static List<Kategori> kategoriList = new ArrayList<Kategori>() {{
        add(new Kategori(1, "Nike"));
        add(new Kategori(2, "Adidas"));
        add(new Kategori(3, "Balenciaga"));
    }};

    public static String[] getListTypeString(){
        String[] tmpResult = new String[kategoriList.size() + 1];
        tmpResult[0] = "Semua Produk";
        for(int i = 1; i <= kategoriList.size(); i++){
            tmpResult[i] = (kategoriList.get(i-1).getNama());
        }
        return tmpResult;
    }
}
