package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.data.model.OrderDetail;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private TextView screenName;
    private ArrayList<OrderDetail> productList;
    private ImageView btnChangeAddress;
    private ImageView btnSeePayment;
    private ImageView btnSeeCoupons;
    private Button btnBuy;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Xác nhận đơn hàng");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set button ChangeAddress click event
        btnChangeAddress = findViewById(R.id.btn_change_address);
        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckoutAddressActivity.class));
            }
        });

        // set button SeePayment click event
        btnSeePayment = findViewById(R.id.btn_see_payment);
        btnSeePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class));
            }
        });

        // set button SeeCoupons click event
        btnSeeCoupons = findViewById(R.id.btn_see_coupons);
        btnSeeCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CouponActivity.class));
            }
        });

        // set button Buy click event
        btnBuy = findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // set recyler list
        recyclerViewOrderDetail();
    }

    private void recyclerViewOrderDetail() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewOrderDetail);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        //todo: check lại khi thêm sản phẩm vào list vì màn hình ko hiện lên (cả những cái order_detail)
        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();

//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));

//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
//        productList.add(new OrderDetail("Bánh", 60000, "Lớn", "a", 2));

//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
//        productList.add(new OrderDetail("Bánh", 45000, "Lớn", "a", 2));
//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "a", 3));
        adapter = new OrderDetailAdapter(productList);
        recyclerViewList.setAdapter(adapter);
    }
}