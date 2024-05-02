package com.example.foodorderingapp.data.model;

import java.util.ArrayList;
import java.util.Locale;

public class BuyingProduct {
    private String productId;
    private String productName;
    private int productPrice;
    private String productSize;
    private String note;
    private int quantity;
    private String productImage;
    private ArrayList<String> topping;

    public BuyingProduct(){

    }

    public BuyingProduct(String productId, String productName, int productPrice, String productSize, String note, int quantity, String productImage, ArrayList<String> topping) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.note = note;
        this.quantity = quantity;
        this.productImage = productImage;
        this.topping = topping;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ArrayList<String> getTopping() {
        return topping;
    }

    public void setTopping(ArrayList<String> topping) {
        this.topping = topping;
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
        return "BuyingProduct{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productSize='" + productSize + '\'' +
                ", note='" + note + '\'' +
                ", quantity=" + quantity +
                ", productImage='" + productImage + '\'' +
                ", topping=" + topping +
                '}';
    }

    public static String formatPrice(int price) {
        double db_price = (double) price;
        return String.format(Locale.getDefault(), "%,.0f đ", price);
    }

}