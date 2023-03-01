package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.Dish;
import com.example.orderingsystem.pojo.DishType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishDAO extends JpaRepository<Dish,Integer> {
    List<Dish> findAllByNameLike(String keywords);
    List<Dish> findAll();
    Dish findByName(String dishName);
    Page<Dish> findAll(Pageable pageable);
    Dish findById(int id);
    List<Dish> findByDishType(DishType dishType);

    List<Dish> findByIsSpecial(boolean isSpecial);
}
