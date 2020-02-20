package com.example.bca_bos.models;

public class Transaksi {
    private int id;
    private String order_date;
    private String payment_date;
    private String shipping_date;
    private String shipping_code;
    private String confirmation_date;
    private int shipping_fee;
    private String shipping_agent;
    private String payment_account;
    private int total_payment;
    private Pembeli buyer;
    private int status;

    public Transaksi(){

    }

    public Transaksi(int p_id, String p_order_date, String p_payment_date, String p_shipping_date, String p_confimation_date,
                     String p_shipping_code, int p_shipping_fee, String p_shipping_agent, String p_payment_account,
                     int p_total_payment, Pembeli p_buyer, int p_status){
        this.setId(p_id);
        this.setOrder_date(p_order_date);
        this.setPayment_date(p_payment_date);
        this.setShipping_date(p_shipping_date);
        this.setConfirmation_date(p_confimation_date);
        this.setShipping_code(p_shipping_code);
        this.setShipping_fee(p_shipping_fee);
        this.setShipping_agent(p_shipping_agent);
        this.setPayment_account(p_payment_account);
        this.setTotal_payment(p_total_payment);
        this.setBuyer(p_buyer);
        this.setStatus(p_status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(String shipping_date) {
        this.shipping_date = shipping_date;
    }

    public String getConfirmation_date() {
        return confirmation_date;
    }

    public void setConfirmation_date(String confirmation_date) {
        this.confirmation_date = confirmation_date;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public int getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(int shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getShipping_agent() {
        return shipping_agent;
    }

    public void setShipping_agent(String shipping_agent) {
        this.shipping_agent = shipping_agent;
    }

    public String getPayment_account() {
        return payment_account;
    }

    public void setPayment_account(String payment_account) {
        this.payment_account = payment_account;
    }

    public int getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(int total_payment) {
        this.total_payment = total_payment;
    }

    public Pembeli getBuyer() {
        return buyer;
    }

    public void setBuyer(Pembeli buyer) {
        this.buyer = buyer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
