package com.example.foodorderingapp.data.repository.order;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface IOrderRepository {
    // Get order document by order id
    void getOrderById(String orderId, OrderCallback callback);

    // Get cart by user id (order has status = 'Giỏ hàng')
    void getCartByUserId(String userId, OrderCallback callback);

    // Get order document list by user id and status
    void getOrderListByStatusAndUserId(String userId, String status, OrderListCallback callback);

    // Get order document list status
    void getOrderListByStatus(String status, OrderListCallback callback);

    // Create new order document by user id and orderItem
    void createOrder(String userId, Map<String, OrderItem> orderItemMap, OrderCallback callback);

    // Update order status by id
    void updateOrderStatusById(String orderId, String status, OrderChangedCallback callback);

    // Checkout (Update order, 'Gio hang' -> 'Cho xac nhan')
    void checkout(Order order, OrderChangedCallback callback);

    // Add or update product in shopping cart by order id
    void addOrUpdateProductCart(String orderId, String orderItemId, OrderItem orderItem, OrderChangedCallback callback);

    // Delete product in shopping cart by order id
    void deleteProductCart(String orderId,  String orderItemId, OrderItemRemovedCallback callback);



    interface OrderCallback {
        void onOrderLoaded(Order order);
        void onError(String errorMessage);
    }

    interface OrderChangedCallback {
        void onOrderChanged();
        void onError(String errorMessage);
    }

    interface OrderItemCallback {
        void onOrderItemLoaded(OrderItem orderItem);
        void onError(String errorMessage);
    }

    interface OrderItemRemovedCallback {
        void onOrderItemRemoved(String id);
        void onError(String errorMessage);
    }

    interface OrderListCallback {
        void onOrderListLoaded(List<Order> orderList);
        void onError(String errorMessage);
    }


}
