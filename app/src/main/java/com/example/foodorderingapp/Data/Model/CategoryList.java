package com.example.foodorderingapp.Data.Model;

import java.util.List;

public class CategoryList {
    private String nameCategory;
    private List<ProductSearch> listProducts;

    public CategoryList(String nameCategory, List<ProductSearch> listProducts) {
        this.nameCategory = nameCategory;
        this.listProducts = listProducts;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<ProductSearch> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<ProductSearch> listProducts) {
        this.listProducts = listProducts;
    }
}

