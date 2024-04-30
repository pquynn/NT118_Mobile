package com.example.foodorderingapp.Data.Repository.Order;

import android.util.Log;

import com.example.foodorderingapp.Data.Model.Entity.Order;
import com.example.foodorderingapp.Data.Model.Entity.OrderItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderRepository implements IOrderRepository {
    private FirebaseFirestore db;
    private CollectionReference collectionRef;
    public OrderRepository() {
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("ORDER");
    }

    // Get order document by order id
    @Override
    public void getOrderById(String orderId, OrderCallback callback) {
        collectionRef.document(orderId)
                .get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Order order = documentSnapshot.toObject(Order.class);
//                        // set Order Item Map into Model
//                        order.setOrderItem((HashMap<String, OrderItem>) documentSnapshot.get("ORDER_ITEM"));

                        callback.onOrderLoaded(order);

                        // log ra
                        Log.d("FirestoreOrderRepository", "Order loaded: " + order.toString());
                    } else {
                        String errorMessage = "Order not found";
                        Log.e("FirestoreOrderRepository", errorMessage);
                        callback.onError(errorMessage);
                    }
                }).addOnFailureListener(e -> {
                    String errorMessage = "Failed to get order: " + e.getMessage();
                    Log.e("FirestoreOrderRepository", errorMessage);
                    callback.onError(errorMessage);
        });
    }

    // Get shopping cart by user id (order document has status = 'Gio hang')
    public void getCartByUserId(String userId, OrderCallback callback){
        Query query = collectionRef
                .whereEqualTo("ID_USER", userId)
                .whereEqualTo("STATUS", "Giỏ hàng");

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                Order order = documentSnapshot.toObject(Order.class);
                // set Order Item Map into Model
//                order.setOrderItem((HashMap<String, OrderItem>) documentSnapshot.get("ORDER_ITEM"));

                callback.onOrderLoaded(order);

                // log ra
                Log.d("FirestoreOrderRepository", "Order loaded: " + order.toString());
            } else {
                String errorMessage = "Order not found";
                Log.e("FirestoreOrderRepository", errorMessage);
                callback.onError(errorMessage);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get order: " + e.getMessage();
            Log.e("FirestoreOrderRepository", errorMessage);
            callback.onError(errorMessage);
        });
    }

    // Get order document list by user id and status
    public void getOrderListByStatusAndUserId(String userId, String status, OrderListCallback callback){
        Query query = collectionRef
                .whereEqualTo("ID_USER", userId)
                .whereEqualTo("STATUS", status);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                // create an order list
                List<Order> orderList = new ArrayList<>();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Order order = documentSnapshot.toObject(Order.class);
                    // set Order Item Map into Model
                    order.setOrderItem((HashMap<String, OrderItem>) documentSnapshot.get("ORDER_ITEM"));
                    orderList.add(order);

                    // log ra
                    Log.d("FirestoreOrderRepository", order.toString());
                }
                // callback
                callback.onOrderListLoaded(orderList);
            } else {
                String errorMessage = "Order not found";
                Log.e("FirestoreOrderRepository", errorMessage);
                callback.onError(errorMessage);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get order: " + e.getMessage();
            Log.e("FirestoreOrderRepository", errorMessage);
            callback.onError(errorMessage);
        });
    }

    // Create order document (create cart)
    @Override
    public void createOrder(String userId, Map<String, OrderItem> orderItemMap, OrderCallback callback){
    //  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date createOn = new Date(0);

        Order order = new Order(
                userId,
                "",
                0,
                0,
                0,
                "COD",
                "Giỏ hàng",
                0,
                0,
                createOn,
                "",
                orderItemMap
        );

        collectionRef.add(order)
                .addOnSuccessListener(documentReference -> callback.onOrderLoaded(order))
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    // Update order status by id
    public void updateOrderStatusById(String orderId, String status, OrderChangedCallback callback){
        collectionRef.document(orderId).update("STATUS", status)
                .addOnSuccessListener(aVoid -> {
                    callback.onOrderChanged();
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());
                });
    }

    // Checkout (Update order, 'Gio hang' -> 'Cho xac nhan')
    public void checkout(Order order, OrderChangedCallback callback){
        collectionRef.document(order.getId()).set(order)
                .addOnSuccessListener(aVoid -> callback.onOrderChanged())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }


    // Add or update product to shopping cart (create new order item by order id)
    @Override
    public void addOrUpdateProductCart(String orderId, OrderItem orderItem, OrderItemCallback callback){
        collectionRef.document(orderId).update("ORDER_ITEM." + orderItem.getIdOrderItem(), orderItem)
                .addOnSuccessListener(aVoid -> {
                    callback.onOrderItemLoaded(orderItem);
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());
                });
    }

    // Delete product in shopping cart by order id
    public void deleteProductCart(String orderId, String orderItemId, OrderItemRemovedCallback callback){
        collectionRef.document(orderId).update("ORDER_ITEM." + orderItemId, FieldValue.delete())
                .addOnSuccessListener(aVoid -> {
                    callback.onOrderItemRemoved(orderItemId);
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());
                });
    }

}
