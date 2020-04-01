package com.example.bca_bos.dummy;

import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.locations.Address;

import java.util.ArrayList;
import java.util.List;

public class ListPembeliDummy {

    public static List<Buyer> pembeliList = new ArrayList<Buyer>(){{
        add(new Buyer(1, "Edwin", "081111111111", null));
        add(new Buyer(2, "Steven", "082222222222", null));
        add(new Buyer(3, "Chris", "083333333333", null));
        add(new Buyer(4, "Steven", "084444444444", null));
        add(new Buyer(5, "Chris", "085555555555", null));
        add(new Buyer(6, "Steven", "086666666666", null));
        add(new Buyer(7, "Chris", "087777777777", null));
    }};
}
