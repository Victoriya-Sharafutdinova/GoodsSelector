package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICharacteristicRepository extends JpaRepository<Characteristic, Long> {

}
