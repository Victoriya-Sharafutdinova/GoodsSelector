package com.example.GoodsSelector.entities;

import com.example.GoodsSelector.models.ProductModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long productTypeId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<Characteristic> characteristics;


    public Product() {}

    public Product(ProductModel productModel) {
        this.id = productModel.getId();
        this.name = productModel.getName();
        this.productTypeId = productModel.getProductTypeId();
        if (productModel.getCharacteristics() != null) {
            this.characteristics = new ArrayList<>();
            for (var characteristic : productModel.getCharacteristics()) {
                this.characteristics.add(new Characteristic(characteristic));
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
