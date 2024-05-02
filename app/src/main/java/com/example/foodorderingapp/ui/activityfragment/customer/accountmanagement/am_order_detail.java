package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.data.model.OrderDetail;

import java.util.ArrayList;


public class am_order_detail extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;

    Button btnCancel, btnFeedback, btnRefund;
    TextView txt_orderStatus; //txt_order_status
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_order_detail);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chi tiết đơn hàng");

        recyclerViewOrderDetail();

        txt_orderStatus = findViewById(R.id.txt_order_status);
        btnCancel = findViewById(R.id.btn_order_cancel);
        btnFeedback = findViewById(R.id.btn_order_feedback);
        btnRefund = findViewById(R.id.btn_order_refund);

        String orderStatus = (String) txt_orderStatus.getText();

        btnCancel.setVisibility(View.GONE);
        btnFeedback.setVisibility(View.GONE);
        btnRefund.setVisibility(View.GONE);

        switch (orderStatus) {
            case "Chờ xác nhận": //pending
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case "Hoàn tất": //completed
                btnFeedback.setVisibility(View.VISIBLE);
                btnRefund.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void recyclerViewOrderDetail() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerProductList);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();
//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
//        productList.add(new OrderDetail("Bánh", 60000, "Lớn", "a", 2));
//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "a", 3));
        adapter = new OrderDetailAdapter(productList);
        recyclerViewList.setAdapter(adapter);

    }
}