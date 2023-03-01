package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order,Integer> {
    List<Order> findAll();
    Page<Order> findAll(Pageable pageable);
    Order findByUser_UsernameAndState(String username,String state);

    @Query(value = "select * from orders where TO_DAYS(pay_time) = TO_DAYS(NOW())",nativeQuery = true)
    List<Order> findByPayTimeToday();

    @Query(value = "select * from orders where TO_DAYS(now())-TO_DAYS(pay_time)=1",nativeQuery = true)
    List<Order> findByPayTimeYesterday();

    @Query(value = "select * from orders where TO_DAYS(now())-TO_DAYS(pay_time)<=30",nativeQuery = true)
    List<Order> findByPayDateMonth();

    List<Order> findAllByState(String s);

    List<Order> findByTables_Id(Integer id);

    List<Order> findAllByUser_Username(String username);

    List<Order> findAllByUser_UsernameAndState(String username, String status);
    List<Order> findByDelivery_Username(String username);
    List<Order> findByStateAndTables_Name(String state,String tableName);
    List<Order> findAllByUrate(int i);
}
