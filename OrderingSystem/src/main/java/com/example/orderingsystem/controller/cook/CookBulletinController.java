package com.example.orderingsystem.controller.cook;

import com.example.orderingsystem.pojo.Bulletin;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CookBulletinController {
    @Autowired
    BulletinService bulletinService;

    @GetMapping("/api/cook/bulletins")
    public Result listCookBulletin(){
        List<Bulletin> bulletins = bulletinService.listBulletinByAccess("staff");
        if(bulletins==null)
            return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(bulletins);
    }
}
