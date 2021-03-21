package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.ProductTypeModel;

import java.util.List;

public interface IProductTypeService {
    List<ProductTypeModel> getAll();

    ProductTypeModel get(Long id);

    void create(ProductTypeModel productTypeModel);

    boolean update(ProductTypeModel productTypeModel);

    boolean delete(Long id);
}
