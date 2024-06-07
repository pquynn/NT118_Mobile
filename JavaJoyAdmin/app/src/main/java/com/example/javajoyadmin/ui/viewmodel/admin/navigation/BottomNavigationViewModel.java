package com.example.javajoyadmin.ui.viewmodel.admin.navigation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javajoyadmin.data.repository.notification.INotificationRepository;
import com.example.javajoyadmin.data.repository.notification.NotificationRepository;
import com.example.javajoyadmin.data.repository.order.IOrderRepository;
import com.example.javajoyadmin.data.repository.order.OrderRepository;

public class BottomNavigationViewModel extends ViewModel {
    private String userId = "";
//    private MutableLiveData<Integer> productCartQuantityLiveData;
    private MutableLiveData<Integer> unreadNotiCountLiveData;
//    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;

    public BottomNavigationViewModel(String userId){
        this.userId = userId;
//        orderRepository = new OrderRepository();
        notificationRepository = new NotificationRepository();
//        productCartQuantityLiveData = new MutableLiveData<>();
        unreadNotiCountLiveData = new MutableLiveData<>();
    }

//    public LiveData<Integer> getProductCartQuantityLiveData() {
//        loadProductCartQuantity();
//        return productCartQuantityLiveData;
//    }

//    public void setProductCartQuantityLiveData(int quantity){
//        productCartQuantityLiveData.setValue(quantity);
//    }

    public MutableLiveData<Integer> getUnreadNotiCountLiveData() {
        loadUnreadNotiCount();
        return unreadNotiCountLiveData;
    }

//    public void loadProductCartQuantity(){
//        orderRepository.calculateTotalProductCart(userId, new IOrderRepository.IntegerCallback() {
//            @Override
//            public void onLoaded(int intNumb) {
//                productCartQuantityLiveData.setValue(intNumb);
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//    }

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
