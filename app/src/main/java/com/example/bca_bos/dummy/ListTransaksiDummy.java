package com.example.bca_bos.dummy;

import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiDummy {

    private static List<Buyer> tmpList = ListPembeliDummy.pembeliList;

    //belom bayar pink = 2
    //belum dikirim kuning = 2
    //belum dikonfirmasi biru = 4
    //sudah selesai hijau = 4

    public static List<Transaction> transactionList = new ArrayList<Transaction>();
}
