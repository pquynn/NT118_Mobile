package com.example.foodorderingapp.Data.Model.Entity;

import java.util.ArrayList;

public class OrderItem {
    private String idOrderItem;
    private String idProduct;
    private int quantity;
    private int price;
    private String note;
    private String size;
    private ArrayList<String> topping;

    //empty constructor
    public OrderItem(){}

    //constructor doesn't have size and topping
    public OrderItem(String idOrderItem, String idProduct, int quantity, int price, String note) {
        this.idOrderItem = idOrderItem;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
    }

    //constructor doesn't have size
    public OrderItem(String idOrderItem, String idProduct, int quantity, int price, String note, ArrayList<String> topping) {
        this.idOrderItem = idOrderItem;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
        this.topping = topping;
    }

    //constructor
    public OrderItem(String idOrderItem, String idProduct, int quantity, int price, String note, String size, ArrayList<String> topping) {
        this.idOrderItem = idOrderItem;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
        this.size = size;
        this.topping = topping;
    }

    //getter and setter

    public String getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(String idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ArrayList<String> getTopping() {
        return topping;
    }

    public void setTopping(ArrayList<String> topping) {
        topping.clear();
        topping.addAll(topping);
    }
}
