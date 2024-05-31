package com.example.javajoyadmin.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class OrderItem {
    private String idProduct;
    private String productName;
    private String productImage;
    private int quantity;
    private int price;
    private String note;
    private String size;
    private ArrayList<String> topping;

    //empty constructor
    public OrderItem(){}

    //constructor
    public OrderItem(String idProduct, String productName, String productImage, int quantity, int price, String note, String size, ArrayList<String> topping) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
        this.size = size;
        this.topping = topping;
    }


    //getter and setter
    @PropertyName("ID_PRODUCT")
    public String getIdProduct() {
        return idProduct;
    }
    @PropertyName("ID_PRODUCT")
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
    @PropertyName("PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }
    @PropertyName("PRODUCT_NAME")
    public void setProductName(String productName) {
        this.productName = productName;
    }
    @PropertyName("PRODUCT_IMAGE")
    public String getProductImage() {
        return productImage;
    }
    @PropertyName("PRODUCT_IMAGE")
    public void setProductImage(String productImage) {
        this.productImage = productImage;
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
                "idProduct='" + idProduct + '\'' +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", size='" + size + '\'' +
                ", topping=" + topping +
                '}';
    }

    // Method to concatenate elements of the topping ArrayList with comma separator
    @Exclude
    public String getToppingString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < topping.size(); i++) {
            stringBuilder.append(topping.get(i));
            if (i < topping.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
