package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.DishDAO;
import com.example.orderingsystem.dao.DishTypeDAO;
import com.example.orderingsystem.pojo.Dish;
import com.example.orderingsystem.pojo.DishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    @Autowired
    DishDAO dishDao;
    @Autowired
    DishTypeDAO dishTypeDAO;

    public List<Dish> listAll(){
        return dishDao.findAll();
    }

    public void addOrUpdate(Dish dish){
        dishDao.save(dish);
    }

    public void deleteById(int id){
        dishDao.deleteById(id);
    }

    public List<Dish> search(String keywords){
        return dishDao.findAllByNameLike("%"+keywords+"%");
    }

    public Page<Dish> listWithPage(PageRequest pageRequest) {
        return dishDao.findAll(pageRequest);
    }

    public List<Dish> list() {
        return dishDao.findAll();
    }

    public Dish save(Dish dish, Integer tid) {
        if(dish.getImgurl()=="") {
            Dish oldDish = dishDao.findById(dish.getId()).get();
            dish.setImgurl(oldDish.getImgurl());
        }
        DishType dishType = dishTypeDAO.findById(tid).get();
        dish.setDishType(dishType);
        dish.setSpecial(false);
        return dishDao.save(dish);
    }

    public int modify(Dish dish) {
        String dishName=dish.getName();
        double dishPrice=dish.getPrice();
        Dish oldDish = dishDao.findByName(dishName);
        String description=dish.getDescription();
        oldDish.setDescription(description);
        oldDish.setName(dishName);
        oldDish.setPrice(dishPrice);
        dishDao.save(oldDish);
        return 1;
    }

    public boolean deleteById(Integer id) {
        boolean result = true;
        try
        {
            dishDao.deleteById(id);
        }
        catch(Exception ex)
        {
            result = false;
        }
        return result;
    }

    public List<Dish> listByDishType(Integer id){
        DishType dishType=dishTypeDAO.findById(id).get();
        return dishDao.findByDishType(dishType);
    }

    public List<Dish> listSpecialDishes() {
        return dishDao.findByIsSpecial(true);
    }

    public Dish getDishById(Integer dishId) {
        return dishDao.findById(dishId).get();
    }
}
