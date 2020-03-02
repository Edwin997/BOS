package com.example.bca_bos.dummy;

import com.example.bca_bos.models.Produk;
import com.example.bca_bos.models.Transaksi;
import com.example.bca_bos.models.TransaksiDetail;

import java.util.ArrayList;
import java.util.List;

public class ListTransaksiDetailDummy {

    private static List<Produk> tmpListP = ListProdukDummy.produkList;
    private static List<Transaksi> tmpListT = ListTransaksiDummy.transaksiList;

    public static List<TransaksiDetail> transaksiDetailList = new ArrayList<TransaksiDetail>() {{
        add(new TransaksiDetail(1, 4, tmpListP.get(0), tmpListT.get(0), 10000));
        add(new TransaksiDetail(2, 1, tmpListP.get(1), tmpListT.get(1), 1000000));
        add(new TransaksiDetail(3, 1, tmpListP.get(1), tmpListT.get(2), 1000000));
        add(new TransaksiDetail(4, 5, tmpListP.get(2), tmpListT.get(2), 20000));
        add(new TransaksiDetail(5, 10, tmpListP.get(0), tmpListT.get(2), 10000));
        add(new TransaksiDetail(6, 4, tmpListP.get(0), tmpListT.get(3), 10000));
        add(new TransaksiDetail(7, 1, tmpListP.get(1), tmpListT.get(5), 1000000));
        add(new TransaksiDetail(8, 1, tmpListP.get(1), tmpListT.get(4), 1000000));
        add(new TransaksiDetail(9, 5, tmpListP.get(2), tmpListT.get(4), 20000));
        add(new TransaksiDetail(10, 10, tmpListP.get(0), tmpListT.get(4), 10000));
        add(new TransaksiDetail(11, 4, tmpListP.get(0), tmpListT.get(6), 10000));
        add(new TransaksiDetail(12, 1, tmpListP.get(1), tmpListT.get(7), 1000000));
        add(new TransaksiDetail(13, 1, tmpListP.get(1), tmpListT.get(8), 1000000));
        add(new TransaksiDetail(14, 5, tmpListP.get(2), tmpListT.get(8), 20000));
        add(new TransaksiDetail(15, 10, tmpListP.get(0), tmpListT.get(8), 10000));
        add(new TransaksiDetail(16, 4, tmpListP.get(0), tmpListT.get(9), 10000));
        add(new TransaksiDetail(17, 1, tmpListP.get(1), tmpListT.get(10), 1000000));
        add(new TransaksiDetail(18, 1, tmpListP.get(1), tmpListT.get(11), 1000000));
    }};

    public static List<TransaksiDetail> getTransaksiDetailList(int id_transaksi){
        List<TransaksiDetail> tmpResult = new ArrayList<>();
        for (int i = 0; i < transaksiDetailList.size(); i++){
            if(transaksiDetailList.get(i).getTransaksi().getId() == id_transaksi){
                tmpResult.add(transaksiDetailList.get(i));
            }
        }
        return tmpResult;
    }
}
