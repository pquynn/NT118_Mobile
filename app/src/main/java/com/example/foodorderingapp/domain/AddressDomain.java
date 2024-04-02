package com.example.foodorderingapp.domain;

public class AddressDomain {
    private String address;
//    add database attributes later

    public AddressDomain(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
