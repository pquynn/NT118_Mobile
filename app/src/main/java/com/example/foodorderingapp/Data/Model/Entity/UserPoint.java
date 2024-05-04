package com.example.foodorderingapp.data.model.entity;

public class UserPoint {
    private int point;
    private String point_date; //date_received/date_of_use;

    public UserPoint(int p, String dateP){
        this.point = p;
        this.point_date = dateP;
    }

    public int getPoint(){
        return point;
    }
    public String getPoint_date(){
        return point_date;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setPoint_date(String point_date) {
        this.point_date = point_date;
    }
}
