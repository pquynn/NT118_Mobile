package com.example.foodorderingapp.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.Data.Model.Entity.Order;
import com.example.foodorderingapp.Data.Model.Entity.OrderItem;
import com.example.foodorderingapp.Data.Repository.Order.IOrderRepository;
import com.example.foodorderingapp.Data.Repository.Order.OrderRepository;
import com.example.foodorderingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class resultTestActivity extends AppCompatActivity {
    private OrderRepository orderRepository;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);

        orderRepository = new OrderRepository();
        orderRepository.getOrderById("1", new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {

            }
            @Override
            public void onError(String errorMessage) {

            }
        });

//        orderRepository.getCartByUserId("3", new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//
//            }
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });



//        // Create an instance of OrderItem
        OrderItem orderItem = new OrderItem();
        String id = UUID.randomUUID().toString();
        orderItem.setIdOrderItem(id);
        orderItem.setIdProduct("4");
        orderItem.setQuantity(2);
        orderItem.setPrice(10);
        orderItem.setNote("No notes");
        orderItem.setSize("Lớn");
        ArrayList<String> topping = new ArrayList<>();
        topping.add("3");
        topping.add("1");
        orderItem.setTopping(topping);

        // Create a Map<String, OrderItem> and add the OrderItem to it
        Map<String, OrderItem> orderItemMap = new HashMap<>();
        orderItemMap.put(orderItem.getIdOrderItem(), orderItem);
        orderRepository.createOrder("1", orderItemMap, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });


        orderRepository.getOrderListByStatusAndUserId("1", "Giỏ hàng", new IOrderRepository.OrderListCallback() {
            @Override
            public void onOrderListLoaded(List<Order> orderList) {

            }
            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
