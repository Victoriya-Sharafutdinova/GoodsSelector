package com.example.GoodsSelector.models;

import java.util.List;

public class GoodsTopInfo {
    private List<CharacteristicsRangeModel> characteristicsRangeModels;
    private ProductTypeModel productTypeModel;

    public List<CharacteristicsRangeModel> getCharacteristicsRangeModels() {
        return characteristicsRangeModels;
    }

    public void setCharacteristicsRangeModels(List<CharacteristicsRangeModel> characteristicsRangeModels) {
        this.characteristicsRangeModels = characteristicsRangeModels;
    }

    public ProductTypeModel getProductTypeModel() {
        return productTypeModel;
    }

    public void setProductTypeModel(ProductTypeModel productTypeModel) {
        this.productTypeModel = productTypeModel;
    }
}
