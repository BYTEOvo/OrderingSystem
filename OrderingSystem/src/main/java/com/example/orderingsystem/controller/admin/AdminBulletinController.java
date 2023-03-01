package com.example.orderingsystem.controller.admin;

import com.example.orderingsystem.pojo.Bulletin;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminBulletinController {
    @Autowired
    BulletinService bulletinService;

    @GetMapping("/api/bulletin/delete/{id}")
    public Result DelBulletinById(@PathVariable("id") Integer id){
        bulletinService.delById(id);
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping("/api/bulletin/add")
    public Result addBulletin(@RequestBody Bulletin bulletin){
        bulletinService.addBulletin(bulletin);
        return ResultFactory.buildSuccessResult(bulletin);
    }
}
