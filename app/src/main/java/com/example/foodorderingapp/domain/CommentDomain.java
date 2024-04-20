package com.example.foodorderingapp.domain;

import android.widget.RatingBar;

import java.util.Date;

public class CommentDomain {

    private String name;
    private float ratingBar;
    private String content;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CommentDomain(String name, float ratingBar, String content, Date date){
        this.name = name;
        this.ratingBar = ratingBar;
        this.content = content;
        this.date = date;
    }


}
