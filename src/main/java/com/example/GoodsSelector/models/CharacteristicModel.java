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

    public boolean isAsc() {
        return type < 3;
    }

    public int compare (int characteristicType, String characteristicValue) {
        if (type != characteristicType) {
            return 0;
        }
        int type = this.type % 3;
        if (type == 0) {
            return 0;
        } else if (type == 1) {
            var currentValue = Integer.parseInt(value);
            var anotherValue = Integer.parseInt(characteristicValue);
            return Integer.compare(currentValue, anotherValue);
        } else if (type == 2) {
            var currentValue = Float.parseFloat(value);
            var anotherValue = Float.parseFloat(characteristicValue);
            return Float.compare(currentValue, anotherValue);
        }
        return 0;
    }
}
