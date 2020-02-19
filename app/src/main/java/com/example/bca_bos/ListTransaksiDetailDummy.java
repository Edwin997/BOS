package com.example.bca_bos;

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
    }};
}
