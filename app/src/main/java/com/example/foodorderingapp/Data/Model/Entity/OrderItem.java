package com.example.foodorderingapp.Data.Model.Entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class OrderItem {
    @DocumentId
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

    @PropertyName("ID_ORDERITEM")
    public String getIdOrderItem() {
        return idOrderItem;
    }

    @PropertyName("ID_ORDERITEM")
    public void setIdOrderItem(String idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    @PropertyName("ID_PRODUCT")
    public String getIdProduct() {
        return idProduct;
    }
    @PropertyName("ID_PRODUCT")
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    @PropertyName("QUANTITY")
    public int getQuantity() {
        return quantity;
    }
    @PropertyName("QUANTITY")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @PropertyName("PRICE")
    public int getPrice() {
        return price;
    }
    @PropertyName("PRICE")
    public void setPrice(int price) {
        this.price = price;
    }

    @PropertyName("NOTE")
    public String getNote() {
        return note;
    }
    @PropertyName("NOTE")
    public void setNote(String note) {
        this.note = note;
    }

    @PropertyName("SIZE")
    public String getSize() {
        return size;
    }
    @PropertyName("SIZE")
    public void setSize(String size) {
        this.size = size;
    }

    @PropertyName("TOPPING")
    public ArrayList<String> getTopping() {
        return topping;
    }
    @PropertyName("TOPPING")
    public void setTopping(ArrayList<String> topping) {
        this.topping = topping;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "idOrderItem='" + idOrderItem + '\'' +
                ", idProduct='" + idProduct + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", size='" + size + '\'' +
                ", topping=" + topping +
                '}';
    }
}
