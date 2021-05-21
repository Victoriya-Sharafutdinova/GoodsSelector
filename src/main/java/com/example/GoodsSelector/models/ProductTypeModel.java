package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductTypeModel {
    private Long id;

    private String name;

    private Long categoryId;

    private List<ProductModel> products;


    public ProductTypeModel() {}

    public ProductTypeModel(ProductType productType) {
        this.id = productType.getId();
        this.name = productType.getName();
        this.categoryId = productType.getCategoryId();
        if (productType.getProducts() != null) {
            this.products = new ArrayList<>();
            for (var product : productType.getProducts()) {
                this.products.add(new ProductModel(product));
            }
        }
    }

    public ProductTypeModel(ProductType productType, boolean notLoadProductsParam) {
        this.id = productType.getId();
        this.name = productType.getName();
        this.categoryId = productType.getCategoryId();
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
