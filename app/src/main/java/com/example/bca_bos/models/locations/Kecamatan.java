package com.example.bca_bos.models.locationmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Kecamatan {
    private int id_kecamatan;
    private String kecamatan_name; //40
    private KotaKab kota_kab;

    public Kecamatan(){

    }

    public Kecamatan(String p_name, KotaKab p_kotakab){
        this.setKecamatan_name(p_name);
        this.setKota_kab(p_kotakab);
    }

    public Kecamatan(int p_id, String p_name, KotaKab p_kotakab){
        this.setId_kecamatan(p_id);
        this.setKecamatan_name(p_name);
        this.setKota_kab(p_kotakab);
    }
}
