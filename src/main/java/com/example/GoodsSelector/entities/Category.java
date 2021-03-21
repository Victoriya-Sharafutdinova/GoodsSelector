package com.example.GoodsSelector.entities;

import com.example.GoodsSelector.models.CategoryModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private List<ProductType> productTypes;


    public Category() {}

    public Category(CategoryModel categoryModel){
        this.id = categoryModel.getId();
        this.name = categoryModel.getName();
        if (categoryModel.getProductTypes() != null) {
            this.productTypes = new ArrayList<>();
            for (var productType : categoryModel.getProductTypes()) {
                this.productTypes.add(new ProductType(productType));
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

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }
}