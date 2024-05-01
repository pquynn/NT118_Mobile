package com.example.foodorderingapp.viewmodel.checkout;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

public class CheckoutViewModel extends ViewModel {
    String orderId;
    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<Order>();

    private OrderRepository orderRepository = new OrderRepository();

    private MutableLiveData<String> test;

    public CheckoutViewModel(String orderId){

        this.orderId = orderId;
        loadOrder(orderId);
    }

    public LiveData<Order> getOrderMutableLiveData() {
            loadOrder(orderId);

        return orderMutableLiveData;
    }

    public void loadOrder(String orderId){
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }


    public MutableLiveData<String> getTest() {
        if (test == null){
            test = new MutableLiveData<>();
            test.setValue("a");
        }
        return test;
    }



}
