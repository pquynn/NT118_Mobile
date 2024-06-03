package com.example.foodorderingapp.data.repository.notification;

import android.util.Log;

import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationRepository implements INotificationRepository{
    private FirebaseFirestore db;
    private CollectionReference collectionRef;
    public NotificationRepository() {
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("NOTIFICATION");
    }

    @Override
    public void getNotificationByRecipientIdAndType(String idRecipient, int recipientType, NotificationListCallback callback) {
        Query query = collectionRef
                .orderBy("DATE", Query.Direction.DESCENDING)
                .whereEqualTo("RECIPIENT_TYPE", recipientType)
                .whereEqualTo("ID_RECIPIENT", idRecipient);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                List<Notification> notificationList = new ArrayList<>();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Notification notification = documentSnapshot.toObject(Notification.class);
                    notificationList.add(notification);

                    // log ra
                    Log.d("FirestoreNotificationRepository", "noti list" + notification.toString());
                }
                // callback
                callback.onListLoaded(notificationList);
            } else {
                String errorMessage = "Notification not found";
                Log.e("FirestoreNotificationRepository", errorMessage);
                callback.onError(errorMessage);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get Notification: " + e.getMessage();
            Log.e("FirestoreNotificationRepository", errorMessage);
            callback.onError(errorMessage);
        });

    }

    // method to calculate unread notification of user
    public void calculateUnreadNoti(String idRecipient, IntegerCallback callback){
        Query query = collectionRef
                .whereEqualTo("ID_RECIPIENT", idRecipient)
                .whereEqualTo("STATUS", "unread");

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            int unreadCount = 0;
            if (!queryDocumentSnapshots.isEmpty()) {
                // create an order list
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
//                    Notification notification = documentSnapshot.toObject(Notification.class);
                    unreadCount++;
                }
                // callback
                callback.onLoad(unreadCount);
            } else {
                callback.onLoad(0);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get noti: " + e.getMessage();
            Log.e("FirestorenotiRepository", errorMessage);
            callback.onError(errorMessage);
        });
    }

    // method to create new notification
    @Override
    public void createNotification(Notification notification){
        collectionRef.add(notification)
                .addOnSuccessListener(documentReference -> {})
                .addOnFailureListener(e -> {
                    Log.e("error", "create noti unsuccessfully ");
                });
    }

    // method to update notification status unread <--> read
    @Override
    public void updateNotificationStatus(String status, String notificationId){
        collectionRef.document(notificationId).update("STATUS", status)
                .addOnSuccessListener(aVoid -> {})
                .addOnFailureListener(e -> {});
    }

    // method to update notification
    @Override
    public void updateNotification(Notification notification){
        if (notification == null) {
            throw new IllegalArgumentException("notification cannot be null");
        }
        collectionRef.document(notification.getId()).set(notification)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                    // Handle failure case, e.g., logging or updating UI
                    Log.d("firestore", "Error updating notification: " + e.getMessage());
                });
    }

    // method to delete notification when expire???

}
