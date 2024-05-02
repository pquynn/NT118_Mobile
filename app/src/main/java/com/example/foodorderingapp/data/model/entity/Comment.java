package com.example.foodorderingapp.data.model.entity;

import java.util.Date;

public class Comment {

    private String idProduct;
    private String idUser;
    private String nameUser;
    private float ratingBar;
    private String content;
    private Date date;

    public Comment() {
    }

    public Comment(String idProduct, String idUser, String nameUser, float ratingBar, String content, Date date) {
        this.idProduct = idProduct;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.ratingBar = ratingBar;
        this.content = content;
        this.date = date;
    }

    public String getName() {
        return "";
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
