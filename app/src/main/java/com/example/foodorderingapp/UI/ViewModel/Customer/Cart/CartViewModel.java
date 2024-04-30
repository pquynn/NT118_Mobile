package com.example.foodorderingapp.UI.ViewModel.Customer.Cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.Data.Model.Entity.Order;
import com.example.foodorderingapp.Data.Model.Entity.OrderItem;
import com.example.foodorderingapp.Data.Repository.Order.IOrderRepository;
import com.example.foodorderingapp.Data.Repository.Order.OrderRepository;

import java.util.ArrayList;
import java.util.Map;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Order> cart = new MutableLiveData<>();
//    private MutableLiveData<ArrayList<OrderItem>> refundItemListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> totalPrice = new MutableLiveData<>();

    private OrderRepository orderRepository = new OrderRepository();

    public MutableLiveData<String> getTotalPrice() {
        return totalPrice;
    }

    public MutableLiveData<Order> getCart() {
        return cart;
    }

    public CartViewModel(String userId){
        // find order by user id and status 'Gio hang'
        orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                cart.setValue(order);
                totalPrice.setValue(String.valueOf(calculateTotalPrice(order)));
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

    public void onEditConfirmClicked(){

    }

    public OrderItem getOrderItemById(String id){
        OrderItem orderItem = cart.getValue().getOrderItem().get(id);
        return orderItem;
    }
}
