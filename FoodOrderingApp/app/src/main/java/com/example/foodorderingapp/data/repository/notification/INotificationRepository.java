package com.example.foodorderingapp.data.repository.notification;

import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;

import java.util.List;

public interface INotificationRepository {
    void getNotificationByRecipientIdAndType(String idRecipient, int recipientType, NotificationListCallback callback);

    interface NotificationListCallback {
        void onListLoaded(List<Notification> notificationList);
        void onError(String errorMessage);
    }
}
