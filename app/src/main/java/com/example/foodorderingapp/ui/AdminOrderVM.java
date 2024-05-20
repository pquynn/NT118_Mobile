package com.example.foodorderingapp.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.List;

public class AdminOrderVM extends ViewModel {
    OrderRepository repository = new OrderRepository();
    MutableLiveData<List<Order>> orderListLiveData = new MutableLiveData<>();
    public AdminOrderVM (String orderStatus){
        repository.getOrderListByStatus(orderStatus, new IOrderRepository.OrderListCallback() {
            @Override
            public void onOrderListLoaded(List<Order> orderList) {
                orderListLiveData.setValue(orderList);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("AdminOrderVM", "Error load orders");
            }
        });
    }

    public MutableLiveData<List<Order>> getOrderListLiveData(){
        return orderListLiveData;
    }
}
