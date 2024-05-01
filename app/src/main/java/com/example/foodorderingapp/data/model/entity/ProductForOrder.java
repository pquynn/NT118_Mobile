package com.example.foodorderingapp.data.model.entity;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.Map;

public class ProductForOrder {
    @DocumentId
    private String id;
    private String productImage;
    private String idCategory;
    private String productName;
    private int productPrice;
    private String productInfo;
    private ArrayList<String> topping;
    private Map<String, SizeForOrder> productSizeMap;

    //construct lấy id, ảnh, tên, giá, mô tả của sản phẩm là bánh
    public ProductForOrder(){}
    public ProductForOrder(String id, String productImage, String productName, int productPrice, String productInfo) {
        this.id = id;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
    }

    public ProductForOrder(String id, String productImage, String idCategory, String productName, int productPrice, String productInfo, ArrayList<String> topping, Map<String, SizeForOrder> productSize) {
        this.id = id;
        this.productImage = productImage;
        this.idCategory = idCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInfo = productInfo;
        this.topping = topping;
        this.productSizeMap = productSize;
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
    public ArrayList<String> getTopping() {
        return topping;
    }

    @PropertyName("TOPPING")
    public void setTopping(ArrayList<String> topping) {
        this.topping = topping;
    }

    @PropertyName("SIZE")
    public Map<String, SizeForOrder> getProductSizeMap() {
        return productSizeMap;
    }
    @PropertyName("SIZE")
    public void setProductSizeMap(Map<String, SizeForOrder> productSizeMap) {
        this.productSizeMap = productSizeMap;
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
                ", productSize=" + productSizeMap +
                '}';
    }
}