package com.example.orderingsystem.controller.customer;

import com.example.orderingsystem.pojo.*;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.*;
import com.example.orderingsystem.utils.DishCartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerDishController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    DishService dishService;
    @Autowired
    DishTypeService dishTypeService;
    @Autowired
    TablesService tablesService;
    @Autowired
    DishCollectService dishCollectService;
    @Autowired
    UserService userService;

    @GetMapping("/api/dishes/{username}/dishCart")
    public Result createDishCart(@PathVariable("username") String username){
        List<Dish> dishList=dishService.list();
        List<DishCollect> dishCollects = dishCollectService.listDishCollectByUserName(username);
        List<DishCartUtil> dishCart=new ArrayList<>();
        for(Dish dish:dishList){
            OrderDetail orderDetail=new OrderDetail();
            DishCartUtil dishCartUtil = new DishCartUtil();
            orderDetail.setCount(0);
            orderDetail.setDish(dish);
            dishCartUtil.setCollect(false);
            dishCartUtil.setOrderDetail(orderDetail);
            for(DishCollect dishCollect : dishCollects){
                if(dishCollect.getDish().getName().trim().equals(dish.getName())){
                    dishCartUtil.setCollect(true);
                }
            }
            dishCart.add(dishCartUtil);
        }
        return ResultFactory.buildSuccessResult(dishCart);
    }

    @GetMapping("api/dishType")
    public Result getDishTypeList(){
        List<DishType> dishTypeList = dishTypeService.list();
        if(dishTypeList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(dishTypeList);
    }

    @GetMapping("api/dishType/{dishTypeId}/dish")
    public Result getDishByDishType(@PathVariable("dishTypeId") Integer dishTypeId){
        List<Dish> dishList = dishService.listByDishType(dishTypeId);
        if(dishList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(dishList);
    }

    @GetMapping("api/dish/tables/ready")
    public Result getReadyTables(){
        List<Tables> tables = tablesService.listTablesByState("ready");
        if(tables==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("api/dish/tables/using")
    public Result getUsingTables(){
        List<Tables> tables = tablesService.listTablesByState("using");
        if(tables==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("/api/dish/{username}/collect")
    public Result listDishCollectByUser(@PathVariable("username") String username){
        List<DishCollect> dishCollects = dishCollectService.listDishCollectByUserName(username);
        if(dishCollects==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(dishCollects);
    }

    @GetMapping("/api/dish/{username}/collect/add/{dishId}")
    public Result addDishCollect(@PathVariable("username") String username,@PathVariable("dishId") Integer dishId){
        DishCollect dishCollect = new DishCollect();
        Dish dish = dishService.getDishById(dishId);
        User user = userService.findByUsername(username);
        dishCollect.setDish(dish);
        dishCollect.setUser(user);
        DishCollect result = dishCollectService.addDishCollect(dishCollect);
        if(result==null)
            return ResultFactory.buildFailResult("添加失败！");
        return ResultFactory.buildSuccessResult(result);
    }

    @GetMapping("/api/dish/{username}/collect/delete/{dishId}")
    public Result delDishCollect(@PathVariable("username") String username,@PathVariable("dishId") Integer dishId){
        DishCollect dishCollect = dishCollectService.getDishCollectByUserNameAndDishId(username,dishId);
        dishCollectService.delCollect(dishCollect);
        return ResultFactory.buildSuccessResult(null);
    }
}
