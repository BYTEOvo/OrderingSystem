package com.example.orderingsystem.controller.customer;

import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.pojo.OrderDetail;
import com.example.orderingsystem.pojo.Tables;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.OrderDetailService;
import com.example.orderingsystem.service.OrderService;
import com.example.orderingsystem.service.TablesService;
import com.example.orderingsystem.service.UserService;
import com.example.orderingsystem.utils.DishCartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
public class CustomerOrderController {
    @Autowired
    TablesService tablesService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    UserService userService;

    @GetMapping("/api/table/ready")
    public Result listReadyTables(){
        List<Tables> tables = tablesService.listTablesByState("ready");
        if (tables == null) {
            return ResultFactory.buildFailResult("查询错误！");
        }
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("/api/table/using")
    public Result listUsingTables(){
        List<Tables> tables = tablesService.listTablesByState("using");
        if (tables == null) {
            return ResultFactory.buildFailResult("查询错误！");
        }
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("/api/order/{username}/creat/{tableId}")
    public Result creatOrder(@PathVariable("username") String username,@PathVariable("tableId") Integer tableId){
        Integer result = orderService.creat(username,tableId);
        if(result==1)
            return ResultFactory.buildFailResult("已有订单进行中！");
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping("/api/{username}/order")
    public Result addOrderDetail(@PathVariable("username") String username, @RequestBody List<DishCartUtil> dishCartUtils){
        Order order = orderService.getByUserNameAndState(username,"已点菜");
        if(order==null)
            order = orderService.getByUserNameAndState(username,"点菜中");
        if(order==null)
            return ResultFactory.buildFailResult("没有正在点菜的订单！");
        for(DishCartUtil dishCartUtil :dishCartUtils) {
            if(dishCartUtil.getOrderDetail().getCount()!=0) {
                orderDetailService.addItem(dishCartUtil.getOrderDetail(), order);
            }
        }
        if(!order.getTables().getName().equals("DELIVERY"))
            order.setState("已点菜");
        else
            order.setState("出餐中");
        orderService.updateOrder(order);
        return ResultFactory.buildSuccessResult(dishCartUtils);
    }

    @GetMapping("/api/order/{username}/history")
    public Result listHistoryOrders(@PathVariable("username") String username){
        List<Order> tempOrderList = orderService.list();
        List<Order> orderList = new ArrayList<>();
        for(Order order:tempOrderList){
            if(order.getUser().getUsername().trim().equals(username)){
               orderList.add(order);
            }
        }
        if(tempOrderList==null)
            return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(orderList);
    }

    @GetMapping("/api/order/{username}/details")
    public Result listHistoryOrderDetails(@PathVariable("username") String username){
        List<List<OrderDetail>> orderDetails=new ArrayList<>();
        List<Order> orders=orderService.list();
        for(Order order:orders){
            List<OrderDetail> orderDetail=orderDetailService.findByOidAndUserName(order.getId(),username);
            if(orderDetail.size()!=0){
                orderDetails.add(orderDetail);
            }
        }
        return ResultFactory.buildSuccessResult(orderDetails);
    }

    @GetMapping("/api/order/{username}/entercomment")
    public Result EnterComment(@PathVariable("username") String username) {
        Order order = orderService.getByUserNameAndState(username, "已完成");
        if (order == null) {
            return ResultFactory.buildFailResult("订单未结账");
        } else {
            return ResultFactory.buildSuccessResult(order);
        }
    }

    @PostMapping("/api/{username}/comment")
    public Result customerComment (@PathVariable("username") String username,@RequestBody Order order)
    {
        Order order1=orderService.getRightPaidByUserName(username);
        System.out.println(order1.getId());
        System.out.println(order1.getComments());
        int result=orderService.comment(order,order1.getId());
        if(result==1)
        {
            return ResultFactory.buildSuccessResult("success");
        }
        else
        {
            return  ResultFactory.buildFailResult("fail");
        }
    }

    @GetMapping("/api/customer/{username}/paid")
    public Result findPaid(@PathVariable("username") String username){
        Order order1=orderService.getRightPaidByUserName(username);
        String status=order1.getState();
        System.out.println(status);
        System.out.println(order1.getId());

        if(Objects.equals(status, "已完成"))
        {
            return ResultFactory.buildSuccessResult(order1);
        }
        return ResultFactory.buildFailResult("查询错误！");
    }

    @GetMapping("/api/order/{username}/delivery/creat")
    public Result createDeliveryOrder(@PathVariable("username") String username){
        Tables tables = tablesService.getTableByName("DELIVERY");
        Integer result = orderService.creatDeliveryOrder(username,tables.getId());
        if(result==1)
            return ResultFactory.buildFailResult("已有订单进行中！");
        return ResultFactory.buildSuccessResult(null);
    }

    @GetMapping("/api/customer/{username}/order/now")
    public Result getRecentOrder(@PathVariable("username")String username){
        Order order = orderService.getRightByUserNameAndState(username,"点菜中");
        if(order == null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(order);
    }

    @PostMapping("/api/customer/order/{username}/delivery/address")
    public Result updateOrderAddress(@RequestBody Order order, @PathVariable String username){
        Order order1 = orderService.getRightByUserNameAndState(username,"点菜中");
        if(order1 == null)
            return ResultFactory.buildFailResult("查询错误！");
        order1.setAddress(order.getAddress());
        order1.setPhoneNumber(order.getPhoneNumber());
        orderService.updateOrder(order1);
        return ResultFactory.buildSuccessResult(order1);
    }

    @GetMapping("/api/comment/findAll")
    public Result findAllComment() {
        List<Order> orders = orderService.list();
        if(orders==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orders);
    }

    @GetMapping("/api/comment/findAllFiveComment")
    public Result findAllFiveComment() {
        List<Order> orders = orderService.listFiveComment();
        System.out.println(orders);
        if(orders==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orders);
    }

    @GetMapping("/api/comment/findAllFourComment")
    public Result findAllFourComment() {
        List<Order> orders = orderService.listFourComment();
        System.out.println(orders);
        if(orders==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orders);
    }

    @GetMapping("/api/comment/findAllThreeComment")
    public Result findAllThreeComment() {
        List<Order> orders = orderService.listThreeComment();
        System.out.println(orders);
        if(orders==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orders);
    }

    @GetMapping("/api/comment/findAllTwoComment")
    public Result findAllTwoComment() {
        List<Order> orders = orderService.listTwoComment();
        System.out.println(orders);
        if(orders==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orders);
    }

    @GetMapping("/api/comment/findAllOneComment")
    public Result findAllOneComment() {
        List<Order> orders = orderService.listOneComment();
        System.out.println(orders);
        if(orders==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orders);
    }
}
