package com.example.GoodsSelector.controllers;

import com.example.GoodsSelector.models.CategoryModel;
import com.example.GoodsSelector.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryModel>> get() {
        final List<CategoryModel> categories = categoryService.getAll();

        return categories != null &&  !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryModel> get(@PathVariable(name = "id") Long id) {
        final CategoryModel categoryModel = categoryService.get(id);

        return categoryModel != null
                ? new ResponseEntity<>(categoryModel, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/categories")
    public ResponseEntity<?> create(@RequestBody CategoryModel categoryModel) {
        categoryService.create(categoryModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/categories/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody CategoryModel categoryModel) {
        categoryModel.setId(id);
        final boolean updated = categoryService.update(categoryModel);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = categoryService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
