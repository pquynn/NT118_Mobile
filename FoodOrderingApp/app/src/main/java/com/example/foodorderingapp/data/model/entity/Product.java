package com.example.foodorderingapp.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;
import java.util.Map;

public class Product {
    @DocumentId
    private String id;
    private String productImage;
    private String idCategory;
    private String productName;
    private int productPrice;
    private String productInfo;
    @ServerTimestamp
    private Map<String, Map<String, Integer>> productSize;
    private List<String> topping;

    public Product() {}

    //constructor lấy id, idCategory, image, name, price
    public Product(String id, String idCategory, String productImage, String productName, int productPrice) {
        this.id = id;
        this.productImage = productImage;
        this.idCategory = idCategory;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    //construct lấy id, ảnh, tên, giá, mô tả
    public Product(String id, String productImage, String productName, int productPrice, String productInfo) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
    }

    public Product(String id, String productImage, String idCategory, String productName, int productPrice, String productInfo, List<String> topping, Map<String, Map<String, Integer>> productSize) {
        this.id = id;
        this.productImage = productImage;
        this.idCategory = idCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.topping = topping;
        this.productSize = productSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("PRODUCT_IMAGE")
    public String getProductImage() {
        return productImage;
    }
    @PropertyName("PRODUCT_IMAGE")
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    @PropertyName("ID_CATEGORY")
    public String getIdCategory() {
        return idCategory;
    }
    @PropertyName("ID_CATEGORY")
    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }
    @PropertyName("PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }
    @PropertyName("PRODUCT_NAME")
    public void setProductName(String productName) {
        this.productName = productName;
    }
    @PropertyName("PRODUCT_PRICE")
    public int getProductPrice() {
        return productPrice;
    }
    @PropertyName("PRODUCT_PRICE")
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    @PropertyName("PRODUCT_INFO")
    public String getProductInfo() {
        return productInfo;
    }
    @PropertyName("PRODUCT_INFO")
    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }
    @PropertyName("TOPPING")
    public List<String> getTopping() {
        return topping;
    }
    @PropertyName("TOPPING")
    public void setTopping(List<String> topping) {
        this.topping = topping;
    }
    @PropertyName("SIZE")
    public Map<String, Map<String, Integer>> getProductSize() {
        return productSize;
    }
    @PropertyName("SIZE")
    public void setProductSize(Map<String, Map<String, Integer>> productSize) {
        this.productSize = productSize;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productImage='" + productImage + '\'' +
                ", idCategory='" + idCategory + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productInfo='" + productInfo + '\'' +
                ", topping=" + topping +
                ", productSize=" + productSize +
                '}';
    }
}