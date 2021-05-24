package com.example.GoodsSelector.models;

import com.example.GoodsSelector.entities.HistoryProduct;

import java.util.ArrayList;
import java.util.List;

public class HistoryProductModel {
    private String name;

    private List<CharacteristicModel> characteristics;


    public HistoryProductModel() {}

    public HistoryProductModel(HistoryProduct historyProduct) {
        this.name = historyProduct.getProduct().getName();
        var characteristicHistoryProducts = historyProduct.getCharacteristicHistoryProducts();
        if (characteristicHistoryProducts != null) {
            this.characteristics = new ArrayList<>();
            for (int i = 0; i < characteristicHistoryProducts.size(); i++) {
                for (var characteristic : characteristicHistoryProducts) {
                    if (characteristic.getOrderIndex() == i) {
                        this.characteristics.add(new CharacteristicModel(characteristic.getCharacteristic()));
                        break;
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CharacteristicModel> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<CharacteristicModel> characteristics) {
        this.characteristics = characteristics;
    }
}
