package com.example.bca_bos;

import com.example.bca_bos.models.Produk;

import java.util.ArrayList;
import java.util.List;

public class ListProdukDummy {
    public static List<Produk> produkList = new ArrayList<Produk>() {{
        add(new Produk(1, "Ekstrak Manggis", 1, R.drawable.produk_dummy_garcia, 10000));
    }};
}
