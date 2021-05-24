package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryRepository extends JpaRepository<History, Long> {
}
