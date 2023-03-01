package com.example.orderingsystem.controller.admin;

import com.example.orderingsystem.pojo.DishType;
import com.example.orderingsystem.pojo.OrderDetail;
import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.OrderDetailService;
import com.example.orderingsystem.service.DishService;
import com.example.orderingsystem.service.OrderService;
import com.example.orderingsystem.service.TablesService;
import com.example.orderingsystem.utils.DishSaleByTypeUtil;
import com.example.orderingsystem.utils.DishTypeUtil;
import com.example.orderingsystem.utils.PercentageUtil;
import com.example.orderingsystem.utils.SalesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminOrderController {
    @Autowired
    DishService dishService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    TablesService tablesService;
    @GetMapping("/api/manageOrder/findAll/{page}/{size}")
    public Result findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        // 应该在Service层使用
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.listWithPage(pageRequest);
        if(orderPage==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderPage);

    }

    @GetMapping("/api/manageOrder/findAll")
    public Result findAll() throws Exception {
        // 应该在Service层使用
        // PageRequest pageRequest = PageRequest.of(page,size);
        List<Order> orderList = orderService.list();
        if(orderList==null)
            return ResultFactory.buildFailResult("查询错误！");
        return ResultFactory.buildSuccessResult(orderList);
    }

    @GetMapping("/api/order/details")
    public Result listAllOrderDetails(){
        List<List<OrderDetail>> orderDetails=new ArrayList<>();
        List<Order> orders=orderService.list();
        for(Order order:orders){
            List<OrderDetail> orderDetail=orderDetailService.findByOid(order.getId());
            orderDetails.add(orderDetail);
        }
        return ResultFactory.buildSuccessResult(orderDetails);
    }

    @GetMapping("/api/order/eachDishSale/yesterdayAndToday")
    public Result listEachDishSaleOfYesterdayAndToday(){
        List<DishSaleByTypeUtil> dishSaleByTypeUtils =new ArrayList<>();

        List<DishTypeUtil> yesterday = orderService.listEachDishSaleYesterday();
        List<DishTypeUtil> today = orderService.listEachDishSaleToday();
        for(int i=0;i<yesterday.size();i++){
            DishSaleByTypeUtil dishSaleByTypeUtil = new DishSaleByTypeUtil();
            dishSaleByTypeUtil.setDishTypeName(yesterday.get(i).getDishType().getName());
            dishSaleByTypeUtil.setYesterday(yesterday.get(i).getAmount());
            dishSaleByTypeUtil.setToday(today.get(i).getAmount());
            dishSaleByTypeUtils.add(dishSaleByTypeUtil);
        }
        return ResultFactory.buildSuccessResult(dishSaleByTypeUtils);
    }

    @GetMapping("/api/order/eachDishSales/yesterdayAndToday")
    public Result listEachDishSalesOfYesterdayAndToday(){
        List<List<Double>> res =new ArrayList<>();
        List<Double> yesterdayRes = new ArrayList<>();
        List<Double> todayRes = new ArrayList<>();
        List<DishTypeUtil> yesterday = orderService.listEachDishSaleOfYesterday();
        List<DishTypeUtil> today = orderService.listEachDishSaleOfToday();

        for(DishTypeUtil dishTypeUtil:yesterday){
           yesterdayRes.add(dishTypeUtil.getAmount());
        }
        res.add(yesterdayRes);
        for(DishTypeUtil dishTypeUtil:today){
            todayRes.add(dishTypeUtil.getAmount());
        }
        res.add(todayRes);
        return ResultFactory.buildSuccessResult(res);
    }

    @GetMapping("/api/order/eachDishSale/today")
    public Result listEachDishSalesOfYToday(){
        List<DishTypeUtil> eachDishSale= orderService.listEachDishSaleOfToday();
        List<PercentageUtil> percentageUtils = new ArrayList<>();
        for(DishTypeUtil dishTypeUtil:eachDishSale){
            PercentageUtil percentageUtil = new PercentageUtil();
            percentageUtil.setName(dishTypeUtil.getDishType().getName());
            percentageUtil.setValue(dishTypeUtil.getAmount());
            percentageUtils.add(percentageUtil);
        }
        return ResultFactory.buildSuccessResult(percentageUtils);
    }

    @GetMapping("/api/order/eachDaySalesOfMonth")
    public Result listEachDaySalesOfMonth(){
        List<SalesUtil> eachDaySales= orderService.listEachDaySaleOfMonth();
        return ResultFactory.buildSuccessResult(eachDaySales);
    }

    @GetMapping("/api/admin/{id}/order/detail")
    public Result getOrderDerailByTableId(@PathVariable Integer id){
        Order order = orderService.getByTableId(id);
        if(order==null){
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

    @GetMapping("/api/admin/order/{tableId}")
    public Result getOrderByTableId(@PathVariable Integer tableId){
        Order order = orderService.getByTableId(tableId);
        if(order == null)
            return ResultFactory.buildFailResult("查询失败！");
        return ResultFactory.buildSuccessResult(order);
    }
}
