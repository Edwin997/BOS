package com.example.bca_bos.models;

import com.example.bca_bos.models.locations.Address;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Buyer {
    private int id_buyer;
    private String buyer_name; //40
    private String phone; //20
    private List<Address> listOfAddress;

    private int sum_trx;
    private int nominalTransaksi;

    public Buyer(int p_id, String p_name, String p_mobile_num, List<Address> p_alamat){
        this.setId_buyer(p_id);
        this.setBuyer_name(buyer_name);
        this.setPhone(p_mobile_num);
        this.setListOfAddress(p_alamat);
    }

    public void addAddress(Address p_address){
        this.listOfAddress.add(p_address);
    }

    public int getAddressCount(){
        return this.listOfAddress.size();
    }
}
