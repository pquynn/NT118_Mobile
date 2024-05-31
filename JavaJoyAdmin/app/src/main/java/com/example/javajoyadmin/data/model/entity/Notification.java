package com.example.javajoyadmin.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Notification {
    @DocumentId
    String id;
    String idOrder;
    String idRecipient;
    String status;
    int recipientType;
    String title;
    String content;
    @ServerTimestamp
    Date date;

    public Notification() {
    }

    public Notification(String id, String idOrder, String idRecipient, String status, int recipientType, String title, String content, Date date) {
        this.id = id;
        this.idOrder = idOrder;
        this.idRecipient = idRecipient;
        this.status = status;
        this.recipientType = recipientType;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("ID_ORDER")
    public String getIdOrder() {
        return idOrder;
    }
    @PropertyName("ID_ORDER")
    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }
    @PropertyName("ID_RECIPIENT")
    public String getIdRecipient() {
        return idRecipient;
    }
    @PropertyName("ID_RECIPIENT")
    public void setIdRecipient(String idRecipient) {
        this.idRecipient = idRecipient;
    }
    @PropertyName("STATUS")
    public String getStatus() {
        return status;
    }
    @PropertyName("STATUS")
    public void setStatus(String status) {
        this.status = status;
    }
    @PropertyName("RECIPIENT_TYPE")
    public int getRecipientType() {
        return recipientType;
    }
    @PropertyName("RECIPIENT_TYPE")
    public void setRecipientType(int recipientType) {
        this.recipientType = recipientType;
    }
    @PropertyName("TITLE")
    public String getTitle() {
        return title;
    }
    @PropertyName("TITLE")
    public void setTitle(String title) {
        this.title = title;
    }
    @PropertyName("CONTENT")
    public String getContent() {
        return content;
    }
    @PropertyName("CONTENT")
    public void setContent(String content) {
        this.content = content;
    }

    @ServerTimestamp
    @PropertyName("DATE")
    public Date getDate() {
        return date;
    }
    @PropertyName("DATE")
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", idRecipient='" + idRecipient + '\'' +
                ", status='" + status + '\'' +
                ", recipientType=" + recipientType +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
