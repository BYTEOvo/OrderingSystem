package com.example.orderingsystem.dao;

import com.example.orderingsystem.pojo.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TablesDAO extends JpaRepository<Tables,Integer> {
    Optional<Tables> findById(Integer id);
    List<Tables> findByTableState(String tableState);
    Tables findByName(String name);
}
