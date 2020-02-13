package com.example.bca_bos;

import com.example.bca_bos.models.Produk;

import java.util.ArrayList;
import java.util.List;

public class ListProdukDummy {
    public static List<Produk> produkList = new ArrayList<Produk>() {{
        add(new Produk(1, "Ekstrak Manggis", 20, R.drawable.produk_dummy_garcia, 10000));
        add(new Produk(2, "BCA", 10, R.drawable.bca_logo_blue, 1000000));
        add(new Produk(3, "Topping", 0, R.drawable.produk_dummy_garcia, 20000));
    }};
}
