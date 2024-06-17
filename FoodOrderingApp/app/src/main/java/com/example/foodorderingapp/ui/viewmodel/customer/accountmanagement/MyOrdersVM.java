package com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.List;

public class MyOrdersVM extends ViewModel {
    OrderRepository repository = new OrderRepository();
    MutableLiveData<List<Order>> orderListLiveData = new MutableLiveData<>();
    String userId;
    String orderStatus;
    public MyOrdersVM (String userId, String orderStatus){
        this.userId = userId;
        this.orderStatus = orderStatus;

    }
    public MutableLiveData<List<Order>> getOrderListLiveData(){
        loadOrder(userId, orderStatus);
        return orderListLiveData;
    }

    private void loadOrder(String userId, String orderStatus){
        repository.getOrderListByStatusAndUserId(userId, orderStatus, new IOrderRepository.OrderListCallback() {
            @Override
            public void onOrderListLoaded(List<Order> orderList) {
                orderListLiveData.setValue(orderList);
            }

            @Override
            public void onError(String errorMessage) {
                orderListLiveData.setValue(null);
                Log.e("MyOrdersVMError", "Error load my orders by status in MyOrdersVM");
            }
        });
    }

}
