package com.example.bca_bos;

import com.example.bca_bos.models.TemplatedText;

import java.util.ArrayList;
import java.util.List;

public class ListTemplatedTextDummy {
    public static List<TemplatedText> templatedTextList = new ArrayList<TemplatedText>(){{
        add(new TemplatedText(1, "HELLO", "Hello, selamat datang di toko si BAMBANG!"));
        add(new TemplatedText(2, "TERIMA KASIH", "Terima kasih telah melakukan transaksi dengan toko kami"));
        add(new TemplatedText(3, "STOK HABIS", "Maaf, stok barang tersebut telah habis"));
    }};
}
