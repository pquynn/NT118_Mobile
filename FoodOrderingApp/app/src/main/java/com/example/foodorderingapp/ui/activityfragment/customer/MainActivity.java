package com.example.foodorderingapp.ui.activityfragment.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.ui.viewmodel.customer.navigation.BottomNavigationViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationViewModel viewModel;
    private String userId="";
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    private int productCartQuantity = 0;
    private BadgeDrawable cartBadge, notiBadge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment_area);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        // if user is login --> show badge
//        if (userId != null) {
            //init viewmodel
            viewModel = new BottomNavigationViewModel(userId);

            // Set badge for cart fragment
            cartBadge = bottomNavigationView.getOrCreateBadge(R.id.cartFragment);
            notiBadge = bottomNavigationView.getOrCreateBadge(R.id.notificationFragment);
            //observe change in cart item
            viewModel.getProductCartQuantityLiveData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    setBadge(cartBadge, integer);
                }
            });

            //observe change in noti item
            viewModel.getUnreadNotiCountLiveData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    setBadge(notiBadge, integer);
                }
            });
//        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void updateProductCartQuantity(int count){
        viewModel.setProductCartQuantityLiveData(count);
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
}
