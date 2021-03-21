package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.CharacteristicModel;

import java.util.List;

public interface ICharacteristicService {
    List<CharacteristicModel> getAll();

    CharacteristicModel get(Long id);

    void create(CharacteristicModel characteristicModel);

    boolean update(CharacteristicModel characteristicModel);

    boolean delete(Long id);
}
