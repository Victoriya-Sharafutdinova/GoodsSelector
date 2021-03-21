package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.Characteristic;

public class CharacteristicModel {
    private Long id;

    private String name;

    private Integer type;

    private String value;

    private Long productId;


    public CharacteristicModel() {}

    public CharacteristicModel(Characteristic characteristic) {
        this.id = characteristic.getId();
        this.name = characteristic.getName();
        this.type = characteristic.getType();
        this.value = characteristic.getValue();
        this.productId = characteristic.getProductId();
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CharacteristicValue getTypedValue() {
        if (type == 0) {
            return new CharacteristicValue<String>(value);
        } else if (type == 1) {
            return new CharacteristicValue<Integer>(Integer.parseInt(value));
        } else if (type == 2) {
            return new CharacteristicValue<Float>(Float.parseFloat(value));
        }
        return new CharacteristicValue<String>(value);
    }

    public void setTypedValue(CharacteristicValue value) {
        this.value = value.getValue().toString();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
