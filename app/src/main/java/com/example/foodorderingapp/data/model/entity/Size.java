package com.example.foodorderingapp.data.model.entity;

public class Size {
    private String nameSize;
    private int priceSize;
    private int quantitySize;

    public Size(String nameSize, int priceSize, int quantitySize) {
        this.nameSize = nameSize;
        this.priceSize = priceSize;
        this.quantitySize = quantitySize;
    }

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public int getPriceSize() {
        return priceSize;
    }

    public void setPriceSize(int priceSize) {
        this.priceSize = priceSize;
    }

    public int getQuantitySize() {
        return quantitySize;
    }

    public void setQuantitySize(int quantitySize) {
        this.quantitySize = quantitySize;
    }
    @Override
    public String toString() {
        return "Size{" +
                "nameSize='" + nameSize + '\'' +
                ", priceSize=" + priceSize +
                ", quantitySize=" + quantitySize +
                '}';
    }
}