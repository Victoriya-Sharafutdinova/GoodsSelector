package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.CharacteristicsRangeModel;
import com.example.GoodsSelector.models.GoodsTopInfo;
import com.example.GoodsSelector.models.ProductModel;
import com.example.GoodsSelector.models.ProductTypeModel;
import com.example.GoodsSelector.services.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AlgorithmController {
    private final AlgorithmService algorithmService;

    @Autowired
    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @PostMapping(value = "/characteristicsRange")
    public ResponseEntity<List<CharacteristicsRangeModel>> getCharacteristicsRange(@RequestBody ProductTypeModel productTypeModel) {
        return new ResponseEntity<>(algorithmService.getCharacteristicsRange(productTypeModel), HttpStatus.OK);
    }

    @PostMapping(value = "/goodsTop")
    public ResponseEntity<List<ProductModel>> getGoodsTop(@RequestBody GoodsTopInfo goodsTopInfo) {
        return new ResponseEntity<>(algorithmService.getGoodsTop(goodsTopInfo.getCharacteristicsRangeModels(), goodsTopInfo.getProductTypeModel()), HttpStatus.OK);
    }
}
