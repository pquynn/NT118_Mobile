package com.example.foodorderingapp.data.model.entity;

import java.util.Date;

public class Notification {
    String type;
    String content;
    Date date;

    public Notification(String type, String content, Date date) {
        this.type = type;
        this.content = content;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
