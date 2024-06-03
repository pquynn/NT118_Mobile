package com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

public class MyOrderDetailVM extends ViewModel {
    private OrderRepository orderRepository;
    private OrdersFeedbackRepository ordersFeedbackRepository;

    private Context context;
    private String orderId, userId;

    private MutableLiveData<String> orderStatusLiveDate;
    private MutableLiveData<Order> orderMutableLiveData;

    public MyOrderDetailVM(String orderId, Context context){
        orderStatusLiveDate = new MutableLiveData<>();
        orderMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.orderId = orderId;

        ordersFeedbackRepository = new OrdersFeedbackRepository();
        orderRepository = new OrderRepository();
        //loadOrderStatus(orderId);
        loadOrderDetail(orderId);
    }

    public MutableLiveData<String> getOrderStatusLiveDate(){
        loadOrderStatus(orderId);
        return orderStatusLiveDate;
    }
    //get order info
    public MutableLiveData<Order> getOrderMutableLiveData(){
        loadOrderDetail(orderId);
        return orderMutableLiveData;
    }
    private void loadOrderStatus(String orderId) {
        ordersFeedbackRepository.getOrderStatus(orderId, new OrdersFeedbackRepository.orderStatusCallback() {
            @Override
            public void loadOrderStatusSuccess(String status) {
                orderStatusLiveDate.setValue(status);
            }

            @Override
            public void loadOrderStatusError(Exception e) {
                Log.e("Error load order status", "Error load order status in MyOrderDetailVM");
            }
        });
    }
    private void loadOrderDetail(String orderId){
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                userId = order.getIdUser();
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
