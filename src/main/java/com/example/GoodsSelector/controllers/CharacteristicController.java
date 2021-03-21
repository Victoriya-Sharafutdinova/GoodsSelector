package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.CharacteristicModel;
import com.example.GoodsSelector.services.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CharacteristicController {
    private final CharacteristicService characteristicService;

    @Autowired
    public CharacteristicController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping(value = "/characteristics")
    public ResponseEntity<List<CharacteristicModel>> get() {
        final List<CharacteristicModel> characteristics = characteristicService.getAll();

        return characteristics != null &&  !characteristics.isEmpty()
                ? new ResponseEntity<>(characteristics, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/characteristics/{id}")
    public ResponseEntity<CharacteristicModel> get(@PathVariable(name = "id") Long id) {
        final CharacteristicModel characteristicModel = characteristicService.get(id);

        return characteristicModel != null
                ? new ResponseEntity<>(characteristicModel, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/characteristics")
    public ResponseEntity<?> create(@RequestBody CharacteristicModel characteristicModel) {
        characteristicService.create(characteristicModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/characteristics/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody CharacteristicModel characteristicModel) {
        characteristicModel.setId(id);
        final boolean updated = characteristicService.update(characteristicModel);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/characteristics/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = characteristicService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
