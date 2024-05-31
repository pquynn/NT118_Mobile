package com.example.javajoyadmin.ui.viewmodel.admin.notification;

import androidx.lifecycle.MutableLiveData;

import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.data.repository.notification.INotificationRepository;
import com.example.javajoyadmin.data.repository.notification.NotificationRepository;

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

    public void updateNotificationStatus(String notiId){
        notificationRepository.updateNotificationStatus("read", notiId);
    }
}
