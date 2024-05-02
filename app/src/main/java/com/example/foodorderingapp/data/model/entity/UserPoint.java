package com.example.foodorderingapp.data.model.entity;

public class UserPoint {
    private int point;
    private String pointDate; //date_received/date_of_use;

    public UserPoint(int p, String dateP){
        this.point = p;
        this.pointDate = dateP;
    }

    public int getPoint(){
        return point;
    }
    public String getPointDate(){
        return pointDate;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setPointDate(String point_date) {
        this.pointDate = point_date;
    }
}
