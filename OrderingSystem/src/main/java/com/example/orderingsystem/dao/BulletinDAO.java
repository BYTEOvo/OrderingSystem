package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulletinDAO extends JpaRepository<Bulletin,Integer> {
    List<Bulletin> findByAccess(String access);
    void deleteById(Integer id);
}
