package com.example.foodorderingapp.UI.Activity_Fragment.Admin.Refund;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CouponAdapter;
import com.example.foodorderingapp.adapter.RefundProductAdapter;
import com.example.foodorderingapp.adapter.RefundProgressAdapter;
import com.example.foodorderingapp.adapter.RefundRequestAdapter;
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.OrderDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RefundProgressActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private TextView screenName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_progress);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Yêu cầu hoàn tiền");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // set recyler list
        recyclerViewRefundRequest();
    }

    private void recyclerViewRefundRequest() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewRefundProgress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();

        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));

        adapter = new RefundProgressAdapter(productList);
        recyclerViewList.setAdapter(adapter);
    }


}
