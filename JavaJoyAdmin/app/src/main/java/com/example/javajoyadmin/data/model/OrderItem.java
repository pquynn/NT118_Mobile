package com.example.javajoyadmin.data.model;

public class OrderItem {
    private String orderid;
    private double orderPrice;
    private int totalDish;

    public OrderItem(String orderid, double orderPrice, int totalDish) {
        this.orderid = orderid;
        this.orderPrice = orderPrice;
        this.totalDish = totalDish;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int totalPrice) {
        this.orderPrice = totalPrice;
    }

    public int getTotalDish() {
        return totalDish;
    }

    public void setTotalDish(int totalDish) {
        this.totalDish = totalDish;
    }
}
