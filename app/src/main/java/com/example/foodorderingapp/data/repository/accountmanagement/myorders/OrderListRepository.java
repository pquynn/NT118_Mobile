package com.example.foodorderingapp.data.repository.accountmanagement.myorders;

import android.util.Log;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepository {
    private FirebaseFirestore db ;

    public OrderListRepository(){
        db = FirebaseFirestore.getInstance();
    }

    public void getOrderListByStatusAndUserId(String userId, String status, IOrderRepository.OrderListCallback callback){
        Log.d("GetUserId", userId );
        Log.d("GetStatus", status);
        db.collection("ORDER")
                .whereEqualTo("ID_USER", userId)
                .whereEqualTo("STATUS", status)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // create an order list
                            List<Order> orderList = new ArrayList<>();

                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                Order order = documentSnapshot.toObject(Order.class);
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
                }
                });
    }
}
