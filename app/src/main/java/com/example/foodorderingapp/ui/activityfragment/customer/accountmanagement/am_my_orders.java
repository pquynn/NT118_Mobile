package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderCancelled;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderCompleted;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderDelivering;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderInProgress;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderRefunded;
import com.example.foodorderingapp.ui.viewpageradapter.MyOrdersViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class am_my_orders extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MyOrdersViewPagerAdapter ViewPagerAdapter;
    FrameLayout btnBack;
    String userId = "";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_my_orders);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đơn hàng của tôi");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }
        Log.d("user id in AM: ", "user id: " + userId);


        viewPager = findViewById(R.id.my_orders_viewpager);
        tabLayout = findViewById(R.id.my_orders_menu);

        ViewPagerAdapter = new MyOrdersViewPagerAdapter(this, userId);
        viewPager.setAdapter(ViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, i) -> {
            switch(i) {
                case 0:
                    tab.setText("Đang xử lý");
                    break;
                case 1:
                    tab.setText("Đang giao");
                    break;
                case 2:
                    tab.setText("Đã giao");
                    break;
                case 3:
                    tab.setText("Đã hủy");
                    break;
                case 4:
                    tab.setText("Hoàn tiền");
                    break;
            }
        }).attach();




    }
}