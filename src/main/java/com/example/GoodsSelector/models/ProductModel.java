package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    private Long id;

    private String name;

    private Long productTypeId;

    private List<CharacteristicModel> characteristics;


    public ProductModel() {}

    public ProductModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.productTypeId = product.getProductTypeId();
        if (product.getCharacteristics() != null) {
            this.characteristics = new ArrayList<>();
            for (var characteristic : product.getCharacteristics()) {
                this.characteristics.add(new CharacteristicModel(characteristic));
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

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public List<CharacteristicModel> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<CharacteristicModel> characteristics) {
        this.characteristics = characteristics;
    }
}
