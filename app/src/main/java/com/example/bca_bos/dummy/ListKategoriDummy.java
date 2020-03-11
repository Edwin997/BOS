package com.example.bca_bos.dummy;

import com.example.bca_bos.models.products.PrdCategory;

import java.util.ArrayList;
import java.util.List;

public class ListKategoriDummy {
    public static List<PrdCategory> kategoriList = new ArrayList<PrdCategory>();

    public static String[] getListTypeString(){
        String[] tmpResult = new String[kategoriList.size() + 1];
        tmpResult[0] = "Semua Product";
        for(int i = 1; i <= kategoriList.size(); i++){
            tmpResult[i] = (kategoriList.get(i-1).getCategory_name());
        }
        return tmpResult;
    }
}
