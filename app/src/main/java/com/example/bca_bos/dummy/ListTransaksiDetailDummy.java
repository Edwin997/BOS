package com.example.bca_bos.dummy;

import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.models.transactions.Transaction;
import com.example.bca_bos.models.transactions.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiDetailDummy {

    private static List<Product> tmpListP = ListProdukDummy.productList;
    private static List<Transaction> tmpListT = ListTransaksiDummy.transactionList;

    public static List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>() {{

    }};
//
//    add(new TransactionDetail(1, 4, tmpListP.get(0), tmpListT.get(0), 10000));
//    add(new TransactionDetail(2, 1, tmpListP.get(1), tmpListT.get(1), 1000000));
//    add(new TransactionDetail(3, 1, tmpListP.get(1), tmpListT.get(2), 1000000));
//    add(new TransactionDetail(4, 5, tmpListP.get(2), tmpListT.get(2), 20000));
//    add(new TransactionDetail(5, 10, tmpListP.get(0), tmpListT.get(2), 10000));
//    add(new TransactionDetail(6, 4, tmpListP.get(0), tmpListT.get(3), 10000));
//    add(new TransactionDetail(7, 1, tmpListP.get(1), tmpListT.get(5), 1000000));
//    add(new TransactionDetail(8, 1, tmpListP.get(1), tmpListT.get(4), 1000000));
//    add(new TransactionDetail(9, 5, tmpListP.get(2), tmpListT.get(4), 20000));
//    add(new TransactionDetail(10, 10, tmpListP.get(0), tmpListT.get(4), 10000));
//    add(new TransactionDetail(11, 4, tmpListP.get(0), tmpListT.get(6), 10000));
//    add(new TransactionDetail(12, 1, tmpListP.get(1), tmpListT.get(7), 1000000));
//    add(new TransactionDetail(13, 1, tmpListP.get(1), tmpListT.get(8), 1000000));
//    add(new TransactionDetail(14, 5, tmpListP.get(2), tmpListT.get(8), 20000));
//    add(new TransactionDetail(15, 10, tmpListP.get(0), tmpListT.get(8), 10000));
//    add(new TransactionDetail(16, 4, tmpListP.get(0), tmpListT.get(9), 10000));
//    add(new TransactionDetail(17, 1, tmpListP.get(1), tmpListT.get(10), 1000000));
//    add(new TransactionDetail(18, 1, tmpListP.get(1), tmpListT.get(11), 1000000));

    public static List<TransactionDetail> getTransaksiDetailList(int id_transaksi){
        List<TransactionDetail> tmpResult = new ArrayList<>();
        for (int i = 0; i < transactionDetailList.size(); i++){
            if(transactionDetailList.get(i).getTransaction().getId_transaction() == id_transaksi){
                tmpResult.add(transactionDetailList.get(i));
            }
        }
        return tmpResult;
    }
}
