package com.example.javajoyadmin.data.repository.notification;

import android.util.Log;

import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.data.model.entity.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository implements INotificationRepository{
    private FirebaseFirestore db;
    private CollectionReference collectionRef;
    public NotificationRepository() {
        db = FirebaseFirestore.getInstance();
        collectionRef = db.collection("NOTIFICATION");
    }

    // method to get notification list by repcipient id and type
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

    // method to get notification by order id and title
    @Override
    public void getNotificationByOrderIdAndTitle(String orderId, String title, NotificationCallback callback){
        Query query = collectionRef
                .whereEqualTo("ID_ORDER", orderId)
                .whereEqualTo("TITLE", title);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                Notification notification = documentSnapshot.toObject(Notification.class);
                callback.onLoaded(notification);

            } else {
                String errorMessage = "notification not found";
                Log.e("FirestorenotificationRepository", errorMessage);
                callback.onError(errorMessage);
            }
        }).addOnFailureListener(e -> {
            String errorMessage = "Failed to get notification: " + e.getMessage();
            Log.e("FirestoreOrderRepository", errorMessage);
            callback.onError(errorMessage);
        });
    }

    // method to delete notification when expire???

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

}
