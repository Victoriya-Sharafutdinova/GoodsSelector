package com.example.GoodsSelector.services;

import com.example.GoodsSelector.models.CategoryModel;

import java.util.List;

public interface ICategoryService {
    List<CategoryModel> getAll();

    CategoryModel get(Long id);

    /**
     * Создает новую категорию
     * @param categoryModel - категория для создания
     */
    void create(CategoryModel categoryModel);
    /**
     * Обновляет категории с заданным ID,
     * в соответствии с переданной категорией
     * @param categoryModel - категория в соответсвии с которой нужно обновить данные
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(CategoryModel categoryModel);

    /**
     * Удаляет категорию с заданным ID
     * @param id - id категории, которую нужно удалить
     * @return - true если категория была удалена, иначе false
     */
    boolean delete(Long id);
}
