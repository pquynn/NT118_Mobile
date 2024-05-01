package com.example.foodorderingapp.data.model.entity;

import com.google.firebase.firestore.PropertyName;

public class RefundItem {

    private String id;
    private String describe;
    private String proofImage;
    private String proofVideo;
    private String reason;
    private int money;

    public RefundItem(){}

    public RefundItem(String id, String describe, String proofImage, String proofVideo, String reason, int money) {
        this.id = id;
        this.describe = describe;
        this.proofImage = proofImage;
        this.proofVideo = proofVideo;
        this.reason = reason;
        this.money = money;
    }

    @PropertyName("ID")
    public String getId() {
        return id;
    }
    @PropertyName("ID")
    public void setId(String id) {
        this.id = id;
    }
    @PropertyName("DESCRIBE")
    public String getDescribe() {
        return describe;
    }
    @PropertyName("DESCRIBE")
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    @PropertyName("PROOF_IMAGE")
    public String getProofImage() {
        return proofImage;
    }
    @PropertyName("PROOF_IMAGE")
    public void setProofImage(String proofImage) {
        this.proofImage = proofImage;
    }
    @PropertyName("PROOF_VIDEO")
    public String getProofVideo() {
        return proofVideo;
    }
    @PropertyName("PROOF_VIDEO")
    public void setProofVideo(String proofVideo) {
        this.proofVideo = proofVideo;
    }
    @PropertyName("REASON")
    public String getReason() {
        return reason;
    }
    @PropertyName("REASON")
    public void setReason(String reason) {
        this.reason = reason;
    }
    @PropertyName("MONEY")
    public int getMoney() {
        return money;
    }
    @PropertyName("MONEY")
    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "RefundItem{" +
                "id='" + id + '\'' +
                ", describe='" + describe + '\'' +
                ", proofImage='" + proofImage + '\'' +
                ", reason='" + reason + '\'' +
                ", money=" + money +
                '}';
    }
}
