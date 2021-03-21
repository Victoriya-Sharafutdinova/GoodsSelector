package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.CharacteristicsRangeModel;
import com.example.GoodsSelector.models.ProductModel;
import com.example.GoodsSelector.models.ProductTypeModel;

import java.util.List;

public interface IAlgorithmService {
    List<CharacteristicsRangeModel> getCharacteristicsRange(ProductTypeModel productTypeModel);
    List<ProductModel> getGoodsTop(List<CharacteristicsRangeModel> characteristicsRangeModels, ProductTypeModel productTypeModel);
}
