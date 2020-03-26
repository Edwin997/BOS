package com.example.bca_bos.dummy;

import com.example.bca_bos.models.TemplatedText;

import java.util.ArrayList;
import java.util.List;

public class ListTemplatedTextDummy {
    public static List<TemplatedText> templatedTextList = new ArrayList<TemplatedText>(){{
        add(new TemplatedText(1, "Halo", "Hello, selamat datang di toko si BAMBANG!"));
        add(new TemplatedText(2, "Terima Kasih", "Terima kasih telah melakukan transaksi dengan toko kami"));
        add(new TemplatedText(3, "Stok Habis", "Maaf, stok barang tersebut telah habis"));
        add(new TemplatedText(4, "Anda Tamfan", "Anda adalah manusia tamfan dan berani"));
    }};
}
