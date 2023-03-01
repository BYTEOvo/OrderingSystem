package com.example.orderingsystem.controller.waiter;

import com.example.orderingsystem.pojo.*;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.DishService;
import com.example.orderingsystem.service.OrderDetailService;
import com.example.orderingsystem.service.OrderService;
import com.example.orderingsystem.service.TablesService;
import com.example.orderingsystem.utils.DishCartUtil;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cy
 * @date 2022/8/19-14:13
 */
@RestController
public class WaiterOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    DishService dishService;
    @Autowired
    TablesService tablesService;

    @Scheduled(fixedRate = 3000)
    @GetMapping("/api/waiter/order")
    public Result demo() {
        List<List<OrderDetail>> orderList= new ArrayList<>();
        List<Order> orders=orderService.demo();
        if(orders==null)
            return ResultFactory.buildSuccessResult(orders);
        else {
            return ResultFactory.buildSuccessResult(orders);
        }
    }

    @GetMapping("/api/order/deta")
    public Result listAllOrder(){
        List<List<OrderDetail>> orderDetails=new ArrayList<>();
        List<Order> orders=orderService.list();
        for(Order order:orders){
            List<OrderDetail> orderDetail=orderDetailService.findByOid(order.getId());
            orderDetails.add(orderDetail);
        }
        return ResultFactory.buildSuccessResult(orderDetails);
    }
    @GetMapping("/api/ServiceOrder/findAll")
    public Result findo() throws Exception {
        // 应该在Service层使用
        // PageRequest pageRequest = PageRequest.of(page,size);
        List<Order> orderList = orderService.demo();
        if(orderList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderList);
    }
    @PostMapping ("/api/{id}/WaiterSubmitorder")
    public Result WaiterSubmitorder(@PathVariable("id") Integer id){
        Order order=orderService.WaiterOrder(id);
        if(order==null)
            return ResultFactory.buildFailResult("修改错误！");
        return ResultFactory.buildSuccessResult(order);
    }
//    @PostMapping("/api/{tableId}/order")
//    public Result creatOrder(@PathVariable("tableId") int tableId){
//    //, @RequestBody List<OrderDetail> itemList){
//        List<Order> order= orderService.getByOId(tableId);
//        orderService.submitOrder(tableId);
//
//        return ResultFactory.buildSuccessResult(order);
//    }

    @GetMapping("/api/ServiceGetOrder/findAll")
    public Result ServiceGetOrder() throws Exception {
        // 应该在Service层使用
        // PageRequest pageRequest = PageRequest.of(page,size);
        List<Order> orderList = orderService.GetOrder();
        if(orderList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderList);
    }
    @PostMapping ("/api/{id}/WaiterServeorder")
    public Result WaiterServeorder(@PathVariable("id") Integer id){
        Order order=orderService.WaiterServeOrder(id);
        if(order==null)
            return ResultFactory.buildFailResult("修改错误！");
        return ResultFactory.buildSuccessResult(order);
    }

    @GetMapping("/api/waiter/dishes/dishCart")
    public Result createDishCart(){
        List<Dish> dishList=dishService.list();
        List<OrderDetail> dishCart=new ArrayList<>();
        for(Dish dish:dishList){
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setCount(0);
            orderDetail.setDish(dish);
            dishCart.add(orderDetail);
        }
        return ResultFactory.buildSuccessResult(dishCart);
    }

    @GetMapping("/api/waiter/dishes/ready")
    public Result listReadyDishes(){
        List<OrderDetail> orderDetailList = orderDetailService.findByState("制作完成");
        if(orderDetailList==null)
            return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(orderDetailList);
    }

    @GetMapping("/api/waiter/dishes/ready/table")
    public Result listReadyDishesTables(){
        List<OrderDetail> orderDetailList = orderDetailService.findByState("制作完成");
        String tables[] = new String[orderDetailList.size()];
        for(int i=0;i<orderDetailList.size();i++){
            tables[i]  = orderDetailList.get(i).getOrder().getTables().getName();
        }
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("/api/waiter/dish/ready/{id}/serve")
    public Result listReadyDishesTables(@PathVariable Integer id){
        OrderDetail orderDetail = orderDetailService.serveById(id);
        if(orderDetail==null)
            return ResultFactory.buildFailResult("操作失败！");
        Order order = orderDetail.getOrder();
        orderService.judgeOrderState(order);
        return ResultFactory.buildSuccessResult(orderDetail);
    }

    @GetMapping("/api/waiter/tables")
    public Result listAllTables(){
        List<Tables> tables = tablesService.list();
        if(tables==null)
            return ResultFactory.buildFailResult("操作失败！");
        return ResultFactory.buildSuccessResult(tables);
    }

    @GetMapping("/api/waiter/{id}/order/detail")
    public Result getOrderDerailByTableId(@PathVariable Integer id){
        Order order = orderService.getByTableId(id);
        if(order==null){
            orderService.creat("admin",id);
            order = orderService.getByTableId(id);
            return ResultFactory.buildSuccessResult(orderDetailService.createDishCart());
        }

        List<OrderDetail> orderDetailList = orderDetailService.findByOid(order.getId());
        List<OrderDetail> dishCart = orderDetailService.createDishCart();
        for(OrderDetail orderDetail : dishCart){
            for(OrderDetail orderDetail1 : orderDetailList){
                if(orderDetail.getDish().getName().trim().equals(orderDetail1.getDish().getName())){
                    orderDetail.setState(orderDetail1.getState());
                    orderDetail.setCount(orderDetail1.getCount());
                    orderDetail.setOrder(orderDetail1.getOrder());
                    orderDetail.setId(orderDetail1.getId());
                }
            }
        }

        if(orderDetailList==null)
            return ResultFactory.buildFailResult("操作失败！");
        return ResultFactory.buildSuccessResult(dishCart);
    }

    @PostMapping("/api/waiter/order/{tableName}/send")
    public Result waiterOrderSend(@PathVariable("tableName") String tableName,@RequestBody List<OrderDetail> orderDetailList){
        Tables tables = tablesService.getTableByName(tableName);
        Order order = orderService.getByTableId(tables.getId());
        for(OrderDetail orderDetail:orderDetailList) {
            if (orderDetail.getCount() != 0) {
                orderDetailService.addItem(orderDetail, order);
            }
        }
        order.setState("出餐中");
        orderService.updateOrder(order);
        return ResultFactory.buildSuccessResult(order);
    }

    @GetMapping("/api/ServiceGetOrder/findSubmit")
    public Result FindSubmit() throws Exception {
        // 应该在Service层使用
        // PageRequest pageRequest = PageRequest.of(page,size);
        List<Order> orderList = orderService.GetOrder();
        if(orderList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderList);
    }
}
