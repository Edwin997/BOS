package com.example.bca_bos.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Courier implements Serializable {
    private int id_selected_courier;
    private int id_seller;
    private int id_courier;
    private int is_selected;
}
