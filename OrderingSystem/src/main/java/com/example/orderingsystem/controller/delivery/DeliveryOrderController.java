package com.example.orderingsystem.controller.delivery;

import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.pojo.OrderDetail;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.OrderDetailService;
import com.example.orderingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DeliveryOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/api/delivery/order/receive")
    public Result listAllDeliveryOrder(){
        List<Order> orderList = orderService.listOrdersByStateAndTableName("出餐完毕","DELIVERY");
        if(orderList==null)
            return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(orderList);
    }

    @GetMapping("/api/delivery/order/{username}/send")
    public Result listDeliveryPersonalOrder(@PathVariable("username") String username){
        List<Order> orderList = orderService.listOrdersByDelivery(username);
        if(orderList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderList);
    }

    @GetMapping("/api/delivery/order/details")
    public Result listDeliveryOrderDetails(){
        List<List<OrderDetail>> orderDetails=new ArrayList<>();
        List<Order> orders=orderService.listOrdersByStateAndTableName("出餐完毕","DELIVERY");
        for(Order order:orders){
            List<OrderDetail> orderDetail=orderDetailService.findByOid(order.getId());
            if(orderDetail.size()!=0){
                orderDetails.add(orderDetail);
            }
        }
        return ResultFactory.buildSuccessResult(orderDetails);
    }

    @GetMapping("/api/delivery/order/{username}/details")
    public Result listDeliveryPersonalOrderDetails(@PathVariable String username){
        List<List<OrderDetail>> orderDetails=new ArrayList<>();
        List<Order> orders=orderService.listOrdersByDelivery(username);
        for(Order order:orders){
            List<OrderDetail> orderDetail=orderDetailService.findByOid(order.getId());
            if(orderDetail.size()!=0){
                orderDetails.add(orderDetail);
            }
        }
        return ResultFactory.buildSuccessResult(orderDetails);
    }

    @GetMapping("/api/delivery/order/{username}/receive")
    public Result listAllPersonalOrderReceive(@PathVariable("username") String username){
        List<Order> orderList = orderService.listOrdersByDelivery(username);
        return ResultFactory.buildSuccessResult(orderList);
    }

    @GetMapping("/api/delivery/order/{orderId}/send/{username}")
    public Result deliveryReceiveOrders(@PathVariable("orderId") Integer orderId,@PathVariable("username") String username){
        Integer result = orderService.deliveryReceiveOrders(orderId,username);
        if(result == 1 || result == 2){
            return ResultFactory.buildFailResult("查询失败！");
        }
        return ResultFactory.buildSuccessResult(null);
    }

    @GetMapping("/api/delivery/order/send/details")
    public Result listDeliveryOrderSendDetails(){
        List<List<OrderDetail>> orderDetails=new ArrayList<>();
        List<Order> orders=orderService.listOrdersByStateAndTableName("已接单","DELIVERY");
        for(Order order:orders){
            List<OrderDetail> orderDetail=orderDetailService.findByOid(order.getId());
            if(orderDetail.size()!=0){
                orderDetails.add(orderDetail);
            }
        }
        return ResultFactory.buildSuccessResult(orderDetails);
    }

    @GetMapping("/api/delivery/order/send/{orderId}")
    public Result sendOrders(@PathVariable("orderId") Integer orderId){
       Integer result = orderService.sendOrderById(orderId);
       if(result==1)
           return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(null);
    }
}
