package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.CharacteristicModel;
import com.example.GoodsSelector.models.ProductModel;
import com.example.GoodsSelector.services.CharacteristicService;
import com.example.GoodsSelector.services.ProductService;
import com.example.GoodsSelector.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final CharacteristicService characteristicService;

    @Autowired
    public ProductController(ProductService productService, ProductTypeService productTypeService, CharacteristicService characteristicService) {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.characteristicService = characteristicService;
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

    @GetMapping(value = "/productsTest/{number}")
    public ResponseEntity<?> testCreate(@PathVariable(name = "number") Long number) {
        long m = System.currentTimeMillis();

        var productTypes = productTypeService.getAll();
        for (int i = 0; i < productTypes.size(); i++) {
            for (int j = 0; j < number / productTypes.size(); j++) {
                var productModel = new ProductModel();
                productModel.setName("test_" + (i * j));
                productModel.setProductTypeId(productTypes.get(i).getId());
                productService.create(productModel);
                for (int k = 0; k < 10; k++) {
                    var characteristicModel = new CharacteristicModel();
                    characteristicModel.setName("test_" + k);
                    characteristicModel.setType(k % 6);
                    characteristicModel.setValue("" + k);
                    var products = productService.getAll();
                    characteristicModel.setProductId(products.get(products.size() - 1).getId());
                    characteristicService.create(characteristicModel);
                }
            }
        }

        System.out.println("Время выполнения " + number + " запросов : " + (double) (System.currentTimeMillis() - m) + "мс");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
