package com.example.GoodsSelector.entities;


import com.example.GoodsSelector.models.CharacteristicModel;

import javax.persistence.*;

@Entity
@Table(name = "characteristics")
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private Long productId;


    public Characteristic() {}

    public Characteristic(CharacteristicModel characteristicModel) {
        this.id = characteristicModel.getId();
        this.name = characteristicModel.getName();
        this.type = characteristicModel.getType();
        this.value = characteristicModel.getValue();
        this.productId = characteristicModel.getProductId();
    }

    public Characteristic(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
