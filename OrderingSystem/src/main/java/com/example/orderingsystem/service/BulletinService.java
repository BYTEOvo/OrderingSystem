package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.BulletinDAO;
import com.example.orderingsystem.pojo.Bulletin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulletinService {
    @Autowired
    BulletinDAO bulletinDAO;

    public List<Bulletin> listBulletinByAccess(String access){
        return bulletinDAO.findByAccess(access);
    }

    public void delById(Integer id) {
        bulletinDAO.deleteById(id);
    }

    public void addBulletin(Bulletin bulletin) {
        bulletinDAO.save(bulletin);
    }
}
