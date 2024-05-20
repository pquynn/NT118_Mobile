package com.example.foodorderingapp.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

public class AdminOrderDetailVM extends ViewModel {
    private OrderRepository orderRepository;
    private String orderID;
    private MutableLiveData<Order> orderMutableLiveData;

    public AdminOrderDetailVM(String orderId) {
        this.orderID = orderId;
        getData(orderID);
    }

    public void updateOrderStatusByID(String orderID, String status, Context context) {
        orderRepository.updateOrderStatusById(orderID, status, new IOrderRepository.OrderChangedCallback() {
            @Override
            public void onOrderChanged() {
                Toast.makeText(context, "Update successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "Update failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(String orderId) {
        orderMutableLiveData = new MutableLiveData<>();
        orderRepository = new OrderRepository();

        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("AdminOrderDetailVM", "Load order fail!");
            }
        });
    }

    public MutableLiveData<Order> getOrder() {
        getData(orderID);
        return orderMutableLiveData;
    }
}
