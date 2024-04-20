package com.example.foodorderingapp.domain;

public class Topping_SizeDomain {

    private String nameTopping;
    private String priceTopping;

    public Topping_SizeDomain(String name, String price) {
        this.nameTopping = name;
        this.priceTopping = price;
    }

    public String getNameTopping() {
        return nameTopping;
    }

    public void setNameTopping(String nameTopping) {
        this.nameTopping = nameTopping;
    }

    public String getPriceTopping() {
        return priceTopping;
    }

    public void setPriceTopping(String priceTopping) {
        this.priceTopping = priceTopping;
    }
}
