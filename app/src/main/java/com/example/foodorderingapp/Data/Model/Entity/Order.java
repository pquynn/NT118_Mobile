package com.example.foodorderingapp.Data.Model.Entity;

import com.google.firebase.firestore.DocumentId;

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
    private Date createOn;
    private String idCoupon;
    private Map<String, OrderItem> orderItem;
}
