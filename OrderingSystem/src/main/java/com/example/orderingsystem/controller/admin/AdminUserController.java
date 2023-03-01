package com.example.orderingsystem.controller.admin;

import com.example.orderingsystem.pojo.User;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cy
 * @date 2022/8/5-17:48
 */
@RestController
public class AdminUserController {
    @Autowired
    UserService userService;

    @GetMapping("/api/manageUser/findAll/{page}/{size}")
    public Result findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userService.findAllWithPage(pageRequest);
        if(userPage==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(userPage);
    }

    @GetMapping("/api/manageUser/findAll")
    public Result findAll() throws Exception {
        // 应该在Service层使用
        // PageRequest pageRequest = PageRequest.of(page,size);
        List<User> userList = userService.list();
        if(userList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(userList);
    }

    @GetMapping("/api/user/{username}/info")
    public Result GetUserInfo(@PathVariable("username") String username) throws Exception {
        User user = userService.findByUsername(username);
        if(user==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(user);
    }

    @PostMapping("/api/user/info/editPassWord")
    public Result EditUserPwd(@RequestBody User user) throws Exception {
        int result = userService.updatePwd(user);
        if(result==0)
            return ResultFactory.buildFailResult("密码长度需大于等于六位！");
        return ResultFactory.buildSuccessResult(user);
    }
}



