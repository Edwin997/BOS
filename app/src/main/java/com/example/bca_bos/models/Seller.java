package com.example.bca_bos.models;

import com.example.bca_bos.models.locations.KotaKab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Seller {
    private int id_seller;
    private String username;// 20
    private String password_user; //100 //8-12 must contains alphabet and numeric
    private String seller_name; //40
    private String phone; //20
    private String shop_name; //40
    private int flag;
    private String last_update_time; //date
    private String image_path; //60
    private KotaKab kotakab;

}