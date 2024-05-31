package com.example.javajoyadmin.data.model.entity;

public class Topping {
    private String nameTopping;
    private int priceTopping;

    public Topping(){}

    public Topping(String name, int price) {
        this.nameTopping = name;
        this.priceTopping = price;
    }

    public String getNameTopping() {
        return nameTopping;
    }

    public void setNameTopping(String nameTopping) {
        this.nameTopping = nameTopping;
    }

    public int getPriceTopping() {
        return priceTopping;
    }

    public void setPriceTopping(int priceTopping) {
        this.priceTopping = priceTopping;
    }
}
