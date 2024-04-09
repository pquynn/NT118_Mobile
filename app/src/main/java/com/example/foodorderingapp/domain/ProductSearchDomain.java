package com.example.foodorderingapp.domain;

public class ProductSearchDomain {
    private int image;
    private String name;
    private String price;

    public ProductSearchDomain(int img, String Name, String Price) {
        this.image = img;
        this.name = Name;
        this.price = Price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
