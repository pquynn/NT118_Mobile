package com.example.foodorderingapp.Data.Model.Entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;


public class Order {
    @DocumentId
    private String id;
    private String idUser;
    private String address;
    private int totalPrice;
    private int totalProduct;
    private int point;
    private String payment;
    private String status;
    private int deliveryCost;
    private int orderPrice;
    @ServerTimestamp
    private Date createOn;
    private String idCoupon;
    private Map<String, OrderItem> orderItemMap;

    public Order(){}
    public Order(String idUser, String address, int totalPrice, int totalProduct, int point, String payment, String status, int deliveryCost, int orderPrice, Date createOn, String idCoupon, Map<String, OrderItem> orderItem) {
        this.idUser = idUser;
        this.address = address;
        this.totalPrice = totalPrice;
        this.totalProduct = totalProduct;
        this.point = point;
        this.payment = payment;
        this.status = status;
        this.deliveryCost = deliveryCost;
        this.orderPrice = orderPrice;
        this.createOn = createOn;
        this.idCoupon = idCoupon;
        this.orderItemMap = orderItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("ID_USER")
    public String getIdUser() {
        return idUser;
    }
    @PropertyName("ID_USER")
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @PropertyName("ADDRESS")
    public String getAddress() {
        return address;
    }
    @PropertyName("ADDRESS")
    public void setAddress(String address) {
        this.address = address;
    }

    @PropertyName("TOTAL_PRICE")
    public int getTotalPrice() {
        return totalPrice;
    }
    @PropertyName("TOTAL_PRICE")
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @PropertyName("TOTAL_PRODUCT")
    public int getTotalProduct() {
        return totalProduct;
    }
    @PropertyName("TOTAL_PRODUCT")
    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }
    @PropertyName("POINT")
    public int getPoint() {
        return point;
    }
    @PropertyName("POINT")
    public void setPoint(int point) {
        this.point = point;
    }

    @PropertyName("PAYMENT")
    public String getPayment() {
        return payment;
    }
    @PropertyName("PAYMENT")
    public void setPayment(String payment) {
        this.payment = payment;
    }

    @PropertyName("STATUS")
    public String getStatus() {
        return status;
    }
    @PropertyName("STATUS")
    public void setStatus(String status) {
        this.status = status;
    }
    @PropertyName("DELIVERY_COST")
    public int getDeliveryCost() {
        return deliveryCost;
    }
    @PropertyName("DELIVERY_COST")
    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @PropertyName("ORDER_PRICE")
    public int getOrderPrice() {
        return orderPrice;
    }
    @PropertyName("ORDER_PRICE")
    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    @PropertyName("CREATE_ON")
    @ServerTimestamp
    public Date getCreateOn() {
        return createOn;
    }
    @PropertyName("CREATE_ON")
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    @PropertyName("ID_COUPON")
    public String getIdCoupon() {
        return idCoupon;
    }
    @PropertyName("ID_COUPON")
    public void setIdCoupon(String idCoupon) {
        this.idCoupon = idCoupon;
    }

    @PropertyName("ORDER_ITEM")
    public Map<String, OrderItem> getOrderItem() {
        return orderItemMap;
    }

    @PropertyName("ORDER_ITEM")
    public void setOrderItem(Map<String, OrderItem> orderItemMap) {
        this.orderItemMap = orderItemMap;
    }

    // get order item in hashmap by orderItemId
    public OrderItem getOrderItemElementById(String orderItemId){
        return this.orderItemMap.get(orderItemId);
    }

    // set (insert and update) order item in hashmap by orderItemId and new order item
    public void setOrderItemElementById(String orderItemId, OrderItem orderItemElement){
        this.orderItemMap.put(orderItemId, orderItemElement);
    }

    // delete order item in hashmap by orderItemId
    public void deleteOrderItemElementById(String orderItemId){
        this.orderItemMap.remove(orderItemId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", idUser='" + idUser + '\'' +
                ", address='" + address + '\'' +
                ", totalPrice=" + totalPrice +
                ", totalProduct=" + totalProduct +
                ", point=" + point +
                ", payment='" + payment + '\'' +
                ", status='" + status + '\'' +
                ", deliveryCost=" + deliveryCost +
                ", orderPrice=" + orderPrice +
                ", createOn=" + createOn +
                ", idCoupon='" + idCoupon + '\'' +
                ", orderItem=" + orderItemMap +
                '}';
    }
}

