package com.example.foodorderingapp.ui.viewmodel.customer.notification;

import androidx.lifecycle.MutableLiveData;

import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.repository.notification.INotificationRepository;
import com.example.foodorderingapp.data.repository.notification.NotificationRepository;

import java.util.List;

public class NotificationViewModel {
    private String recipientId;
    private int recipientType;
    private NotificationRepository notificationRepository;
    private MutableLiveData<List<Notification>> notiListMutableLiveData = new MutableLiveData<>();

    public NotificationViewModel(String recipientId, int recipientType) {
        this.recipientId = recipientId;
        this.recipientType = recipientType;
        notificationRepository = new NotificationRepository();
    }

    public MutableLiveData<List<Notification>> getNotiListMutableLiveData() {
        loadNotification(recipientId, recipientType);
        return notiListMutableLiveData;
    }

    public void loadNotification(String recipientId, int recipientType){
        notificationRepository.getNotificationByRecipientIdAndType(recipientId, recipientType, new INotificationRepository.NotificationListCallback() {
            @Override
            public void onListLoaded(List<Notification> notificationList) {
                notiListMutableLiveData.setValue(notificationList);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public void reloadData(){
        loadNotification(recipientId, recipientType);
    }
}
