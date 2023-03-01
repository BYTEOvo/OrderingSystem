package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.DishCollect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishCollectDAO extends JpaRepository<DishCollect,Integer> {
    public List<DishCollect> findByUser_Id(Integer id);
    public List<DishCollect> findByUser_Username(String username);
    public DishCollect findByUser_UsernameAndAndDish_Id(String username,Integer dishId);
}
