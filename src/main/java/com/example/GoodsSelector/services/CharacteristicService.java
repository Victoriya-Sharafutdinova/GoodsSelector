package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.Characteristic;
import com.example.GoodsSelector.models.CharacteristicModel;
import com.example.GoodsSelector.repositories.ICharacteristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacteristicService implements ICharacteristicService {
    @Autowired
    private final ICharacteristicRepository characteristicRepository;

    public CharacteristicService(ICharacteristicRepository characteristicRepository){
        this.characteristicRepository = characteristicRepository;
    }

    @Override
    public List<CharacteristicModel> getAll() {
        List<CharacteristicModel> characteristicModels = new ArrayList<>();
        List<Characteristic> characteristics = characteristicRepository.findAll();
        for (var characteristic: characteristics) {
            characteristicModels.add(new CharacteristicModel(characteristic));
        }
        return characteristicModels;
    }

    @Override
    public CharacteristicModel get(Long id) {
        return new CharacteristicModel(characteristicRepository.getOne(id));
    }

    @Override
    public void create(CharacteristicModel characteristicModel) {
        characteristicRepository.save(new Characteristic(characteristicModel));
    }

    @Override
    public boolean update(CharacteristicModel characteristicModel) {
        if (characteristicRepository.getOne(characteristicModel.getId()) != null) {
            characteristicRepository.save(new Characteristic(characteristicModel));
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (characteristicRepository.getOne(id) != null) {
            characteristicRepository.deleteById(id);
            return true;
        }

        return false;
    }
}