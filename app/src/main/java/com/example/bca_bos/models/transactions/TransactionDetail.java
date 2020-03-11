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
    private int id_transaction_dtl;
    private Transaction transaction;
    private Product product;
    private int quantity;
    private int sell_price;

    public TransactionDetail(Transaction p_transaction, Product p_product, int p_quantity, int p_sell_price){
        this.setTransaction(p_transaction);
        this.setProduct(p_product);
        this.setQuantity(p_quantity);
        this.setSell_price(p_sell_price);
    }
}
