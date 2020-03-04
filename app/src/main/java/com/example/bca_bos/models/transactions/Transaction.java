package com.example.bca_bos.models.transactions;

import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.locations.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int id_transaction;
    private Buyer buyer;
    private Seller seller;
    private String payment_account; //20
    private int total_payment;
    private String order_time; //date
    private String payment_time; //date
    private String shipping_time; //date
    private String shipping_code; //20
    private int shipping_fee;
    private Address shipping_address;
    private String shipping_agent; //20
    private String confirmation_time; //date
    private int status;

    public Transaction(Buyer p_buyer, Seller p_seller, String p_payment_account, int p_total_payment,
                       String p_order_time, String p_payment_time, String p_shipping_time, String p_shipping_code,
                       int p_shipping_fee, Address p_shipping_address, String p_shipping_agent,
                       String p_confimation_time, int p_status){
        this.setBuyer(p_buyer);
        this.setSeller(p_seller);
        this.setPayment_account(p_payment_account);
        this.setTotal_payment(p_total_payment);
        this.setOrder_time(p_order_time);
        this.setPayment_time(p_payment_time);
        this.setShipping_time(p_shipping_time);
        this.setShipping_code(p_shipping_code);
        this.setShipping_fee(p_shipping_fee);
        this.setShipping_address(p_shipping_address);
        this.setShipping_agent(p_shipping_agent);
        this.setConfirmation_time(p_confimation_time);
        this.setStatus(p_status);
    }
}
