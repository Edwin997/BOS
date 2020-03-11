package com.example.bca_bos.models.locations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KotaKab {
    private int id_kota_kab;
    private String kota_kab_name; //40
    private Provinsi provinsi;

    public KotaKab(){}

    public KotaKab(String p_nama, Provinsi p_provinsi){
        this.setKota_kab_name(p_nama);
        this.setProvinsi(p_provinsi);
    }

    public KotaKab(int p_id, String p_nama, Provinsi p_provinsi){
        this.setId_kota_kab(p_id);
        this.setKota_kab_name(p_nama);
        this.setProvinsi(p_provinsi);
    }

}