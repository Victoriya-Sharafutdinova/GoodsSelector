package com.example.GoodsSelector.services;

import com.example.GoodsSelector.entities.Category;
import com.example.GoodsSelector.models.CategoryModel;
import com.example.GoodsSelector.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryModel> getAll() {
        List<CategoryModel> categoryModels = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category: categories) {
            categoryModels.add(new CategoryModel(category, true));
        }
        return categoryModels;
    }

    @Override
    public CategoryModel get(Long id) {
        return new CategoryModel(categoryRepository.getOne(id));
    }

    @Override
    public void create(CategoryModel categoryModel) {
        categoryRepository.save(new Category(categoryModel));
    }

    @Override
    public boolean update(CategoryModel categoryModel) {
        if (categoryRepository.getOne(categoryModel.getId()) != null) {
            categoryRepository.save(new Category(categoryModel));
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (categoryRepository.getOne(id) != null) {
            categoryRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
