package com.example.foodorderingapp.Data.Model.Entity;

public class Category {
    private int resourceid;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(int resourceid, String name) {
        this.name = name;
        this.resourceid = resourceid;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }
}
