package com.example.GoodsSelector.models;

import java.util.List;

public class ProductModel {
    private String name;

    private List<CharacteristicModel> characteristics;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
