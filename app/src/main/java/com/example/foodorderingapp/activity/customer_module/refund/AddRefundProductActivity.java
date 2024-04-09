package com.example.foodorderingapp.activity.customer_module.refund;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CouponAdapter;
import com.example.foodorderingapp.adapter.RefundProductAdapter;
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.OrderDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddRefundProductActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_refundprod);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn sản phẩm hoàn tiền");

        // set recyler list
        recyclerViewCoupon();
    }

    private void recyclerViewCoupon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewRefundProd);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();

        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "50% đường", 3));
        productList.add(new OrderDetail("Bánh", "60.000 đ", "Lớn", "a", 2));

        adapter = new RefundProductAdapter(productList);
        recyclerViewList.setAdapter(adapter);
    }
}
