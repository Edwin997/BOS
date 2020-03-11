package com.example.bca_bos.models.locations;

import com.example.bca_bos.models.Buyer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    private int id_address;
    private String address; //100
    private Buyer buyer;
    private Kelurahan kelurahan;

    public Address(String p_address, Buyer p_buyer, Kelurahan p_kelurahan){
        this.setAddress(p_address);
        this.setBuyer(p_buyer);
        this.setKelurahan(p_kelurahan);
    }

}
