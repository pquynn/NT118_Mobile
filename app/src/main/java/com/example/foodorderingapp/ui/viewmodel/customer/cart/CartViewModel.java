package com.example.foodorderingapp.ui.viewmodel.customer.cart;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.Map;

public class CartViewModel extends ViewModel {
    private String userId;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context; // Context variable

    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> totalPrice = new MutableLiveData<>();

    private OrderRepository orderRepository;

    public CartViewModel(String userId, Context context){
        this.userId = userId;
        this.context = context;
        orderRepository = new OrderRepository();
    }

    public MutableLiveData<Integer> getTotalPrice() {
        totalPrice.setValue(calculateTotalPrice(orderMutableLiveData.getValue().getOrderItem()));
        return totalPrice;
    }

    public MutableLiveData<Order> getOrderMutableLiveData() {
        loadCart(userId);
        return orderMutableLiveData;
    }


    public void loadCart(String userId){
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

    // Click listeners for increase and decrease buttons
    public void onChangeQuantityButtonClick(String orderItemId, int deltaPrice) {
        // update total price
        totalPrice.setValue(totalPrice.getValue() + deltaPrice);

        // update product cart in firestore
        orderRepository.addOrUpdateProductCart(
                orderMutableLiveData.getValue().getId(),
                orderItemId,
                orderMutableLiveData.getValue().getOrderItemElementById(orderItemId),
                new IOrderRepository.OrderChangedCallback() {
                    @Override
                    public void onOrderChanged() {

                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                }
        );
    }

    // Delete orderItem
    public void deleteProductCart(String orderItemId){
        // Create and show progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        orderRepository.deleteProductCart(
                orderMutableLiveData.getValue().getId(),
                orderItemId,
                new IOrderRepository.OrderItemRemovedCallback() {
                    @Override
                    public void onOrderItemRemoved(String id) {
                        // Load new cart product after delete from db
                        loadCart(userId);
                        // Dismiss progress dialog
                        dismissProgressDialog();

                    }
                    @Override
                    public void onError(String errorMessage) {
                        dismissProgressDialog();
                    }
                }
        );
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
