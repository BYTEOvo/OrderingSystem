package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.UserDAO;
import com.example.orderingsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user=getByName(username);
        return null!=user;
    }

    public User getByName(String username) {

        return userDAO.findByUsername(username);
    }

    public User get(String username, String password) {

        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void addOrUpdate(User user) {
        userDAO.save(user);
    }

    public User findByUsername(String userName) {
        return userDAO.findByUsername(userName);
    }

    public int register(User user){
        String username=user.getUsername();
        String password=user.getPassword();

        if (username.equals("") || password.equals("")) {
            return 0;
        }

        if(password.length()<6)
        {
            return 3;
        }

        boolean exist = isExist(username);

        if (exist) {
            return 2;
        }

        user.setRole("customer");
        userDAO.save(user);

        return 1;
    }

    public int updatePwd(User user){
        String username=user.getUsername();
        String password=user.getPassword();

        if(password.length()<6)
        {
            return 0;
        }

        int userId = userDAO.findByUsername(username).getId();
        User oldUser = userDAO.findById(userId);
        oldUser.setPassword(password);
        userDAO.save(oldUser);
        return 1;
    }

    public List<User> list(){
        return userDAO.findByRole("user");
    }

    public int getUserNumber() {
        return userDAO.findByRole("user").size();
    }

    public int login(User user) {
        User tempUser=findByUsername(user.getUsername());
        if(tempUser!=null&&tempUser.getPassword().trim().equals(user.getPassword()))
            return 1;
        else
            return 0;
    }

    public Page<User> findAllWithPage(PageRequest pageRequest) {
        return userDAO.findAll(pageRequest);
    }

    public User findUserById(Integer id) {
        return userDAO.findById(id).get();
    }
}
