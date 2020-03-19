package com.example.bca_bos.models;

import com.example.bca_bos.models.locations.KotaKab;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Seller implements Serializable {
    private int id_seller;
    private String username;// 20
    private String password_user; //100 //8-12 must contains alphabet and numeric
    private String name; //40
    private String card_number; //40
    private String phone; //20
    private String shop_name; //40
    private int flag;
    private String last_update_time; //date
    private String base64StringImage; //60
    private KotaKab kota_kab;
    private List<Courier> selected_courier;

}
