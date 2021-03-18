package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.Category;

import java.util.List;

public class CategoryModel {
    private String name;

    private List<ProductTypeModel> productTypes;

    public CategoryModel(){}

    public CategoryModel(Category category) {
        this.name = category.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
