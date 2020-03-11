package com.example.bca_bos.models.locations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Provinsi {
    private int id_provinsi;
    private String provinsi_name; //40

    public Provinsi(){

    }

    public Provinsi(int p_id, String p_nama){
        this.setId_provinsi(p_id);
        this.setProvinsi_name(p_nama);
    }
}