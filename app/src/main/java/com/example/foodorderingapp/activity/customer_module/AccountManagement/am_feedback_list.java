package com.example.foodorderingapp.activity.customer_module.AccountManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.FeedbackItemAdapter;
import com.example.foodorderingapp.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.domain.OrderDetailDomain;

import java.util.ArrayList;

public class am_feedback_list extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_list);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá đơn hàng");

        recyclerViewOrderFeedBack();
    }

    private void recyclerViewOrderFeedBack() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerFeedbackList);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetailDomain> productList = new ArrayList<>();

        productList.add(new OrderDetailDomain("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
        productList.add(new OrderDetailDomain("Bánh", 60000, "Lớn", "a", 2));
        productList.add(new OrderDetailDomain("Trà sữa trân châu", 45000, "Lớn", "a", 3));
        productList.add(new OrderDetailDomain("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
        productList.add(new OrderDetailDomain("Bánh", 60000, "Lớn", "a", 2));
        productList.add(new OrderDetailDomain("Trà sữa trân châu", 45000, "Lớn", "a", 3));

        adapter = new FeedbackItemAdapter(productList);
        recyclerViewList.setAdapter(adapter);
    }
}