package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {

}
