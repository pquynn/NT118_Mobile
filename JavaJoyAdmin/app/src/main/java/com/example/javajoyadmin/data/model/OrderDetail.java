package com.example.javajoyadmin.data.model;

public class OrderDetail {
    private String productName;
    private int productPrice;
    private String productSize;
    private String note;
    private int quantity;
    private String productImage;

    public OrderDetail(String productName, int productPrice, String productSize, String note, int quantity, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.note = note;
        this.quantity = quantity;
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productSize='" + productSize + '\'' +
                ", note='" + note + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
