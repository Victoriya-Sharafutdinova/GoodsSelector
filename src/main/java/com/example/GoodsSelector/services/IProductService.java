package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.ProductModel;

import java.util.List;

public interface IProductService {
    List<ProductModel> getAll();

    ProductModel get(Long id);

    void create(ProductModel productModel);

    boolean update(ProductModel productModel);

    boolean delete(Long id);
}
