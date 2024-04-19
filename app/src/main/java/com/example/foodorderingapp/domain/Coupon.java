package com.example.foodorderingapp.domain;

import java.util.Date;

public class Coupon {
    private String couponName;
    private Date validFrom;
    private String description;
    public Coupon(String couponName, Date validFrom, String description){
        this.couponName = couponName;
        this.validFrom = validFrom;
        this.description = description;
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
}
