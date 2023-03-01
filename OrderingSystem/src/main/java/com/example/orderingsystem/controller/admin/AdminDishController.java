package com.example.orderingsystem.controller.admin;

import com.example.orderingsystem.pojo.Dish;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class AdminDishController {
    @Autowired
    DishService dishService;

    @GetMapping("/api/dishes")
    public Result list() throws Exception {
        List<Dish> dishList = dishService.listAll();
        if(dishList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(dishList);
    }

    @GetMapping("/api/dishes/findAll/{page}/{size}")
    public Result findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        // 应该在Service层使用
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Dish> dishList = dishService.listWithPage(pageRequest);
        if(dishList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(dishList);
    }

    @PutMapping("/api/dishes/update/{tid}")
    public Result update(@RequestBody Dish dish, @PathVariable("tid") Integer tid){
        Dish res = dishService.save(dish,tid);
        if(res != null){
            return ResultFactory.buildSuccessResult(dish);
        }
        else{
            return ResultFactory.buildFailResult("fail!");
        }
    }

    @DeleteMapping("/api/dishes/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        if(dishService.deleteById(id))
            return ResultFactory.buildSuccessResult(null);
        else
            return ResultFactory.buildFailResult("fail!");
    }

    @PostMapping("/api/dishes/save/{tid}")
    public Result saveNewDish(@RequestBody Dish dish, @PathVariable("tid") Integer tid){
        Dish res=dishService.save(dish,tid);
        if(res != null){
            return ResultFactory.buildSuccessResult(dish);
        }
        else{
            return ResultFactory.buildFailResult("fail!");
        }
    }

//    @PostMapping("api/covers")
//    public String coversUpload(MultipartFile file) throws Exception {
//        String folder = "/usr/local/img";
//        File imageFolder = new File(folder);
//        if (!imageFolder.exists()){
//            imageFolder.mkdirs();
//        }
//        File f = new File(folder+"/", file.getOriginalFilename());
//        if(!f.exists()) {
//            f.createNewFile();
//        }
//        try {
//            file.transferTo(f);
//            String imgURL = "http://123.60.210.166:8443/api/file/" + f.getName();
//            return imgURL;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "C:/Users/huang/Desktop/img";//改为自己存放图片的路径
        File imageFolder = new File(folder);
        File f = new File(imageFolder, file.getOriginalFilename());
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/api/dishes/special")
    public Result listSpecialDishes(){
        List<Dish> dishLIst = dishService.listSpecialDishes();
        if(dishLIst==null)
            return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(dishLIst);
    }
}
