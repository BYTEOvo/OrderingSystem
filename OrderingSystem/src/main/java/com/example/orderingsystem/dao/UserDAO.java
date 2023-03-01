package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer>{
    User findByUsername(String username);
    User getByUsernameAndPassword(String username,String password);
    User findById(int id);
    List<User> findByRole(String role);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);

    //
}
