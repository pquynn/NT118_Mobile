package com.example.javajoyadmin.ui.activityfragment.admin;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.javajoyadmin.ui.activityfragment.authentication.activity_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class AdminMainActivity extends AppCompatActivity {
//    private SharedPreferences sharedPreferences;
//    private static final String SHARE_PREF_NAME = "sharePrefName";
//    private static final String KEY_USER_ID = "userID";
//    private static final String KEY_TOKEN = "token";
//    private String userId, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment_area);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

//        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
//        userId = sharedPreferences.getString(KEY_USER_ID, null);
//        String token = sharedPreferences.getString(KEY_TOKEN, null);
//        if (userId == null) {
//            // User ID not found, handle this case
//            // For example, redirect to login activity or show a message
//            finish();
//            Intent intent = new Intent(this, activity_login.class);
//            startActivity(intent);
//        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

}

}
