package com.example.GoodsSelector.models;

import java.util.List;

public class ProductTypeModel {
    private String name;

    private List<ProductModel> products;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
