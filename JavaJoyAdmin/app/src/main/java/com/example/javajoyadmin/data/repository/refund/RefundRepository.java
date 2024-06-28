package com.example.javajoyadmin.data.repository.refund;

import android.util.Log;

import com.example.javajoyadmin.data.model.entity.Refund;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class RefundRepository implements IRefundRepository {
    private FirebaseFirestore db;
    private CollectionReference collectionRef;
    public RefundRepository() {
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("REFUND");
    }

    // Get refund document by order id
    public void getRefundByOrderId(String orderId, RefundCallback callback){
        Query query = collectionRef.whereEqualTo("ID_ORDER", orderId);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                Refund refund = documentSnapshot.toObject(Refund.class);

                callback.onRefundLoaded(refund);

                // log ra
                Log.d("FirestoreOrderRepository", "Refund loaded: " + refund.toString());
            } else {
                String errorMessage = "Refund not found";
                Log.e("FirestoreRefundRepository", errorMessage);
                callback.onError(errorMessage);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get Refund: " + e.getMessage();
            Log.e("FirestoreRefundRepository", errorMessage);
            callback.onError(errorMessage);
        });
    }


    // Create new refund document by order id and refund item
    public void createRefund(Refund refund, RefundCallback callback){
        collectionRef.add(refund)
                .addOnSuccessListener(documentReference -> callback.onRefundLoaded(refund))
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }


    // Update refund status by id
    public void updateRefundStatusById(String refundId, String status, RefundChangedCallback callback){
        Date dateProcessed;
        dateProcessed = new Date();

        collectionRef.document(refundId).update("STATUS", status, "DATE_PROCESSED", dateProcessed)
                .addOnSuccessListener(aVoid -> {
                    callback.onRefundChanged();
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());
                });
    }
}
