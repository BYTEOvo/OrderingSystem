package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<com.example.orderingsystem.pojo.OrderDetail,Integer> {
    List<OrderDetail> findAllByOrder(Order order);
    List<OrderDetail> findByOrder_IdAndOrder_User_Username(Integer id,String username);

    List<OrderDetail> findByState(String state);
}
