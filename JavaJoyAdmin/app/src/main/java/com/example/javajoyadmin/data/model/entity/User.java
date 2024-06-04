package com.example.javajoyadmin.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

public class User {
    private String userName;
    private String phone;

    @DocumentId
    private String id;

    private String idLogin;
    private String gender;
    private int role;

    public User(){}

    public User(String userName, String phone, String id, String idLogin, String gender, int role) {
        this.userName = userName;
        this.phone = phone;
        this.id = id;
        this.idLogin = idLogin;
        this.gender = gender;
        this.role = role;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("USER_NAME")
    public String getUserName() {
        return userName;
    }

    @PropertyName("PHONE")
    public String getPhone() {
        return phone;
    }


    @PropertyName("ID_LOGIN")
    public String getIdLogin() {
        return idLogin;
    }
    @PropertyName("USER_NAME")
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @PropertyName("PHONE")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @PropertyName("ID_LOGIN")
    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    @PropertyName("GENDER")
    public String getGender() {
        return gender;
    }
    @PropertyName("GENDER")
    public void setGender(String gender) {
        this.gender = gender;
    }
    @PropertyName("ROLE")
    public int getRole() {
        return role;
    }
    @PropertyName("ROLE")
    public void setRole(int role) {
        this.role = role;
    }
}
