package com.example.foodorderingapp.domain;

public class OrderItemDomain {
    private String orderid;
    private int totalPrice;
    private int totalDish;

    public OrderItemDomain(String orderid, int totalPrice, int totalDish) {
        this.orderid = orderid;
        this.totalPrice = totalPrice;
        this.totalDish = totalDish;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalDish() {
        return totalDish;
    }

    public void setTotalDish(int totalDish) {
        this.totalDish = totalDish;
    }
}
