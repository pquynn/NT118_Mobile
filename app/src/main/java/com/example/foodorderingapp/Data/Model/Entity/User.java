package com.example.foodorderingapp.Data.Model.Entity;

public class User {
    private String userName;
    private String phone;

    private String id;

    private String idLogin;

    public User(String userName, String phone, String id, String idLogin) {
        this.userName = userName;
        this.phone = phone;
        this.id = id;
        this.idLogin = idLogin;
    }


    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getIdLogin() {
        return idLogin;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }
}
