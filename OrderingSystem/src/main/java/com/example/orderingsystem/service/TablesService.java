package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.TablesDAO;
import com.example.orderingsystem.pojo.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TablesService {
    @Autowired
    TablesDAO tablesDAO;

    public Tables getTableById(Integer id){
        return tablesDAO.findById(id).get();
    }

    public List<Tables> listTablesByState(String state){
        return tablesDAO.findByTableState(state);
    }

    public List<Tables> list() {
        return tablesDAO.findAll();
    }

    public Tables getTableByName(String tableName) {
        return tablesDAO.findByName(tableName);
    }

    public void updateTable(Tables tables) {
        tablesDAO.save(tables);
        return;
    }
}
