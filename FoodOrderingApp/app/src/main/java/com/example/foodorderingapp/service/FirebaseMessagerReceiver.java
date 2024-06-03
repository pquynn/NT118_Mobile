package com.example.foodorderingapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.viewmodel.customer.navigation.BottomNavigationViewModel;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_order_detail;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.UUID;

public class FirebaseMessagerReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null) {
            showNotification(
                    message.getNotification().getTitle(),
                    message.getNotification().getBody());
        }
    }

    private void showNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String channelId = "noti";
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

//        builder = builder.setContent(customView(title, body));
        builder = builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channelId,
                    "web_app",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Generate a unique ID for the notification using UUID
        int notificationId = UUID.randomUUID().hashCode();
        notificationManager.notify(notificationId, builder.build());
    }

}
