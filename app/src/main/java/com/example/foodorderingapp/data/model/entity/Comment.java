package com.example.foodorderingapp.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {
    @DocumentId
    private String id;
    private String idProduct;
    private String idUser;
    private String nameUser;
    private int ratingBar;
    private String content;
    @ServerTimestamp
    private Date date;

    public Comment() {
    }

    public Comment(String idProduct, String idUser, String nameUser, int ratingBar, String content, Date date) {
        this.idProduct = idProduct;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.ratingBar = ratingBar;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

//    public String getName() {
//        return "";
//    }

    @PropertyName("ID_PRODUCT")
    public String getIdProduct() {
        return idProduct;
    }
    @PropertyName("ID_PRODUCT")
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
    @PropertyName("ID_USER")
    public String getIdUser() {
        return idUser;
    }
    @PropertyName("ID_USER")
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    @PropertyName("USER_NAME")
    public String getNameUser() {
        return nameUser;
    }
    @PropertyName("USER_NAME")
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    @PropertyName("POINT")
    public int getRatingBar() {
        return ratingBar;
    }
    @PropertyName("POINT")
    public void setRatingBar(int     ratingBar) {
        this.ratingBar = ratingBar;
    }
    @PropertyName("CONTENT")
    public String getContent() {
        return content;
    }
    @PropertyName("CONTENT")
    public void setContent(String content) {
        this.content = content;
    }
    @PropertyName("CM_DATE")
    public Date getDate() {
        return date;
    }
    @PropertyName("CM_DATE")
    public void setDate(Date date) {
        this.date = date;
    }
}