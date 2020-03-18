package com.example.bca_bos.dummy;

import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.models.transactions.Transaction;
import com.example.bca_bos.models.transactions.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiDetailDummy {

    private static List<Product> tmpListP = ListProdukDummy.productList;
    private static List<Transaction> tmpListT = ListTransaksiDummy.transactionList;

    public static List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>();

//    public static List<TransactionDetail> getTransaksiDetailList(int id_transaksi){
//        List<TransactionDetail> tmpResult = new ArrayList<>();
//        for (int i = 0; i < transactionDetailList.size(); i++){
//            if(transactionDetailList.get(i).getTransaction().getId_transaction() == id_transaksi){
//                tmpResult.add(transactionDetailList.get(i));
//            }
//        }
//        return tmpResult;
//    }
}
