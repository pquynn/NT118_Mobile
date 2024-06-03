package com.example.javajoyadmin.ui.activityfragment.admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.SendNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class AdminMainActivity extends AppCompatActivity {
//    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment_area);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


//    FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//        @Override
//        public void onComplete(@NonNull Task<String> task) {
//            if (!task.isSuccessful()) {
//                Log.w("fcm", "Fetching FCM registration token failed", task.getException());
//                return;
//            }
//            // Get new FCM registration token
//            token = task.getResult();
//            // Log and toast
////            Log.d("fcm", token);
//        }
//    });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//    sendNoti();
}

//    public void sendNoti(){
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SendNotification notificationSender =
//                        new SendNotification(token, "push noti from admin", "aaaaaa",
//                                getApplicationContext());
//                notificationSender.SendNotifications();
//
//            }
//        } , 300);
//    }
}
