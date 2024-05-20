package com.example.foodorderingapp.ui.viewmodel.admin.refund;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.OrderItem;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Refund;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.data.repository.coupon.CouponRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.data.repository.refund.IRefundRepository;
import com.example.foodorderingapp.data.repository.refund.RefundRepository;
import com.example.foodorderingapp.data.repository.topping.ToppingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RefundViewModel extends ViewModel {
    private String orderId;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context; // Context variable

    //Live data
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<Refund> refundLiveData = new MutableLiveData<>();
    // Repository
    private OrderRepository orderRepository;
    private RefundRepository refundRepository;


    // Constructor
    public RefundViewModel(String orderId, Context context) {
        this.orderId = orderId;
        this.context = context;
        orderRepository = new OrderRepository();
        refundRepository = new RefundRepository();
    }

    // Getter
    public MutableLiveData<Order> getOrderLiveData() {
        loadOrder();
        return orderLiveData;
    }

    public MutableLiveData<Refund> getRefundLiveData() {
        loadRefund();
        return refundLiveData;
    }


// Loader

    // Load order live data by order id
    public void loadOrder(){
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    // Start: ADMIN------------------------------------
    // Load refund live data by order id
    public void loadRefund(){
        refundRepository.getRefundByOrderId(orderId, new IRefundRepository.RefundCallback() {
            @Override
            public void onRefundLoaded(Refund refund) {
                refundLiveData.setValue(refund);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    // update status of refund by refund id
    private String status = "";
    public void updateRefundStatus(String action){
        showProgressDialog("Đang xử lý...");
        if(action != null){
            if(action.equals("Chấp nhận hoàn tiền")){
                status = "Chờ hoàn tiền";
            }
            else if(action.equals("Từ chối hoàn tiền")){
                status = action;
            }
            else  if(action.equals("Đã hoàn tiền")){
                status = "Đã hoàn tiền";
            }
        }

        refundRepository.updateRefundStatusById(
                refundLiveData.getValue().getId(),
                status,
                new IRefundRepository.RefundChangedCallback() {
                    @Override
                    public void onRefundChanged() {
                        refundLiveData.getValue().setStatus(status);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        dismissProgressDialog();
                    }
                });
    }
    // End: ADMIN------------------------------------

    // method to show progress dialog
    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}