package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private Long id;

    private String name;

    private List<ProductTypeModel> productTypes;


    public CategoryModel() {}

    public CategoryModel(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if (category.getProductTypes() != null) {
            this.productTypes = new ArrayList<>();
            for (var productType : category.getProductTypes()) {
                this.productTypes.add(new ProductTypeModel(productType));
            }
        }
    }

    public CategoryModel(Category category, boolean notLoadProductsParam) {
        this.id = category.getId();
        this.name = category.getName();
        if (category.getProductTypes() != null) {
            this.productTypes = new ArrayList<>();
            for (var productType : category.getProductTypes()) {
                this.productTypes.add(new ProductTypeModel(productType, notLoadProductsParam));
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductTypeModel> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductTypeModel> productTypes) {
        this.productTypes = productTypes;
    }
}
