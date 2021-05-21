package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.Characteristic;

import java.util.ArrayList;
import java.util.List;

public class CharacteristicsRangeModel {
    private String name;

    private Integer type;

    private List<String> values;

    public CharacteristicsRangeModel() {

    }

    public CharacteristicsRangeModel(Characteristic characteristic) {
        this.name = characteristic.getName();
        this.type = characteristic.getType();
        this.values = new ArrayList<>();
        this.values.add(characteristic.getValue());
        if (type != 0) {
            this.values.add(characteristic.getValue());
        }
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

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public boolean isAsc() {
        return type < 3;
    }
}
