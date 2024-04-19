package com.example.foodorderingapp.UI.Activity.Customer.Refund;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Activity.Customer.Checkout.CheckoutAddressActivity;
import com.example.foodorderingapp.adapter.RefundProductAdapter;
import com.example.foodorderingapp.domain.OrderDetail;

import java.util.ArrayList;

public class AddRefundProductActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private Button btnAdd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_refundprod);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn sản phẩm hoàn tiền");

        //
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RefundRequestActivity.class));
            }
        });
        // set recyler list
        recyclerViewRefundProd();
    }

    private void recyclerViewRefundProd() {
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
