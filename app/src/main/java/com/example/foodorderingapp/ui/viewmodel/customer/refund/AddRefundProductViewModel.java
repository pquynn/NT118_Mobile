package com.example.foodorderingapp.ui.viewmodel.customer.refund;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class AddRefundProductViewModel extends ViewModel {
    private String orderId;
    private ProgressDialog progressDialog;
    private Context context;

    // Live data
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> selectedOrderItemId = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> selectedQuantity = new MutableLiveData<>();
    // Repository
    private OrderRepository orderRepository;

    // Constructor
    public AddRefundProductViewModel(String orderId, Context context) {
        this.orderId = orderId;
        this.context = context;
        this.orderRepository = new OrderRepository();
        selectedOrderItemId.setValue(new ArrayList<>());
        selectedQuantity.setValue(new ArrayList<>());
    }

    // Getter
    public MutableLiveData<Order> getOrderLiveData() {
        loadOrder();
        return orderLiveData;
    }

    public MutableLiveData<List<String>> getSelectedOrderItemId() {
        return selectedOrderItemId;
    }

    public MutableLiveData<List<Integer>> getSelectedQuantity() {
        return selectedQuantity;
    }

    // Load order live data by order id
    public void loadOrder() {
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
