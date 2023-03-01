package com.example.orderingsystem.controller;

import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.service.UserService;
import com.example.orderingsystem.utils.UserUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.orderingsystem.pojo.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    Producer producer;
    @Autowired
    HttpServletRequest request;


    @PostMapping(value = "api/login")
    public Result login(@RequestBody UserUtil requestUser) {
        User user=requestUser.getUser();
        String username = user.getUsername();
        String code= (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if(requestUser.getCode().trim().equals(code)) {
            int result = userService.login(user);
            if (result == 1) {
                user=userService.findByUsername(username);
                return ResultFactory.buildSuccessResult(user);
            }
            String message = "账号密码错误";
            return ResultFactory.buildFailResult(message);
        }
        String message= "验证码输入错误";
        return ResultFactory.buildFailResult(message);
    }

    @PostMapping(value="api/register")
    public Result register(@RequestBody User user){
        int result=userService.register(user);

        if(result == 0){
            String message="用户名或者密码不能为空";
            return ResultFactory.buildFailResult(message);
        }
        else if(result == 2){
            String message="用户名已被注册";
            return ResultFactory.buildFailResult(message);
        }
        else if(result == 3){
            String message="密码不得少于6位";
            return ResultFactory.buildFailResult(message);
        }

        return ResultFactory.buildSuccessResult(user);
    }

    @GetMapping("api/logout")
    public Result logout() {
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/api/user/{username}/role")
    public Result listByUserName(@PathVariable("username") String username) throws Exception {
        User user = userService.findByUsername(username);
        if(user==null){
            return ResultFactory.buildFailResult("查询错误！");
        }
        return ResultFactory.buildSuccessResult(user);
    }

    @GetMapping("/api/creatCodeImg")
    public void createImageCode(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        HttpSession session = request.getSession();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }
}

