package com.example.bca_bos.dummy;

import com.example.bca_bos.R;
import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProdukDummy {

    private static List<PrdCategory> list = ListKategoriDummy.kategoriList;

    public static List<Product> productList = new ArrayList<Product>(){{
        add(new Product(1, "Youtube", 10, "image_path", 10000, list.get(0)));
        add(new Product(2, "Google", 1, "image_path", 20000, list.get(1)));
        add(new Product(3, "Facebook", 20, "image_path", 30000, list.get(1)));
    }};

    public static List<Product> getProduks(){
        return productList;
    }

    public static List<Product> getProdukByKategory(String p_kategori){
        List<Product> tmpResult = new ArrayList<>();

        if(p_kategori.equals("Semua Product")){
            tmpResult = productList;
        }
        else{
            for(int i = 0; i < productList.size(); i++){
                if(productList.get(i).getPrdCategory().getCategory_name().equals(p_kategori)){
                    tmpResult.add(productList.get(i));
                }
            }
        }
        return tmpResult;
    }
}
