package com.example.orderingsystem.utils;

import com.example.orderingsystem.pojo.OrderDetail;

public class DishCartUtil {
    private OrderDetail orderDetail;
    private Boolean isCollect;

    public Boolean getCollect() {
        return isCollect;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setCollect(Boolean collect) {
        isCollect = collect;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
