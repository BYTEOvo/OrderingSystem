package com.example.orderingsystem.controller;

import com.alipay.api.AlipayApiException;
import com.example.orderingsystem.pojo.Order;
import com.example.orderingsystem.pojo.Tables;
import com.example.orderingsystem.result.Result;
import com.example.orderingsystem.result.ResultFactory;
import com.example.orderingsystem.service.OrderDetailService;
import com.example.orderingsystem.service.OrderService;
import com.example.orderingsystem.service.TablesService;
import com.example.orderingsystem.utils.AlipayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


@RestController
public class AlipayController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    TablesService tablesService;
    private AlipayUtil alipayUtil;

    @Autowired
    public void setAlipayUtil(AlipayUtil alipayUtil) {
        this.alipayUtil = alipayUtil;
    }

//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }

    //    @PostMapping(value="api/create")
//    public String create(@RequestBody Order order, Model model) {
//        Integer i = order.getId();
//        String id=i.toString();
//        Double p = order.getPrice();
//        String price=p.toString();
//        String title = "红烧码铃薯";
//        String pay = alipayUtil.pay(id, price, title);
//        model.addAttribute("form", pay);
//        return "pay";
//    }
    @ResponseBody
    @RequestMapping("/api/create/{rate}")
    public Result alipayByRate(@RequestBody Order order, Model model, @PathVariable Double rate) throws AlipayApiException {
        Integer i = order.getId() + 100;
        String id=i.toString();
        Double p = order.getPrice()*rate;
        BigDecimal bigDecimal = new BigDecimal(p);
        p=bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(p);
        String price=p.toString();
        String title = "红烧码铃薯";
        String pay = alipayUtil.pay(id, price, title,false);
        order.setState("已完成");
        order.setPayTime(new Date());
        Tables tables = tablesService.getTableById(order.getTables().getId());
        tables.setTableState("ready");
        tablesService.updateTable(tables);
        orderService.updateOrder(order);
        model.addAttribute("form", pay);
        //调用封装好的方法（给支付宝接口发送请求）
        return ResultFactory.buildSuccessResult(pay);

        //return pay;
    }

    @ResponseBody
    @RequestMapping("/api/create")
    public Result alipay(@RequestBody Order order, Model model) throws AlipayApiException {
        order = orderService.findById(order.getId());
        Boolean flag;
        if(order.getTables().getName().trim().equals("DELIVERY"))
            flag = true;
        else
            flag = false;
        Integer i = order.getId() + 100;
        String id=i.toString();
        Double p = order.getPrice();
        String price=p.toString();
        String title = "红烧码铃薯";
        String pay = alipayUtil.pay(id, price, title, flag);
        model.addAttribute("form", pay);
        //调用封装好的方法（给支付宝接口发送请求）
        return ResultFactory.buildSuccessResult(pay);

        //return pay;
    }

    @GetMapping(value="api/return")
    public String returnNotice(String out_trade_no, Model model){
        String query = alipayUtil.query(out_trade_no);
        model.addAttribute("query", query);
        return "query";
    }

    @PostMapping("/alipay/notify")
    public void notifyUrl(String out_trade_no, String trade_no, String total_amount, String trade_status, String gmt_payment){
        if(Objects.equals(trade_status, "TRADE_SUCCESS"))
        {
            orderService.complish(out_trade_no,gmt_payment);
        }
        System.err.println("支付宝订单编号：" + out_trade_no+"  "+trade_no + ", 订单金额： " + total_amount + ",订单状态：" + trade_status);
    }
}