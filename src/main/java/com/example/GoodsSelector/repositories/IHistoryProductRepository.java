package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.HistoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryProductRepository extends JpaRepository<HistoryProduct, Long> {
}
