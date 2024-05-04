package com.example.foodorderingapp.ui.Activity_Fragment.Customer.Checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.CouponAdapter;
import com.example.foodorderingapp.data.model.entity.Coupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CouponActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private TextView screenName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn mã khuyến mãi");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // set recyler list
        recyclerViewCoupon();
    }

    private void recyclerViewCoupon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewCoupon);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<Coupon> coupons = new ArrayList<Coupon>();

        // Original date string
        String dateString = "06-03-2025";

        // Define the date format of your input string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Parse the string to obtain a Date object
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        coupons.add(new Coupon("Giảm 15.000đ", date, "Giảm 10.000đ cho đơn hàng từ 100.000đ"));
        coupons.add(new Coupon("Giảm 20.000đ", date, "Giảm 20.000đ cho đơn hàng từ 100.000đ"));
        coupons.add(new Coupon("Giảm 15.000đ", date, "Giảm 10.000đ cho đơn hàng từ 100.000đ"));
        adapter = new CouponAdapter(coupons);
        recyclerViewList.setAdapter(adapter);
    }
}
