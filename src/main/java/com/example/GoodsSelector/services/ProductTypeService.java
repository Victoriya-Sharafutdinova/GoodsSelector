package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.ProductType;
import com.example.GoodsSelector.models.ProductTypeModel;
import com.example.GoodsSelector.repositories.IProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeService implements IProductTypeService {
    @Autowired
    private final IProductTypeRepository productTypeRepository;

    public ProductTypeService(IProductTypeRepository productTypeRepository){
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public List<ProductTypeModel> getAll() {
        List<ProductTypeModel> productTypeModels = new ArrayList<>();
        List<ProductType> productTypes = productTypeRepository.findAll();
        for (var productType: productTypes) {
            productTypeModels.add(new ProductTypeModel(productType));
        }
        return productTypeModels;
    }

    @Override
    public ProductTypeModel get(Long id) {
        return new ProductTypeModel(productTypeRepository.getOne(id));
    }

    @Override
    public void create(ProductTypeModel productTypeModel) {
        productTypeRepository.save(new ProductType(productTypeModel));
    }

    @Override
    public boolean update(ProductTypeModel productTypeModel) {
        if (productTypeRepository.getOne(productTypeModel.getId()) != null) {
            productTypeRepository.save(new ProductType(productTypeModel));
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (productTypeRepository.getOne(id) != null) {
            productTypeRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
