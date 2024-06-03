package com.example.foodorderingapp.ui.activityfragment.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.ui.viewmodel.customer.navigation.BottomNavigationViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationViewModel viewModel;
    private String userId = "3";
    private int productCartQuantity = 0;
    private BadgeDrawable cartBadge, notiBadge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment_area);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

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
        // Observe changes to the cart item count
//        viewModel.getProductCartQuantityLiveData().observe(this, count -> {
//            if (count != null && count > 0) {
//                cartBadge.setVisible(true);
//                cartBadge.setNumber(count);
//            } else {
//                cartBadge.clearNumber();
//                cartBadge.setVisible(false);
//            }
//        });
//
//        // Observe changes to the notification count
//        viewModel.getUnreadNotiCountLiveData().observe(this, count -> {
//            if (count != null && count > 0) {
//                notiBadge.setVisible(true);
//                notiBadge.setNumber(count);
//            } else {
//                notiBadge.clearNumber();
//                notiBadge.setVisible(false);
//            }
//        });

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
