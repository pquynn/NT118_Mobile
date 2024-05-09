package com.example.foodorderingapp.data.model.entity;

public class Topping {
    private String id;
    private String nameTopping;
    private String priceTopping;

    public Topping(){}

    public Topping(String name, String price) {
        this.nameTopping = name;
        this.priceTopping = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
