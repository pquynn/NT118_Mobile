package com.example.foodorderingapp.Data.Repository.Order;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.Data.Model.Entity.Order;
import com.example.foodorderingapp.Data.Model.Entity.OrderItem;
import com.example.foodorderingapp.Data.Repository.Order.IOrderRepository;
import com.example.foodorderingapp.Data.Repository.Order.OrderRepository;
import com.example.foodorderingapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class resultTestActivity extends AppCompatActivity {
    private OrderRepository orderRepository;
    private Order od;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);

        orderRepository = new OrderRepository();
//        orderRepository.getOrderById("1", new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//
//            }
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

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
//        OrderItem orderItem = new OrderItem();
//        String id = UUID.randomUUID().toString();
//        orderItem.setIdOrderItem(id);
//        orderItem.setIdProduct("4");
//        orderItem.setQuantity(2);
//        orderItem.setPrice(10);
//        orderItem.setNote("No notes");
//        orderItem.setSize("Lớn");
//        ArrayList<String> topping = new ArrayList<>();
//        topping.add("3");
//        topping.add("1");
//        orderItem.setTopping(topping);
//
//        // Create a Map<String, OrderItem> and add the OrderItem to it
//        Map<String, OrderItem> orderItemMap = new HashMap<>();
//        orderItemMap.put(orderItem.getIdOrderItem(), orderItem);
//        orderRepository.createOrder("1", orderItemMap, new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

//         Create an instance of OrderItem
//        OrderItem orderItem = new OrderItem();
////        String id = UUID.randomUUID().toString();
////        orderItem.setIdOrderItem(id);
//        orderItem.setIdOrderItem("602447ac-2108-4d83-aca6-15349523d78d");
//        orderItem.setIdProduct("12");
//        orderItem.setQuantity(1);
//        orderItem.setPrice(10000);
//        orderItem.setNote("");
//        orderItem.setSize("Vừa");
//        ArrayList<String> topping = new ArrayList<>();
//        topping.add("1");
//        orderItem.setTopping(topping);
//        orderRepository.addOrUpdateProductCart("UV4jcGJFA5p37R0OUVqH", orderItem, new IOrderRepository.OrderItemCallback() {
//            @Override
//            public void onOrderItemLoaded(OrderItem orderItem) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

//        orderRepository.deleteProductCart("UV4jcGJFA5p37R0OUVqH", "602447ac-2108-4d83-aca6-15349523d78d", new IOrderRepository.OrderItemRemovedCallback() {
//            @Override
//            public void onOrderItemRemoved(String id) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//
//        orderRepository.getOrderListByStatusAndUserId("1", "Giỏ hàng", new IOrderRepository.OrderListCallback() {
//            @Override
//            public void onOrderListLoaded(List<Order> orderList) {
//
//            }
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

//        orderRepository.updateOrderStatusById("2", "Đã giao", new IOrderRepository.OrderChangedCallback() {
//            @Override
//            public void onOrderChanged() {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

        orderRepository.getOrderById("nqwbKhPyhuXNyvyaYnA4", new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                // Modify the order here
                order.setAddress("aaaaaaaaaaaaaaaaaaaa");
                order.setOrderPrice(100002);
                order.setDeliveryCost(100002);
                order.setTotalPrice(100002);

                // Checkout the modified order
                orderRepository.checkout(order, new IOrderRepository.OrderChangedCallback() {
                    @Override
                    public void onOrderChanged() {
                        Log.d("FirestoreOrderRepository", "checkout: " + order.toString());
                        // Update UI or perform any other actions after checkout
                    }

                    @Override
                    public void onError(String errorMessage) {
                        // Handle error
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });



    }
}
