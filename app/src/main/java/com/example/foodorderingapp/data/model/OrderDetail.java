package com.example.foodorderingapp.data.model;

import java.util.ArrayList;

public class OrderDetail {
    private String productName;
    private int productPrice;
    private String productSize;
    private String note;
    private int quantity;
    private ArrayList<String> topping;
    public OrderDetail(String productName, int productPrice, String productSize, String note, int quantity){
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.note = note;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
