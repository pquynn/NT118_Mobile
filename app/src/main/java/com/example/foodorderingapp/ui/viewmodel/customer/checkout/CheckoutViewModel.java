package com.example.foodorderingapp.ui.viewmodel.customer.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.Map;

public class CheckoutViewModel extends ViewModel {
    private String userId;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context;

    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> totalPrice = new MutableLiveData<>();

    private OrderRepository orderRepository = new OrderRepository();

    public CheckoutViewModel(String userId, Context context){
        this.userId = userId;
        this.context = context;
        orderRepository = new OrderRepository();
    }

    public LiveData<Order> getOrderMutableLiveData() {
        loadOrder(userId);
        return orderMutableLiveData;
    }

    public MutableLiveData<Integer> getTotalPrice() {
        totalPrice.setValue(calculateTotalPrice(orderMutableLiveData.getValue().getOrderItem()));
        return totalPrice;
    }


    // load order from firestore
    public void loadOrder(String orderId){
        orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderMutableLiveData.setValue(order);
                totalPrice.setValue(calculateTotalPrice(order.getOrderItem()));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }


    // calculate total price
    public int calculateTotalPrice(Map<String, OrderItem> buyingProductHashMap){
        int totalPrice = 0;
        for(Map.Entry<String, OrderItem> entry : buyingProductHashMap.entrySet()){
            totalPrice += entry.getValue().getPrice() * entry.getValue().getQuantity();
        }
        return totalPrice;
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
