package com.example.bca_bos.dummy;

import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProdukDummy {

    private static List<PrdCategory> list = ListKategoriDummy.prdCategoryList;

    public static List<Product> productList = new ArrayList<Product>() {{

    }};

//    add(new Product(1, "Jordan 1 High - Bred Toe", 20, R.drawable.shoes_red_jordan, 5500000, list.get(0)));
//    add(new Product(2, "Balenciaga Triple S - Triple Black", 10, R.drawable.shoes_black_balenciaga, 13000000, list.get(2)));
//    add(new Product(3, "Jordan 1 High - UNC", 0, R.drawable.shoes_light_blue_jordan, 4300000, list.get(0)));
//    add(new Product(4, "Jordan 1 High - Royal Blue", 30, R.drawable.shoes_dark_blue_dark_blue, 8900000, list.get(0)));
//    add(new Product(5, "Yeezy - Frozen Yellow", 30, R.drawable.shoes_neon_yeezy, 7700000, list.get(1)));
//    add(new Product(6, "Balenciaga Triple S - Black Red", 30, R.drawable.shoes_black_red_balenciaga, 9000000, list.get(2)));

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
