package com.example.bca_bos.dummy;

import com.example.bca_bos.models.products.PrdCategory;

import java.util.ArrayList;
import java.util.List;

public class ListKategoriDummy {
    public static List<PrdCategory> prdCategoryList = new ArrayList<PrdCategory>() {{
        add(new PrdCategory(1, "Nike"));
        add(new PrdCategory(2, "Adidas"));
        add(new PrdCategory(3, "Balenciaga"));
    }};

    public static String[] getListTypeString(){
        String[] tmpResult = new String[prdCategoryList.size() + 1];
        tmpResult[0] = "Semua Product";
        for(int i = 1; i <= prdCategoryList.size(); i++){
            tmpResult[i] = (prdCategoryList.get(i-1).getCategory_name() );
        }
        return tmpResult;
    }
}
