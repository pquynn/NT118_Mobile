package com.example.foodorderingapp.data.model.entity;

public class Topping {

    private String nameTopping;
    private String priceTopping;

    public Topping(){}

    public Topping(String name, String price) {
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
