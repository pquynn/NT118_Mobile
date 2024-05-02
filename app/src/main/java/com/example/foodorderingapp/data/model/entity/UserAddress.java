package com.example.foodorderingapp.data.model.entity;

import com.google.firebase.firestore.PropertyName;

public class UserAddress {
    @PropertyName("RECIPIENT_NAME")
    private String recipientName;
    @PropertyName("ADDRESS_DETAIL")
    private String addressDetail;
    @PropertyName("CITY")
    private String city;
    @PropertyName("DISTRICT")
    private String district;
    @PropertyName("WARD")
    private String ward;
    @PropertyName("RECIPIENT_PHONE")
    private String recipientPhone;
    @PropertyName("ID_USER")
    private String idUser;
//    add database attributes later


    public UserAddress() {
        addressDetail = " ";
        ward = " ";
        district = " ";
        city = " ";
    }

    public UserAddress(String recipientName, String addressDetail, String city, String district, String ward, String recipientPhone, String idUser) {
        this.recipientName = recipientName;
        this.addressDetail = addressDetail;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.recipientPhone = recipientPhone;
        this.idUser = idUser;
    }

    public String getAllAddress(){
        if(addressDetail.equals("null")) addressDetail = " ";
        if(ward.equals("null")) ward = " ";
        if(district.equals("null")) district = " ";
        if(city.equals("null")) city = " ";
        return addressDetail +", "+ ward +", "+ district +", "+ city;
    }

    @PropertyName("RECIPIENT_NAME")
    public String getRecipientName() {
        return recipientName;
    }
    @PropertyName("RECIPIENT_NAME")
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    @PropertyName("ADDRESS_DETAIL")
    public String getAddressDetail() {
        return addressDetail;
    }
    @PropertyName("ADDRESS_DETAIL")
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    @PropertyName("CITY")
    public String getCity() {
        return city;
    }
    @PropertyName("CITY")
    public void setCity(String city) {
        this.city = city;
    }
    @PropertyName("DISTRICT")
    public String getDistrict() {
        return district;
    }
    @PropertyName("DISTRICT")
    public void setDistrict(String district) {
        this.district = district;
    }
    @PropertyName("WARD")
    public String getWard() {
        return ward;
    }
    @PropertyName("WARD")
    public void setWard(String ward) {
        this.ward = ward;
    }
    @PropertyName("RECIPIENT_PHONE")
    public String getRecipientPhone() {
        return recipientPhone;
    }
    @PropertyName("RECIPIENT_PHONE")
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }
    @PropertyName("ID_USER")
    public String getIdUser() {
        return idUser;
    }
    @PropertyName("ID_USER")
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
