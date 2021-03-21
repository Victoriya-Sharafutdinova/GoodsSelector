package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.Product;
import com.example.GoodsSelector.models.ProductModel;
import com.example.GoodsSelector.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductModel> getAll() {
        List<ProductModel> productModels = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (var product: products) {
            productModels.add(new ProductModel(product));
        }
        return productModels;
    }

    @Override
    public ProductModel get(Long id) {
        return new ProductModel(productRepository.getOne(id));
    }

    @Override
    public void create(ProductModel productModel) {
        productRepository.save(new Product(productModel));
    }

    @Override
    public boolean update(ProductModel productModel) {
        if (productRepository.getOne(productModel.getId()) != null) {
            productRepository.save(new Product(productModel));
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (productRepository.getOne(id) != null) {
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
