package com.example.bca_bos;

import com.example.bca_bos.models.Pembeli;

import java.util.ArrayList;
import java.util.List;

public class ListPembeliDummy {
    public static List<Pembeli> pembeliList = new ArrayList<Pembeli>() {{
        add(new Pembeli(1, "Edwin", 1234567890, "aa"));
        add(new Pembeli(2, "Steven", 1234567891, "bb"));
        add(new Pembeli(3, "Putra", 1234567892, "cc"));
    }};
}
