package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.CharacteristicsRangeModel;
import com.example.GoodsSelector.models.ProductModel;
import com.example.GoodsSelector.models.ProductTypeModel;
import com.example.GoodsSelector.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlgorithmService implements IAlgorithmService {
    @Autowired
    private final IProductRepository productRepository;

    public AlgorithmService(IProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<CharacteristicsRangeModel> getCharacteristicsRange(ProductTypeModel productTypeModel) {
        return null;
    }

    @Override
    public List<ProductModel> getGoodsTop(List<CharacteristicsRangeModel> characteristicsRangeModels, ProductTypeModel productTypeModel) {
        return null;
    }
}
