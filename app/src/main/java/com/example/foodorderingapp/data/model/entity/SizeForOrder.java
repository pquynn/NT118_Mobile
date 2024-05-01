package com.example.foodorderingapp.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

public class SizeForOrder {
    @DocumentId
    private String name;
    private int price;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String nameSize) {
        this.name = nameSize;
    }

    @PropertyName("PRICE")
    public int getPrice() {
        return price;
    }
    @PropertyName("PRICE")
    public void setPrice(int price) {
        this.price = price;
    }
    @PropertyName("QUANTITY")
    public int getQuantity() {
        return quantity;
    }
    @PropertyName("QUANTITY")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SizeForOrder(){}
    public SizeForOrder(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}