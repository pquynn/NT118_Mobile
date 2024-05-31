package com.example.javajoyadmin.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserPoint {
    @DocumentId
    private String id;
    private String userId;
    private int point;
    @ServerTimestamp
    private Date pointDate;

    public UserPoint(String userId, int point, Date pointDate) {
        this.userId = userId;
        this.point = point;
        this.pointDate = pointDate;
    }

    public UserPoint() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("ID_USER")
    public String getUserId() {
        return userId;
    }
    @PropertyName("ID_USER")
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @PropertyName("POINT")
    public int getPoint() {
        return point;
    }
    @PropertyName("POINT")
    public void setPoint(int point) {
        this.point = point;
    }

    @PropertyName("DATE_RECEIVED")
    @ServerTimestamp
    public Date getPointDate() {
        return pointDate;
    }
    @PropertyName("DATE_RECEIVED")
    public void setPointDate(Date pointDate) {
        this.pointDate = pointDate;
    }
}
