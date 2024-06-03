package com.example.javajoyadmin.data.repository.notification;

import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.data.model.entity.Order;
import com.example.javajoyadmin.data.repository.order.IOrderRepository;

import java.util.List;

public interface INotificationRepository {
    void getNotificationByRecipientIdAndType(String idRecipient, int recipientType, NotificationListCallback callback);

    void createNotification(Notification notification);

    void updateNotificationStatus(String status, String notificationId);

    void updateNotification(Notification notification);

    void getNotificationByOrderIdAndTitle(String orderId, String title, NotificationCallback callback);

    interface NotificationListCallback {
        void onListLoaded(List<Notification> notificationList);
        void onError(String errorMessage);
    }

    interface NotificationCallback {
        void onLoaded(Notification notification);
        void onError(String errorMessage);
    }
}
