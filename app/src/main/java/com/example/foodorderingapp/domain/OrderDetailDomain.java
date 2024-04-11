package com.example.foodorderingapp.domain;

public class OrderDetailDomain {
    private String productName;
    private String productPrice;
    private String productSize;
    private String note;
    private int quantity;
    public OrderDetailDomain(String productName, String productPrice, String productSize, String note, int quantity){
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
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
