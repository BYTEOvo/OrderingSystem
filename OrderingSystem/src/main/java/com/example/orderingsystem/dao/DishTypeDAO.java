package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.DishType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishTypeDAO extends JpaRepository<DishType,Integer> {
    DishType findByName(String name);
}
