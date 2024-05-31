package com.example.javajoyadmin.data.model.entity;

import java.util.Date;

public class Coupon {
    private String idCoupon;
    private String couponName;
    private Date validFrom;
    private Date validTo;
    private double discountValue;
    private double minOrder;
    private double quantity;
    private String description;

    public Coupon(String idCoupon, String couponName, Date validFrom, Date validTo, double discountValue, double minOrder, double quantity, String description) {
        this.idCoupon = idCoupon;
        this.couponName = couponName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.discountValue = discountValue;
        this.minOrder = minOrder;
        this.quantity = quantity;
        this.description = description;
    }
    public Coupon(){
    }

    public Coupon(String couponName, Date validFrom, String description){
        this.couponName = couponName;
        this.validFrom = validFrom;
        this.description = description;
    }

    public String getIdCoupon() {
        return idCoupon;
    }

    public Date getValidTo() {
        return validTo;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public double getMinOrder() {
        return minOrder;
    }

    public double getQuantity() {
        return quantity;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getDescription() {
        return description;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public void setIdCoupon(String idCoupon) {
        this.idCoupon = idCoupon;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public void setMinOrder(double minOrder) {
        this.minOrder = minOrder;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "idCoupon='" + idCoupon + '\'' +
                ", couponName='" + couponName + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", discountValue=" + discountValue +
                ", minOrder=" + minOrder +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
