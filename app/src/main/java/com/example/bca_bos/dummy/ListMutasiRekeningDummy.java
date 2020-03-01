package com.example.bca_bos.dummy;

import com.example.bca_bos.models.MutasiRekening;

import java.util.ArrayList;
import java.util.List;

public class ListMutasiRekeningDummy {
    public static List<MutasiRekening> mutasiRekeningList = new ArrayList<MutasiRekening>(){{
        add(new MutasiRekening(1, "1/1/2020", "123123123123", "Budi Setia", "542000", "Pending"));
        add(new MutasiRekening(2, "1/1/2020", "123123123124", "Ani Aya", "321000", "Done"));
        add(new MutasiRekening(3, "1/1/2020", "123123123125", "Bambang Moe", "989000", "Done"));
        add(new MutasiRekening(4, "1/1/2020", "123123123126", "Raufi Mus", "231200", "Done"));
        add(new MutasiRekening(5, "1/1/2020", "123123123127", "Axell Geral", "765230", "Done"));
        add(new MutasiRekening(6, "1/1/2020", "123123123128", "Farras Muha", "150500", "Done"));
        add(new MutasiRekening(7, "1/1/2020", "123123123129", "Damas Prako", "99000", "Done"));

    }};

}
