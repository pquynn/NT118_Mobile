package com.example.foodorderingapp.ui.viewmodel.customer.notification;

import android.util.Log;

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
    private MutableLiveData<Integer> unreadNotiCountLiveData = new MutableLiveData<>();

    public NotificationViewModel(String recipientId, int recipientType) {
        this.recipientId = recipientId;
        this.recipientType = recipientType;
        notificationRepository = new NotificationRepository();
    }

    public MutableLiveData<List<Notification>> getNotiListMutableLiveData() {
        loadNotification(recipientId, recipientType);
        return notiListMutableLiveData;
    }

    public MutableLiveData<Integer> getUnreadNotiCountLiveData() {
        loadUnreadNotiCount();
        return unreadNotiCountLiveData;
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

    // method to load noti ietm count
    public void loadUnreadNotiCount(){
        notificationRepository.calculateUnreadNoti(recipientId, new INotificationRepository.IntegerCallback() {
            @Override
            public void onLoad(int count) {
                Log.d("badge", "onLoad: " + count);
                unreadNotiCountLiveData.setValue(count);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public void reloadData(){
        loadNotification(recipientId, recipientType);
        loadUnreadNotiCount();
    }

    public void updateNotificationStatus(String notiId){
        notificationRepository.updateNotificationStatus("read", notiId);
    }
}
