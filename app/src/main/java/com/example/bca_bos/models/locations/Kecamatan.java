package com.example.bca_bos.models.locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kecamatan {
    private int id_kecamatan;
    private String kecamatan_name; //40
    private KotaKab kota_kab;

    public Kecamatan(String p_name, KotaKab p_kotakab){
        this.setKecamatan_name(p_name);
        this.setKota_kab(p_kotakab);
    }
}
