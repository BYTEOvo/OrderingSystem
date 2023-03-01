package com.example.orderingsystem.service;

import com.example.orderingsystem.dao.*;
import com.example.orderingsystem.pojo.*;
import com.example.orderingsystem.utils.DishTypeUtil;
import com.example.orderingsystem.utils.SalesUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDao;
    @Autowired
    DishDAO dishDao;
    @Autowired
    UserDAO userDao;
    @Autowired
    TablesDAO tablesDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    DishTypeDAO dishTypeDAO;

    public Integer creat(String username,Integer tableId) {
        if(username != "admin") {
            Boolean flag = false;
            List<Order> orderList = orderDao.findAllByUser_Username(username);
            for(Order order : orderList){
                if(!order.getState().trim().equals("已完成")){
                    flag = true;
                }
            }
            if(flag){
                return 1;
            }
        }
        Order order = new Order();
        Tables tables = tablesDAO.findById(tableId).get();
        tables.setTableState("using");
        tablesDAO.save(tables);
        User user = userDao.findByUsername(username);
        order.setTables(tables);
        order.setCreatedTime(new Date());
        order.setState("点菜中");
        order.setUser(user);
        order.setPrice(0.0);
        orderDao.save(order);
        return 0;
    }

    public Integer creatDeliveryOrder(String username,Integer tableId) {
        Boolean flag = false;
        List<Order> orderList = orderDao.findAllByUser_Username(username);
        for(Order order : orderList){
            if((!order.getState().trim().equals("已完成"))){
                flag = true;
            }
        }
        if(flag){
            return 1;
        }
        Order order = new Order();
        Tables tables = tablesDAO.findById(tableId).get();
        tablesDAO.save(tables);
        User user = userDao.findByUsername(username);
        order.setTables(tables);
        order.setCreatedTime(new Date());
        order.setState("点菜中");
        order.setUser(user);
        order.setPrice(0.0);
        orderDao.save(order);
        return 0;
    }

    public List<Order> list() {
        return orderDao.findAll();
    }

    public Page<Order> listWithPage(PageRequest pageRequest) {
        return orderDao.findAll(pageRequest);
    }

    public Order getByUserNameAndState(String username, String state) {
        return orderDao.findByUser_UsernameAndState(username,state);
    }

    public List<DishTypeUtil> listEachDishSaleYesterday() {
        List<DishType> dishTypeList = dishTypeDAO.findAll();
        List<DishTypeUtil> dishTypeUtils = new ArrayList<>();
        for(DishType dishType: dishTypeList){
            DishTypeUtil dishTypeUtil = new DishTypeUtil();
            dishTypeUtil.setDishType(dishType);
            dishTypeUtil.setAmount(0);
            dishTypeUtils.add(dishTypeUtil);
        }
        List<Order> orderList = orderDao.findByPayTimeYesterday();
        for(Order order:orderList){
            List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
            for(OrderDetail orderDetail:orderDetailList){
                for(DishTypeUtil dishTypeUtil:dishTypeUtils){
                    if(orderDetail.getDish().getDishType().getName().trim().equals(dishTypeUtil.getDishType().getName())){
                        dishTypeUtil.setAmount(dishTypeUtil.getAmount()+orderDetail.getCount());
                        break;
                    }
                }
            }
        }
        return dishTypeUtils;
    }

    public List<DishTypeUtil> listEachDishSaleToday() {
        List<DishType> dishTypeList = dishTypeDAO.findAll();
        List<DishTypeUtil> dishTypeUtils = new ArrayList<>();
        for(DishType dishType: dishTypeList){
            DishTypeUtil dishTypeUtil = new DishTypeUtil();
            dishTypeUtil.setDishType(dishType);
            dishTypeUtil.setAmount(0);
            dishTypeUtils.add(dishTypeUtil);
        }
        List<Order> orderList = orderDao.findByPayTimeToday();
        for(Order order:orderList){
            List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
            for(OrderDetail orderDetail:orderDetailList){
                for(DishTypeUtil dishTypeUtil:dishTypeUtils){
                    if(orderDetail.getDish().getDishType().getName().trim().equals(dishTypeUtil.getDishType().getName())){
                        dishTypeUtil.setAmount(dishTypeUtil.getAmount()+orderDetail.getCount());
                        break;
                    }
                }
            }
        }
        return dishTypeUtils;
    }

    public List<DishTypeUtil> listEachDishSaleOfToday() {
        List<DishType> dishTypeList = dishTypeDAO.findAll();
        List<DishTypeUtil> dishTypeUtils = new ArrayList<>();
        for(DishType dishType: dishTypeList){
            DishTypeUtil dishTypeUtil = new DishTypeUtil();
            dishTypeUtil.setDishType(dishType);
            dishTypeUtil.setAmount(0);
            dishTypeUtils.add(dishTypeUtil);
        }
        List<Order> orderList = orderDao.findByPayTimeToday();
        for(Order order:orderList){
            List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
            for(OrderDetail orderDetail:orderDetailList){
                for(DishTypeUtil dishTypeUtil:dishTypeUtils){
                    if(orderDetail.getDish().getDishType().getName().trim().equals(dishTypeUtil.getDishType().getName())){
                        dishTypeUtil.setAmount(dishTypeUtil.getAmount()+orderDetail.getCount()*orderDetail.getDish().getPrice());
                        break;
                    }
                }
            }
        }
        return dishTypeUtils;
    }

    public List<DishTypeUtil> listEachDishSaleOfYesterday() {
        List<DishType> dishTypeList = dishTypeDAO.findAll();
        List<DishTypeUtil> dishTypeUtils = new ArrayList<>();
        for(DishType dishType: dishTypeList){
            DishTypeUtil dishTypeUtil = new DishTypeUtil();
            dishTypeUtil.setDishType(dishType);
            dishTypeUtil.setAmount(0);
            dishTypeUtils.add(dishTypeUtil);
        }
        List<Order> orderList = orderDao.findByPayTimeYesterday();
        for(Order order:orderList){
            List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
            for(OrderDetail orderDetail:orderDetailList){
                for(DishTypeUtil dishTypeUtil:dishTypeUtils){
                    if(orderDetail.getDish().getDishType().getName().trim().equals(dishTypeUtil.getDishType().getName())){
                        dishTypeUtil.setAmount(dishTypeUtil.getAmount()+orderDetail.getCount()*orderDetail.getDish().getPrice());
                        break;
                    }
                }
            }
        }
        return dishTypeUtils;
    }

    public List<SalesUtil> listEachDaySaleOfMonth(){
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        Calendar temp = Calendar.getInstance();
        ca.setTime(new Date()); // 设置时间为当前时间
        temp.setTime(new Date());
        //ca.add(Calendar.YEAR, -1); // 年份减1
        ca.add(Calendar.MONTH, -1);// 月份减1
        temp.add(Calendar.MONTH, -1);

        ca.set(Calendar.HOUR_OF_DAY, 0);        //小时
        ca.set(Calendar.MINUTE, 0);        //分钟
        ca.set(Calendar.SECOND, 0);        //秒
        ca.set(Calendar.MILLISECOND, 0);   //毫秒

        temp.set(Calendar.HOUR_OF_DAY, 0);        //小时
        temp.set(Calendar.MINUTE, 0);        //分钟
        temp.set(Calendar.SECOND, 0);        //秒
        temp.set(Calendar.MILLISECOND, 0);   //毫秒
        //ca.add(Calendar.DATE, -1);// 日期减1

        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(new Date());
        todayStart.set(Calendar.HOUR_OF_DAY, 0);        //小时
        todayStart.set(Calendar.MINUTE, 0);        //分钟
        todayStart.set(Calendar.SECOND, 0);        //秒
        todayStart.set(Calendar.MILLISECOND, 0);   //毫秒

        Date resultDate = ca.getTime(); // 结果
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        resultDate = todayStart.getTime();
        List<SalesUtil> res = new ArrayList<>();
        List<Order> orderList = orderDao.findByPayDateMonth();
        while(!temp.getTime().after(todayStart.getTime())){
            SalesUtil salesUtil = new SalesUtil();
            Double sales = 0.0;
            temp.add(Calendar.DATE,+1);
            for(Order order:orderList){
                resultDate = temp.getTime();
                if(order.getPayTime().after(ca.getTime())&&order.getPayTime().before(temp.getTime())){
                    sales+=order.getPrice();
                }
            }
            salesUtil.setSale(sales);
            salesUtil.setDate(sdf.format(ca.getTime()));
            res.add(salesUtil);
            ca.add(Calendar.DATE,+1);
        }
        return res;
    }

    public List<Order> demo() {
        return orderDao.findAllByState("1");
    }

    public Order submito(int id) {
        Order order=orderDao.findById(id).get();
//        Tables tables= tablesDAO.findById(tableid);
//        tables.setTableState("occupy");
        order.setState("2");
        return orderDao.save(order);
    }

    public Order WaiterOrder(int id) {
        Order order=orderDao.findById(id).get();
//        Tables tables= tablesDAO.findById(tableid);
//        tables.setTableState("occupy");
        order.setState("3");
        return orderDao.save(order);
    }
    public Order cookOrder(int id) {
        Order order=orderDao.findById(id).get();
//        Tables tables= tablesDAO.findById(tableid);
//        tables.setTableState("occupy");
        order.setState("4");
        return orderDao.save(order);
    }

    public List<Order> AdminOrder() {
        return orderDao.findAllByState("3");

    }

    public List<Order> cookFindOrder() {
        return orderDao.findAllByState("3");
    }

    public List<Order> GetOrder() {
        return orderDao.findAllByState("已点菜");
    }

    public Order WaiterServeOrder(int id) {
        Order order=orderDao.findById(id).get();
        order.setState("5");
        return orderDao.save(order);
    }

    public List<Order> listOrdersByState(String state) {
        return orderDao.findAllByState(state);
    }

    public void updateOrder(Order order) {
        orderDao.save(order);
        return;
    }

    public void judgeOrderState(Order order) {
        List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
        Boolean flag = true;
        for(OrderDetail orderDetail:orderDetailList){
            if(!orderDetail.getState().trim().equals("已上菜"))
                flag=false;
        }
        if(flag){
            order.setState("出餐完毕");
            orderDao.save(order);
        }
    }

    public void judgeDeliveryOrderState(Order order) {
        List<OrderDetail> orderDetailList = orderDetailDAO.findAllByOrder(order);
        Boolean flag = true;
        for(OrderDetail orderDetail:orderDetailList){
            if(!orderDetail.getState().trim().equals("制作完成"))
                flag=false;
        }
        if(flag){
            order.setState("出餐完毕");
            orderDao.save(order);
        }
    }

    public Order getByTableId(Integer id) {
        List<Order> orderList = orderDao.findByTables_Id(id);
        for(Order order: orderList){
            if(!order.getState().trim().equals("已完成"))
                return order;
        }
        return null;
    }

    public Order complish(String id, String v) {
        String i=id;
        String cc=v;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(cc, pos);
        System.out.println(i);
        Order order=orderDao.findById(Integer.parseInt(i)).get();
        order.setState("已完成");
        order.setPayTime(new Date());
        int tid=order.getTables().getId();
        Tables table=tablesDAO.findById(tid).get();
        table.setTableState("ready");
        order.setPayTime(strtodate);
        return orderDao.save(order);
    }
    public int comment(Order order, Integer id) {
        Order oldOrder=orderDao.findById(id).get();
        oldOrder.setUrate(order.getUrate());
        oldOrder.setR1(order.getR1());
        oldOrder.setR2(order.getR2());
        oldOrder.setR3(order.getR3());
        oldOrder.setR4(order.getR4());
        oldOrder.setComments(order.getComments());
        orderDao.save(oldOrder);
        return 1;
    }

    public Page<Order> findAllWithPage(PageRequest pageRequest) {
        return orderDao.findAll(pageRequest);

    }

    public Order findById(Integer id) {
        Order order=orderDao.findById(id).get();
        return order;
    }

    public Order getRightByUserNameAndState(String username, String status) {
        List<Order> orderList=orderDao.findAllByUser_UsernameAndState(username,status);
        Order order= orderList.get(orderList.size()-1);
        return order;
    }
    public Order getRightPaidByUserName(String username) {
        List<Order> orderList=orderDao.findAllByUser_Username(username);
        Order order= orderList.get(orderList.size()-1);
        return order;
    }

    public List<Order> listOrdersByDelivery(String username) {
        userDao.findByUsername(username);
        return orderDao.findByDelivery_Username(username);
    }

    public List<Order> listOrdersByStateAndTableName(String state, String tableName) {
        return orderDao.findByStateAndTables_Name(state,tableName);
    }

    public Integer deliveryReceiveOrders(Integer orderId, String username) {
        User user = userDao.findByUsername(username);
        if(user==null)
            return 1;
        Order order = orderDao.findById(orderId).get();
        if(order==null)
            return 2;
        order.setDelivery(user);
        order.setState("已接单");
        orderDao.save(order);
        return 0;
    }

    public Integer sendOrderById(Integer orderId) {
        Order order = orderDao.findById(orderId).get();
        if(order == null)
            return 1;
        order.setState("已完成");
        order.setPayTime(new Date());
        orderDao.save(order);
        return 0;
    }

    public List<Order> listFourComment() {
        List<Order> orderList=orderDao.findAllByUrate(4);
        return orderList;
    }

    public List<Order> listFiveComment() {
        List<Order> orderList=orderDao.findAllByUrate(5);
        return orderList;
    }

    public List<Order> listThreeComment() {
        List<Order> orderList=orderDao.findAllByUrate(3);
        return orderList;
    }

    public List<Order> listTwoComment() {
        List<Order> orderList=orderDao.findAllByUrate(2);
        return orderList;
    }

    public List<Order> listOneComment() {
        List<Order> orderList=orderDao.findAllByUrate(1);
        return orderList;
    }
}
