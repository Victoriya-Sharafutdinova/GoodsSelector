package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.ProductModel;
import com.example.GoodsSelector.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductModel>> get() {
        final List<ProductModel> products = productService.getAll();

        return products != null &&  !products.isEmpty()
                ? new ResponseEntity<>(products, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductModel> get(@PathVariable(name = "id") Long id) {
        final ProductModel productModel = productService.get(id);

        return productModel != null
                ? new ResponseEntity<>(productModel, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<?> create(@RequestBody ProductModel productModel) {
        productService.create(productModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody ProductModel productModel) {
        productModel.setId(id);
        final boolean updated = productService.update(productModel);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = productService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
