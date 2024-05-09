package com.example.foodorderingapp.Data.Model.Entity;

public class Category {
    private String id;

    private String imgCategory;
    private String nameCategory;

    public Category(String imgCategory, String nameCategory) {
        this.imgCategory = imgCategory;
        this.nameCategory = nameCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
