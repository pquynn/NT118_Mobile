package com.example.foodorderingapp.data.model;

import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.entity.Product;

import java.util.List;

public class CategoryList {
    private Category category;
    private List<Product> listProducts;

    public CategoryList(Category category, List<Product> listProducts) {
        this.category= category;
        this.listProducts = listProducts;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }
}

