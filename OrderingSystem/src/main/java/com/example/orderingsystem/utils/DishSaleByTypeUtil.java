package com.example.orderingsystem.utils;

public class DishSaleByTypeUtil {
    private String dishTypeName;
    private Double yesterday;
    private Double today;

    public String getDishTypeName() {
        return dishTypeName;
    }

    public Double getToday() {
        return today;
    }

    public void setDishTypeName(String dishTypeName) {
        this.dishTypeName = dishTypeName;
    }

    public Double getYesterday() {
        return yesterday;
    }

    public void setToday(Double today) {
        this.today = today;
    }

    public void setYesterday(Double yesterday) {
        this.yesterday = yesterday;
    }
}
