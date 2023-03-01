package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.DishTypeDAO;
import com.example.orderingsystem.pojo.DishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishTypeService {
    @Autowired
    DishTypeDAO dishTypeDAO;

    public List<DishType> list()
    {
        return dishTypeDAO.findAll(Sort.by(Sort.Direction.DESC,"id")) ;

    }

    public DishType get(int id) {
        DishType c= dishTypeDAO.findById(id).orElse(null);
        return c;
    }

    public int addDishType(DishType dishType){
        String name= dishType.getName();
        if(dishTypeDAO.findByName(name)!=null)
            return 2;
        dishTypeDAO.save(dishType);
        return 1;
    }
}
