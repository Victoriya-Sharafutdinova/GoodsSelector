package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductTypeRepository extends JpaRepository<ProductType, Long> {

}
