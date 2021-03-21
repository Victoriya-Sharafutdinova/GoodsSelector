package com.example.GoodsSelector.entities;

import com.example.GoodsSelector.models.ProductTypeModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_types")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long categoryId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productTypeId")
    private List<Product> products;


    public ProductType() {}

    public ProductType(ProductTypeModel productTypeModel){
        this.id = productTypeModel.getId();
        this.name = productTypeModel.getName();
        this.categoryId = productTypeModel.getCategoryId();
        if (productTypeModel.getProducts() != null) {
            this.products = new ArrayList<>();
            for (var product : productTypeModel.getProducts()) {
                this.products.add(new Product(product));
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
