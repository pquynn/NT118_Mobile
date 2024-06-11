package com.example.javajoyadmin.ui.viewmodel.admin.order;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javajoyadmin.data.model.entity.Order;
import com.example.javajoyadmin.data.repository.order.IOrderRepository;
import com.example.javajoyadmin.data.repository.order.OrderRepository;

import java.util.List;

public class AdminOrderVM extends ViewModel {
    OrderRepository repository = new OrderRepository();
    MutableLiveData<List<Order>> orderListLiveData = new MutableLiveData<>();
    String orderStatus;
    public AdminOrderVM (String status){
        orderStatus = status;
    }

    private void getOrderList(){
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
        getOrderList();
        return orderListLiveData;
    }
}
