package com.example.foodorderingapp.data.repository.order;

import android.util.Log;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
                        callback.onOrderLoaded(order);
//                        Log.d("FirestoreOrderRepository", "Order loaded: " + order.toString());
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

    // Get cart by user id (order has status = 'Giỏ hàng')
    public void getCartByUserId(String userId, OrderCallback callback){
        Query query = collectionRef
                .whereEqualTo("ID_USER", userId)
                .whereEqualTo("STATUS", "Giỏ hàng");

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                Order order = documentSnapshot.toObject(Order.class);
//                Log.d("FirestoreOrderRepository", "Cart loaded: " + order.toString());
                callback.onOrderLoaded(order);

            } else {
                String errorMessage = "Cart not found";
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
        Log.d("GetUserId", userId );
        Log.d("GetStatus", status);
        Query query;
        if (Objects.equals(status, "Chờ xác nhận") || Objects.equals(status, "Đã xác nhận")) {
            query = collectionRef
                    .whereIn("STATUS", Arrays.asList("Chờ xác nhận", "Đã xác nhận"))
                    .whereEqualTo("ID_USER", userId)
                    .orderBy("CREATE_ON", Query.Direction.DESCENDING); // Hóa đơn mới nhất đứng đầu danh sách
        } else {
            query = collectionRef
                    .whereEqualTo("STATUS", status)
                    .whereEqualTo("ID_USER", userId)
                    .orderBy("CREATE_ON", Query.Direction.DESCENDING); // Hóa đơn mới nhất đứng đầu danh sách
        }

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                // create an order list
                List<Order> orderList = new ArrayList<>();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Order order = documentSnapshot.toObject(Order.class);
                    orderList.add(order);

                    // log ra
                    Log.d("FirestoreOrderRepository", "getorderlist" + order.toString());
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

    // Get order document list status
    public void getOrderListByStatus(String status, OrderListCallback callback){
        Query query = collectionRef
                .whereEqualTo("STATUS", status);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                // create an order list
                List<Order> orderList = new ArrayList<>();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Order order = documentSnapshot.toObject(Order.class);
                    orderList.add(order);

                    // log ra
                    Log.d("FirestoreOrderRepository", "getorderlist" + order.toString());
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
                "",
                "",
                0,
                0,
                0,
                "",
                "Giỏ hàng",
                0,
                0,
                createOn,
                0,
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

    // updateCartToOrder (Update order, 'Gio hang' -> 'Cho xac nhan')
//    public void updateCartToOrder(Order order, OrderUpdatedCallback callback) {
//        if (order == null) {
//            if (callback != null) {
//                callback.onError("Order cannot be null");
//            }
//            return;
//        }
//
//        if (callback == null) {
//            throw new IllegalArgumentException("Callback cannot be null");
//        }
//
//        collectionRef.document(order.getId()).set(order)
//                .addOnSuccessListener(aVoid -> callback.onOrderChanged(order))
//                .addOnFailureListener(e -> callback.onError(e.getMessage()));
//    }
    public void updateCartToOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        collectionRef.document(order.getId()).set(order)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                    // Handle failure case, e.g., logging or updating UI
                    Log.d("firestore", "Error updating order: " + e.getMessage());
                });
    }




    // Add or update product to shopping cart (create new order item by order id)
    @Override
    public void addOrUpdateProductCart(String orderId, String orderItemId, OrderItem orderItem, OrderChangedCallback callback){
        collectionRef.document(orderId).update("ORDER_ITEM." + orderItemId, orderItem)
                .addOnSuccessListener(aVoid -> {
                    callback.onOrderChanged();
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


    // method to update order items by order id
    public void updateOrderItemsByOrderId(String orderId, Map<String, OrderItem> orderItemMap) {

        collectionRef.document(orderId).update("ORDER_ITEM", orderItemMap)
                .addOnSuccessListener(aVoid ->
                        Log.d("firestore", "update order item: " ))
                .addOnFailureListener(e -> System.err.println("Error updating order items: " + e.getMessage()));
    }

    // method to get total product cart


    // method to calculate product cart item count
//    public void calculateTotalProductCart(String userId, IntegerCallback callback){
//        Query query = collectionRef
//                .whereEqualTo("ID_USER", userId)
//                .whereEqualTo("STATUS", "Giỏ hàng");
//
//        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
//            if (!queryDocumentSnapshots.isEmpty()) {
//                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
//                Order order = documentSnapshot.toObject(Order.class);
//                int quantity = 0;
//                for(Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()){
//                    quantity += entry.getValue().getQuantity();
//                }
//                callback.onLoaded(quantity);
//            }
//            else
//                callback.onLoaded(0);
//        }).addOnFailureListener(e -> {
//            String errorMessage = "Failed to get order: " + e.getMessage();
//            Log.e("FirestoreOrderRepository", errorMessage);
//            callback.onError(errorMessage);
//        });
//    }
    private int quantity = 0;

    public void calculateTotalProductCart(String userId, IntegerCallback callback) {
        Query query = collectionRef
                .whereEqualTo("ID_USER", userId)
                .whereEqualTo("STATUS", "Giỏ hàng");

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                Order order = documentSnapshot.toObject(Order.class);

                // Use a list to keep track of pending tasks
                List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                AtomicInteger quantity = new AtomicInteger(0);

                for (Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()) {
                    DocumentReference productRef = db.collection("PRODUCT").document(entry.getValue().getIdProduct());
                    Task<DocumentSnapshot> task = productRef.get();
                    tasks.add(task);

                    task.addOnSuccessListener(documentSnapshot1 -> {
                        if (documentSnapshot1.exists()) {
                            Product product = documentSnapshot1.toObject(Product.class);
                            String size = (entry.getValue().getSize() == null || entry.getValue().getSize().isEmpty()) ? "Mặc định" : entry.getValue().getSize();
                            int productQuantity = product.getProductSize().get(size).getOrDefault("QUANTITY", 0);

                            if (productQuantity > 0) {
                                quantity.addAndGet(entry.getValue().getQuantity());
                            }
                        }
                    }).addOnFailureListener(e -> Log.e("FirestoreOrderRepository", e.getMessage()));
                }

                // Use Tasks.whenAllComplete to handle completion of all tasks
                Tasks.whenAllComplete(tasks).addOnSuccessListener(voids -> callback.onLoaded(quantity.get()))
                        .addOnFailureListener(e -> {
                            String errorMessage = "Failed to get all product details: " + e.getMessage();
                            Log.e("FirestoreOrderRepository", errorMessage);
                            callback.onError(errorMessage);
                        });
            } else {
                callback.onLoaded(0);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get order: " + e.getMessage();
            Log.e("FirestoreOrderRepository", errorMessage);
            callback.onError(errorMessage);
        });
    }


}
