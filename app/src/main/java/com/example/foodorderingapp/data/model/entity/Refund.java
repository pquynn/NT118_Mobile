package com.example.foodorderingapp.data.model.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.Date;
import java.util.Map;

public class Refund {
    @DocumentId
    private String id;
    private String idOrder;
    private String status;
    private int totalMoney;
    private Date dateProcessed;
    private Date dateRequested;
    private Map<String, RefundItem> refundItemMap;

    public Refund(){}

    public Refund(String id, String idOrder, String status, int totalMoney, Date dateProcessed, Date dateRequested, Map<String, RefundItem> refundItemMap) {
        this.id = id;
        this.idOrder = idOrder;
        this.status = status;
        this.totalMoney = totalMoney;
        this.dateProcessed = dateProcessed;
        this.dateRequested = dateRequested;
        this.refundItemMap = refundItemMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("ID_ORDER")
    public String getIdOrder() {
        return idOrder;
    }
    @PropertyName("ID_ORDER")
    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }
    @PropertyName("STATUS")
    public String getStatus() {
        return status;
    }
    @PropertyName("STATUS")
    public void setStatus(String status) {
        this.status = status;
    }
    @PropertyName("TOTAL_MONEY")
    public int getTotalMoney() {
        return totalMoney;
    }
    @PropertyName("TOTAL_MONEY")
    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
    @PropertyName("DATE_PROCESSED")
    public Date getDateProcessed() {
        return dateProcessed;
    }
    @PropertyName("DATE_PROCESSED")
    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
    @PropertyName("DATE_REQUESTED")
    public Date getDateRequested() {
        return dateRequested;
    }
    @PropertyName("DATE_REQUESTED")
    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }
    @PropertyName("REFUND_ITEM")
    public Map<String, RefundItem> getRefundItemMap() {
        return refundItemMap;
    }
    @PropertyName("REFUND_ITEM")
    public void setRefundItemMap(Map<String, RefundItem> refundItemMap) {
        this.refundItemMap = refundItemMap;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "id='" + id + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", status='" + status + '\'' +
                ", totalMoney=" + totalMoney +
                ", dateProcessed=" + dateProcessed +
                ", dateRequested=" + dateRequested +
                ", refundItemMap=" + refundItemMap +
                '}';
    }
}
