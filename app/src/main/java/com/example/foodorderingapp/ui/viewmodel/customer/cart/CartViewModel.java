package com.example.foodorderingapp.viewmodel.cart;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.ui.adapter.CartAdapter;

import java.util.HashMap;
import java.util.Map;

public class CartViewModel extends ViewModel {
    private String userId;
    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> totalPrice = new MutableLiveData<>();

    private OrderRepository orderRepository = new OrderRepository();

    public CartViewModel(String userId){
        this.userId = userId;
    }

    public MutableLiveData<String> getTotalPrice() {
        totalPrice.setValue(String.valueOf(calculateTotalPrice(orderMutableLiveData.getValue())));
        return totalPrice;
    }

    public LiveData<Order> getOrderMutableLiveData() {
        loadOrder(userId);
        return orderMutableLiveData;
    }

    public void loadOrder(String orderId){
        orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {
                if(errorMessage == "Order not found"){
                    //todo: show empty cart
                }
            }
        });
    }



    // calculate total price
    public int calculateTotalPrice(Order order){
        int totalPrice = 0;
        for(Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()){
            totalPrice += entry.getValue().getPrice();
        }
        return totalPrice;
    }




}
