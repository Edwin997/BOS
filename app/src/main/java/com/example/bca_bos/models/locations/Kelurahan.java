package com.example.bca_bos.models.locations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kelurahan {
    private int id_kelurahan;
    private String kelurahan_name; //40
    private Kecamatan kecamatan;
}
