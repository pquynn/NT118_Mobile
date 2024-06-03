package com.example.javajoyadmin.data.repository.refund;

import com.example.javajoyadmin.data.model.entity.Refund;

public interface IRefundRepository {

    // Get refund document by order id
    void getRefundByOrderId(String orderId, RefundCallback callback);

    // Create new refund document by order id and refund item
    void createRefund(Refund refund, RefundCallback callback);

    // Update refund status by id
    void updateRefundStatusById(String refundId, String status, RefundChangedCallback callback);

    interface RefundCallback {
        void onRefundLoaded(Refund refund);
        void onError(String errorMessage);
    }

    interface RefundChangedCallback {
        void onRefundChanged();
        void onError(String errorMessage);
    }
}
