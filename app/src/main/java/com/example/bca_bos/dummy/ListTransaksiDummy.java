package com.example.bca_bos.dummy;

import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiDummy {

    private static List<Buyer> tmpList = ListPembeliDummy.buyerList;

    //belom bayar pink = 2
    //belum dikirim kuning = 2
    //belum dikonfirmasi biru = 4
    //sudah selesai hijau = 4

    public static List<Transaction> transactionList = new ArrayList<Transaction>() {{

    }};

//    add(new Transaction(1, "2020-02-11", "2020-02-12", "2020-02-13", "2020-02-14", "AAAAAA", 10000, "JNE", "123456", 40000, tmpList.get(0),3));
//    add(new Transaction(2, "2020-02-15", "2020-02-16", "2020-02-17", "2020-02-18", "BBBBBB", 15000, "TIKI", "234567", 1000000, tmpList.get(1),3));
//    add(new Transaction(3, "2020-02-15", "2020-02-17", "2020-02-18", "", "CCCCCC", 20000, "GOSEND", "345678", 1200000, tmpList.get(2),2));
//    add(new Transaction(4, "2020-02-15", "2020-02-16", "", "", "BBBBBB", 15000, "TIKI", "234567", 1000000, tmpList.get(1),1));
//    add(new Transaction(5, "2020-02-15", "", "", "", "CCCCCC", 20000, "GOSEND", "345678", 1200000, tmpList.get(2),0));
//    add(new Transaction(6, "2020-02-15", "", "", "", "BBBBBB", 15000, "TIKI", "234567", 1000000, tmpList.get(1),0));
//    add(new Transaction(7, "2020-02-15", "2020-02-17", "", "", "CCCCCC", 20000, "GOSEND", "345678", 1200000, tmpList.get(2),1));
//    add(new Transaction(8, "2020-02-15", "2020-02-16", "2020-02-17", "2020-02-18", "BBBBBB", 15000, "TIKI", "234567", 1000000, tmpList.get(1),3));
//    add(new Transaction(9, "2020-02-15", "2020-02-17", "2020-02-18", "", "CCCCCC", 20000, "GOSEND", "345678", 1200000, tmpList.get(2),2));
//    add(new Transaction(10, "2020-02-15", "2020-02-16", "2020-02-17", "2020-02-18", "BBBBBB", 15000, "TIKI", "234567", 1000000, tmpList.get(1),3));
//    add(new Transaction(11, "2020-02-15", "2020-02-17", "2020-02-18", "", "CCCCCC", 20000, "GOSEND", "345678", 1200000, tmpList.get(2),2));
//    add(new Transaction(12, "2020-02-15", "2020-02-17", "2020-02-18", "", "CCCCCC", 20000, "GOSEND", "345678", 1200000, tmpList.get(2),2));
}
