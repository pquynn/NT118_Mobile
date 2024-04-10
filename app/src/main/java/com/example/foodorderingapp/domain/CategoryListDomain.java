package com.example.foodorderingapp.domain;

import java.util.List;

public class CategoryListDomain {
    private String nameCategory;
    private List<ProductSearchDomain> listProducts;

    public CategoryListDomain(String nameCategory, List<ProductSearchDomain> listProducts) {
        this.nameCategory = nameCategory;
        this.listProducts = listProducts;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<ProductSearchDomain> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<ProductSearchDomain> listProducts) {
        this.listProducts = listProducts;
    }
}

