package com.example.foodorderingapp.data.model.entity;

public class UserAddress {
    private String recipientName;
    private String phone;
    private String address;
//    add database attributes later

    public UserAddress(String address, String recipientName, String phone){

        this.address = address;
        this.recipientName = recipientName;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
