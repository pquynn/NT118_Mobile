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

    private MutableLiveData<ArrayList<OrderDetail>> orderDetailListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> orderStatusLiveData = new MutableLiveData<>();
    private OrdersFeedbackRepository repository = new OrdersFeedbackRepository();
//    private OrdersFeedbackRepository repositoryStatus = new OrdersFeedbackRepository();
    public OrderFeedbackListVM(String orderId){
        repository.listFeedback(orderId, new OrdersFeedbackRepository.orderItemCallback() {
            @Override
            public void loadOrderItemsSuccess(ArrayList<OrderDetail> orderItems) {
                orderDetailListLiveData.setValue(orderItems);
            }

            @Override
            public void loadOrderItemsError(Exception e) {
//                Log.d(TAG, "onErrorOrderList: " + e.getMessage());
                Log.d(TAG, "onErrorOrderList: in listFeedback in VM");
            }

        });
        repository.getOrderStatus(orderId, new OrdersFeedbackRepository.orderStatusCallback() {
            @Override
            public void loadOrderStatusSuccess(String status) {
                orderStatusLiveData.setValue(status);
            }

            @Override
            public void loadOrderStatusError(Exception e) {
                Log.d(TAG, "onErrorStatus: " + e.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<OrderDetail>> getOrderDetailListLiveData() {
        return orderDetailListLiveData;
    }

    public MutableLiveData<String> getOrderStatusLiveData() {
        return orderStatusLiveData;
    }
}
