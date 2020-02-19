package com.example.bca_bos;

import com.example.bca_bos.models.Kategori;

import java.util.ArrayList;
import java.util.List;

public class ListKategoriDummy {
    public static List<Kategori> kategoriList = new ArrayList<Kategori>() {{
        add(new Kategori(1, "Makanan"));
        add(new Kategori(2, "Minuman"));
        add(new Kategori(3, "Elektronik"));
    }};
}
