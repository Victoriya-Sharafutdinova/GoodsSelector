package com.example.GoodsSelector.entities;

import com.example.GoodsSelector.models.CategoryModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<ProductType> productTypes;


    public Category(){}

    public Category(Long id, CategoryModel categoryModel){
        this.id = id;
        this.name = categoryModel.getName();
    }

    public Category(CategoryModel categoryModel){
        this.name = categoryModel.getName();
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
}