package com.example.javajoyadmin.data.model;

public class PaymentMethod {
    private String methodName;
    private int methodImg;

    public PaymentMethod(String methodName, int methodImg) {
        this.methodName = methodName;
        this.methodImg = methodImg;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getMethodImg() {
        return methodImg;
    }

    public void setMethodImg(int methodImg) {
        this.methodImg = methodImg;
    }
}
