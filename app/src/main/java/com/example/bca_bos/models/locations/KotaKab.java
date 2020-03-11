package com.example.bca_bos.models.locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class KotaKab {
    private int id_kota_kab;
    private String kota_kab_name; //40
    private Provinsi provinsi;

    public KotaKab(String p_nama, Provinsi p_provinsi){
        this.setKota_kab_name(p_nama);
        this.setProvinsi(p_provinsi);
    }

}
