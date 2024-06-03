package com.example.foodorderingapp.ui.viewmodel.customer.navigation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.notification.INotificationRepository;
import com.example.foodorderingapp.data.repository.notification.NotificationRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.Map;

public class BottomNavigationViewModel extends ViewModel {
    private String userId = "";
    private MutableLiveData<Integer> productCartQuantityLiveData;
    private MutableLiveData<Integer> unreadNotiCountLiveData;
    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;

    public BottomNavigationViewModel(String userId){
        this.userId = userId;
        orderRepository = new OrderRepository();
        notificationRepository = new NotificationRepository();
        productCartQuantityLiveData = new MutableLiveData<>();
        unreadNotiCountLiveData = new MutableLiveData<>();
    }

    public LiveData<Integer> getProductCartQuantityLiveData() {
        loadProductCartQuantity();
        return productCartQuantityLiveData;
    }

    public void setProductCartQuantityLiveData(int quantity){
        productCartQuantityLiveData.setValue(quantity);
    }

    public MutableLiveData<Integer> getUnreadNotiCountLiveData() {
        loadUnreadNotiCount();
        return unreadNotiCountLiveData;
    }

    public void loadProductCartQuantity(){
        orderRepository.calculateTotalProductCart(userId, new IOrderRepository.IntegerCallback() {
            @Override
            public void onLoaded(int intNumb) {
                productCartQuantityLiveData.setValue(intNumb);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public void loadUnreadNotiCount(){
        notificationRepository.calculateUnreadNoti(userId, new INotificationRepository.IntegerCallback() {
            @Override
            public void onLoad(int count) {
                unreadNotiCountLiveData.setValue(count);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
