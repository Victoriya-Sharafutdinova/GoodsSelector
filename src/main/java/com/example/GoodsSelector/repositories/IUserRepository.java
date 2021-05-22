package com.example.GoodsSelector.repositories;

import com.example.GoodsSelector.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
