package com.example.GoodsSelector.models;

public class CharacteristicValue<T> {
    private T value;

    CharacteristicValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
