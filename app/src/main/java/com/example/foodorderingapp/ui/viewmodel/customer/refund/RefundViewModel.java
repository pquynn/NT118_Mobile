package com.example.foodorderingapp.ui.viewmodel.customer.refund;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.Refund;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.data.repository.refund.IRefundRepository;
import com.example.foodorderingapp.data.repository.refund.RefundRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.refund.SendRefundSucessActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RefundViewModel extends ViewModel {
    private String orderId;
    private ProgressDialog progressDialog;
    private Context context;

    // Live data
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();

    private MutableLiveData<Refund> refundLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<String, OrderItem>> selectedOrderItemMapLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<String, RefundItem>> refundItemMapLiveData = new MutableLiveData<>();
    // Repository
    private OrderRepository orderRepository;
    private RefundRepository refundRepository;
    // Constructor
    public RefundViewModel(String orderId, Context context) {
        this.orderId = orderId;
        this.context = context;
        this.orderRepository = new OrderRepository();
        refundRepository = new RefundRepository();
        loadOrder();
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
    public MutableLiveData<Map<String, OrderItem>> getSelectedOrderItemMapLiveData(List<String> keys, Order order) {
        loadSelectedOrderItemMap(keys, order);
        return selectedOrderItemMapLiveData;
    }

    public MutableLiveData<Map<String, RefundItem>> getRefundItemMapLiveData() {
        return refundItemMapLiveData;
    }

    // Load order live data by order id
    public void loadOrder() {
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
                // Directly update selected order items map if order data is available
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });
    }
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

    // Load selected order item map
    public void loadSelectedOrderItemMap(List<String> keys, Order order) {
        Map<String, OrderItem> orderItemMap = new HashMap<>();
        Map<String, RefundItem> refundItemMap = new HashMap<>();
        if (order != null) {
            for (String key : keys) {
                OrderItem orderItem = order.getOrderItemElementById(key);
                if (orderItem != null) {
                    RefundItem refundItem = new RefundItem();
                    orderItemMap.put(key, orderItem);
                    refundItemMap.put(key, refundItem);
                }
            }
        }
        refundItemMapLiveData.setValue(refundItemMap);
        selectedOrderItemMapLiveData.setValue(orderItemMap);
    }

    // Create new refund
    public void sendRefundRequest(Map<String, RefundItem> updatedRefundItemMap) {
        showProgressDialog("Đang xử lý...");

        Map<String, RefundItem> refundItemMap = refundItemMapLiveData.getValue();
        refundItemMap.putAll(updatedRefundItemMap);

        // Upload proof images and videos to Firebase Storage and get the download URLs
        List<Task<Uri>> uploadTasks = new ArrayList<>();
        for (Map.Entry<String, RefundItem> entry : refundItemMap.entrySet()) {
            RefundItem refundItem = entry.getValue();
            Uri proofImageUri = Uri.parse(refundItem.getProofImage());
            Uri proofVideoUri = Uri.parse(refundItem.getProofVideo());

            // Upload image
            if (proofImageUri != null) {
                StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("refund_image/" + proofImageUri.getLastPathSegment());
                uploadTasks.add(imageRef.putFile(proofImageUri)
                        .continueWithTask(task -> imageRef.getDownloadUrl())
                        .addOnSuccessListener(uri -> refundItem.setProofImage(uri.toString()))
                        .addOnFailureListener(e -> Log.e("RefundViewModel", "Error uploading image", e)));
            }

            // Upload video
            if (proofVideoUri != null) {
                StorageReference videoRef = FirebaseStorage.getInstance().getReference().child("refund_video/" + proofVideoUri.getLastPathSegment());
                uploadTasks.add(videoRef.putFile(proofVideoUri)
                        .continueWithTask(task -> videoRef.getDownloadUrl())
                        .addOnSuccessListener(uri -> refundItem.setProofVideo(uri.toString()))
                        .addOnFailureListener(e -> Log.e("RefundViewModel", "Error uploading video", e)));
            }
        }

        // Wait for all uploads to complete
        Tasks.whenAll(uploadTasks)
                .addOnSuccessListener(aVoid -> {
                    // All uploads complete, create the refund request
                    createRefundRequest(refundItemMap);
                })
                .addOnFailureListener(e -> {
                    dismissProgressDialog();
                    Log.e("RefundViewModel", "Error uploading files", e);
                    // Handle the error
                });
    }

    private void createRefundRequest(Map<String, RefundItem> refundItemMap) {
        Order order = orderLiveData.getValue();
        int totalMoney = 0;
        for (Map.Entry<String, RefundItem> entry : refundItemMap.entrySet()) {
            totalMoney += entry.getValue().getMoney() * entry.getValue().getQuantity();
        }

        Refund refund = new Refund();
        String id = UUID.randomUUID().toString();
        refund.setId(id);
        refund.setIdOrder(order.getId());
        refund.setStatus("Chờ xác nhận");
        refund.setTotalMoney(totalMoney);
        refund.setDateRequested(new Date());
        refund.setDateProcessed(new Date(0));  // new Date(0) represents the epoch (January 1, 1970)
        refund.setRefundItemMap(refundItemMap);

        refundRepository.createRefund(refund, new IRefundRepository.RefundCallback() {
            @Override
            public void onRefundLoaded(Refund refund) {
                // update order status to 'hoan tien' when create refund successfully
                dismissProgressDialog();
                orderRepository.updateOrderStatusById(orderId, "Hoàn tiền", new IOrderRepository.OrderChangedCallback() {
                    @Override
                    public void onOrderChanged() {
                        Intent intent = new Intent(context, SendRefundSucessActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", orderId);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                dismissProgressDialog();
                Log.e("RefundViewModel", "Error creating refund"+ errorMessage);
                // Handle the error
            }
        });
    }


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
