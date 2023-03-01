package com.example.orderingsystem.controller.cook;

import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.pojo.OrderDetail;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cy
 * @date 2022/8/19-17:08
 */
@RestController
public class CookDishController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    DishService dishService;
    @Autowired
    DishTypeService dishTypeService;
    @Autowired
    TablesService tablesService;
    @Autowired
    OrderService orderService;
//    @PostMapping("/cook")
//    public String test(@RequestBody Map<String, Object> params) throws IOException {
//        // 获取参数 params 中键为 name 的值
//        String name  = (String) params.get("name");
//        // 获取参数 params 中键为 list 的值
//        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
//        System.out.println("name: " + name);  // 打印接收到的 name 变量的值
//        System.out.println("list: " + list.toString());  // 打印接收到的 list 变量的值
//
//        return "ok";
//    }

    @PostMapping ("/api/{id}/Cookorder")
    public Result CookOrder(@PathVariable("id") Integer id){
        Order order=orderService.cookOrder(id);
        if(order==null)
            return ResultFactory.buildFailResult("修改错误！");
        return ResultFactory.buildSuccessResult(order);
    }

    @GetMapping("/api/CookOrder/findAll")
    public Result CookFindOrder() throws Exception {
        // 应该在Service层使用
        // PageRequest pageRequest = PageRequest.of(page,size);
        List<Order> orderList = orderService.cookFindOrder();
        if(orderList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderList);
    }

    @GetMapping("/api/cook/order/unprocessed")
    public Result listUnprocessedOrderDetails(){
        List<List<OrderDetail>> orderDetailList = new ArrayList<>();
        List<Order> orderList = orderService.listOrdersByState("出餐中");
        for(Order order:orderList){
            List<OrderDetail> temp = orderDetailService.findByOid(order.getId());
            List<OrderDetail> orderDetailList1 = new ArrayList<>();
            for(OrderDetail orderDetail:temp){
                if(orderDetail.getState().trim().equals("制作中"))
                    orderDetailList1.add(orderDetail);
            }
            orderDetailList.add(orderDetailList1);
        }
        return ResultFactory.buildSuccessResult(orderDetailList);
    }

    @GetMapping("/api/cook/order/unprocessed/tables")
    public Result listUnprocessedTablesOrderDetails(){
        List<Order> orderList = orderService.listOrdersByState("出餐中");
        String tables[] = new String[orderList.size()];
        for(int i=0;i<orderList.size();i++){
            tables[i]=orderList.get(i).getTables().getName();
        }
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("/api/cook/{id}/ready")
    public Result dishReady(@PathVariable Integer id){
        OrderDetail orderDetail = orderDetailService.readyById(id);

        if(orderDetail==null)
            return ResultFactory.buildFailResult("修改失败！");
        orderService.judgeDeliveryOrderState(orderDetail.getOrder());
        return ResultFactory.buildSuccessResult(orderDetail);
    }

}
