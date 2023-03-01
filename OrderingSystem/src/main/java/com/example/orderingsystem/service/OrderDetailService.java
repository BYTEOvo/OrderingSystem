package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.DishDAO;
import com.example.orderingsystem.dao.OrderDAO;
import com.example.orderingsystem.dao.OrderDetailDAO;
import com.example.orderingsystem.pojo.Dish;
import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.pojo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    OrderDAO orderDao;
    @Autowired
    DishDAO dishDAO;

    public void addItem(OrderDetail orderDetail, Order order) {
        if(orderDetail.getId()==null) {
            order.setPrice(order.getPrice() + orderDetail.getDish().getPrice() * orderDetail.getCount());
            orderDao.save(order);
            orderDetail.setOrder(order);
        }
        else{
            List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
            for(OrderDetail orderDetail1:orderDetailList){
                if(orderDetail.getId()!=orderDetail1.getId())
                    continue;
                else {
                    if (orderDetail.getCount() == orderDetail1.getCount())
                        return;
                    else {
                        order.setPrice(order.getPrice() + (orderDetail.getCount() - orderDetail1.getCount()) * orderDetail.getDish().getPrice());
                        orderDao.save(order);
                    }
                }
            }
        }
            orderDetail.setState("制作中");
        orderDetailDAO.save(orderDetail);
    }

    public List<OrderDetail> findByOid(Integer id) {
        Order order=orderDao.findById(id).get();
        return orderDetailDAO.findAllByOrder(order);
    }

    public List<OrderDetail> findByOidAndUserName(Integer id, String username) {
        return orderDetailDAO.findByOrder_IdAndOrder_User_Username(id,username);
    }

    public OrderDetail readyById(Integer id) {
        OrderDetail orderDetail = orderDetailDAO.findById(id).get();
        orderDetail.setState("制作完成");
        return orderDetailDAO.save(orderDetail);
    }

    public List<OrderDetail> findByState(String state) {
        return orderDetailDAO.findByState(state);
    }

    public OrderDetail serveById(Integer id) {
        OrderDetail orderDetail = orderDetailDAO.findById(id).get();
        orderDetail.setState("已上菜");
        return orderDetailDAO.save(orderDetail);
    }

    public List<OrderDetail> createDishCart() {
        List<Dish> dishList = dishDAO.findAll();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(Dish dish : dishList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDish(dish);
            orderDetail.setCount(0);
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }
}
