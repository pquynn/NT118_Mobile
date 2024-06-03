package com.example.foodorderingapp.data.repository.notification;

import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;

import java.util.List;

public interface INotificationRepository {
    void getNotificationByRecipientIdAndType(String idRecipient, int recipientType, NotificationListCallback callback);
    void calculateUnreadNoti(String idRecipient, IntegerCallback callback);
    void createNotification(Notification notification);

    void updateNotificationStatus(String status, String notificationId);

    void updateNotification(Notification notification);
    interface NotificationListCallback {
        void onListLoaded(List<Notification> notificationList);
        void onError(String errorMessage);
    }

    interface IntegerCallback {
        void onLoad(int count);
        void onError(String errorMessage);
    }
}
