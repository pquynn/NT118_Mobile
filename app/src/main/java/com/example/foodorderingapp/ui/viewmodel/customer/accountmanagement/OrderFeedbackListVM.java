package com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.OrderDetail;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderFeedbackListVM extends ViewModel{

    private MutableLiveData<List<OrderItem>> orderDetailListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> orderStatusLiveData = new MutableLiveData<>();
    private OrdersFeedbackRepository repository = new OrdersFeedbackRepository();
    private Context context;
//    private OrdersFeedbackRepository repositoryStatus = new OrdersFeedbackRepository();
    public OrderFeedbackListVM(String orderId, Context context){
        this.context = context;
        repository.listFeedback(orderId, new OrdersFeedbackRepository.orderItemCallback() {
            @Override
            public void loadOrderItemsSuccess(List<OrderItem> orderItems) {
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

    public MutableLiveData<List<OrderItem>> getOrderDetailListLiveData() {
        return orderDetailListLiveData;
    }

    public MutableLiveData<String> getOrderStatusLiveData() {
        return orderStatusLiveData;
    }
}
