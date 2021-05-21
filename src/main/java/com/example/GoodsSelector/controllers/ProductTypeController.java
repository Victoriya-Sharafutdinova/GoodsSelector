package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.ProductTypeModel;
import com.example.GoodsSelector.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductTypeController {
    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping(value = "/productTypes")
    public ResponseEntity<List<ProductTypeModel>> get() {
        final List<ProductTypeModel> productTypes = productTypeService.getAll();

        return productTypes != null &&  !productTypes.isEmpty()
                ? new ResponseEntity<>(productTypes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/productTypes/{id}")
    public ResponseEntity<ProductTypeModel> get(@PathVariable(name = "id") Long id) {
        final ProductTypeModel productTypeModel = productTypeService.get(id);

        return productTypeModel != null
                ? new ResponseEntity<>(productTypeModel, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/productTypes")
    public ResponseEntity<?> create(@RequestBody ProductTypeModel productTypeModel) {
        productTypeService.create(productTypeModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/productTypes/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody ProductTypeModel productTypeModel) {
        productTypeModel.setId(id);
        final boolean updated = productTypeService.update(productTypeModel);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/productTypes/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = productTypeService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
