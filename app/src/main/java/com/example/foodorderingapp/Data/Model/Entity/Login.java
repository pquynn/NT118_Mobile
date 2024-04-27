package com.example.foodorderingapp.Data.Model.Entity;

import com.google.firebase.firestore.DocumentId;

public class Login {

    @DocumentId
    private String Id;
    private String Phone;
    private String Password;

    public Login() {
        this.Id = "";
        this.Phone = "";
        this.Password = "";
    }

    public Login(String id, String phone, String password) {
        Id = id;
        Phone = phone;
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
