package com.example.javajoyadmin.data.model;

public class User {
    private String ID;
    private String ID_Login;
    private String User_Name;
    private String Phone;
    private String Gender;

    public User(String ID, String ID_Login, String user_Name, String phone, String gender) {
        this.ID = ID;
        this.ID_Login = ID_Login;
        User_Name = user_Name;
        Phone = phone;
        Gender = gender;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_Login() {
        return ID_Login;
    }

    public void setID_Login(String ID_Login) {
        this.ID_Login = ID_Login;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
