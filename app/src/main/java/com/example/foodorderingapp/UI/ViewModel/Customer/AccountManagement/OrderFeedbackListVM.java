package com.example.foodorderingapp.UI.ViewModel.Customer.AccountManagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.Data.Model.OrderDetail;
import com.example.foodorderingapp.Data.Repository.AccountManagement.MyOrders.OrdersFeedbackRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderFeedbackListVM extends ViewModel{
    private String orderId;
    private MutableLiveData<ArrayList<OrderDetail>> orderDetailListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> orderStatusLiveData = new MutableLiveData<>();
    private OrdersFeedbackRepository repository = new OrdersFeedbackRepository();
    private OrdersFeedbackRepository repositoryStatus = new OrdersFeedbackRepository();
    public OrderFeedbackListVM(String orderIdGet){
        repository.listFeedback(orderIdGet, new OrdersFeedbackRepository.orderItemCallback() {
            @Override
            public void loadOrderItemsSuccess(ArrayList<OrderDetail> orderItems) {
                orderDetailListLiveData.setValue(orderItems);
            }

            @Override
            public void loadOrderItemsError(Exception e) {
                Log.d(TAG, "onErrorOrderList: " + e.getMessage());
            }

        });
        repositoryStatus.getOrderStatus(orderIdGet, new OrdersFeedbackRepository.orderStatusCallback() {
            @Override
            public void loadOrderStatusSuccess(String status) {
                orderStatusLiveData.setValue(status);
            }

            @Override
            public void loadOrderStatusError(Exception e) {
                Log.d(TAG, "onErrorStatus: " + e.getMessage());
            }
        });
        orderId = orderIdGet;
    }

    public MutableLiveData<ArrayList<OrderDetail>> getOrderDetailListLiveData() {
        return orderDetailListLiveData;
    }

    public MutableLiveData<String> getOrderStatusLiveData() {
        return orderStatusLiveData;
    }
}
