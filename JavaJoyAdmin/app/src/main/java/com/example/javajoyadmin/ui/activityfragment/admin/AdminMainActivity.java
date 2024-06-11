package com.example.javajoyadmin.ui.activityfragment.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.SendNotification;
import com.example.javajoyadmin.ui.activityfragment.authentication.activity_login;
import com.example.javajoyadmin.ui.viewmodel.admin.navigation.BottomNavigationViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class AdminMainActivity extends AppCompatActivity {
    private BottomNavigationViewModel viewModel;
    private String userId="";
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    private BadgeDrawable notiBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment_area);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
//        String token = sharedPreferences.getString(KEY_TOKEN, null);
        if (userId == null) {
            // For example, redirect to login activity or show a message
            finish();
            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);
        }

        viewModel = new BottomNavigationViewModel(userId);
        // Set badge for cart fragment
        notiBadge = bottomNavigationView.getOrCreateBadge(R.id.notificationFragment);

        //observe change in noti item
        viewModel.getUnreadNotiCountLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setBadge(notiBadge, integer);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
    public void reloadBadge(){
        viewModel.loadUnreadNotiCount();
    }

    public void updateUnreadNotiQuantity(int count){
        viewModel.getUnreadNotiCountLiveData().setValue(count);
    }

    // method to set badge number in bottom navigation
    public void setBadge(BadgeDrawable badgeDrawable, int count){
        int primary = ContextCompat.getColor(this, R.color.primary);
        badgeDrawable.setBackgroundColor(primary);
        if (count > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(count);
        }
        else {
            badgeDrawable.setVisible(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadBadge();
    }
}
