package com.example.orderingsystem.utils;

import java.util.Date;

public class SalesUtil {
    private Double sale;
    private String date;

    public String getDate() {
        return date;
    }

    public Double getSale() {
        return sale;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }
}
