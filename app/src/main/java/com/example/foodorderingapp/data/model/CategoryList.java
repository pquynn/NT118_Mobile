package com.example.foodorderingapp.data.model;

import com.example.foodorderingapp.data.model.entity.Product;

import java.util.List;

public class CategoryList {
    private String nameCategory;
    private List<Product> listProducts;

    public CategoryList(String nameCategory, List<Product> listProducts) {
        this.nameCategory = nameCategory;
        this.listProducts = listProducts;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }
}

