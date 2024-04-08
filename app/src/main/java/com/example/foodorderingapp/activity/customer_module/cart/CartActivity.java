package com.example.foodorderingapp.activity.customer_module.cart;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CartAdapter;
import com.example.foodorderingapp.adapter.CouponAdapter;
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.OrderDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Giỏ hàng");

        // set recyler list
        recyclerViewCart();
    }

    private void recyclerViewCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewCart);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();
        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "", 3));
        productList.add(new OrderDetail("Bánh", "60.000 đ", "Lớn", "", 2));
        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "", 3));
        adapter = new CartAdapter(productList);
        recyclerViewList.setAdapter(adapter);
    }
}
