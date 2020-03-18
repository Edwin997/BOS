package com.example.bca_bos.models.transactions;

import com.example.bca_bos.models.products.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetail {
    private int id_transaction_detail;
    private Product product;
    private int quantity;
    private String sell_price;

    public TransactionDetail(Product p_product, int p_quantity, String p_sell_price){
        this.setProduct(p_product);
        this.setQuantity(p_quantity);
        this.setSell_price(p_sell_price);
    }
}
