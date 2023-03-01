package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.DishCollectDAO;
import com.example.orderingsystem.pojo.DishCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishCollectService {
    @Autowired
    DishCollectDAO dishCollectDAO;

    public List<DishCollect> listDishCollectByUserName(String username) {
        return dishCollectDAO.findByUser_Username(username);
    }

    public DishCollect addDishCollect(DishCollect dishCollect) {
        return dishCollectDAO.save(dishCollect);
    }

    public void delCollect(DishCollect dishCollect) {
        dishCollectDAO.delete(dishCollect);
    }

    public DishCollect getDishCollectByUserNameAndDishId(String username, Integer dishId) {
        return dishCollectDAO.findByUser_UsernameAndAndDish_Id(username,dishId);
    }
}
