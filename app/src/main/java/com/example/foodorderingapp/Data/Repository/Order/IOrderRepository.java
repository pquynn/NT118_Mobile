package com.example.foodorderingapp.Data.Repository.Order;

import com.example.foodorderingapp.Data.Model.Entity.Order;
import com.example.foodorderingapp.Data.Model.Entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface IOrderRepository {
    /// Get order document by order id
    void getOrderById(String orderId, OrderCallback callback);

    // Get shopping cart by user id (order document has status = 'Gio hang')
    void getCartByUserId(String userId, OrderCallback callback);

    // Get order document list by user id and status
    void getOrderListByStatusAndUserId(String userId, String status, OrderListCallback callback);

    // Create new order document by user id and orderItem
    void createOrder(String userId, Map<String, OrderItem> orderItemMap, OrderCallback callback);

    // Update order by id
//    void updateOrderById(String orderId, Order order, OrderCallback callback);





//    // Get order item by id
//    void getOrderItemById(String itemId, OrderItemCallback callback);
//
//    // Get order item list by order id
//    void getOrderItemListByOrderId(String orderId, OrderItemListCallback callback);
//
//
//
//    // Delete order item by id
//    void deleteOrderItemById(String itemId, OrderItemCallback callback);
//
//    // Update order item by id
//    void updateOrderItemById(String itemId, OrderItem orderItem, OrderItemCallback callback);

    interface OrderCallback {
        void onOrderLoaded(Order order);
        void onError(String errorMessage);
    }

    interface OrderListCallback {
        void onOrderListLoaded(List<Order> orderList);
        void onError(String errorMessage);
    }

    interface OrderItemCallback {
        void onOrderItemLoaded(OrderItem orderItem);
        void onError(String errorMessage);
    }

    interface OrderItemListCallback {
        void onOrderItemListLoaded(List<OrderItem> orderItemList);
        void onError(String errorMessage);
    }
}
