package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
