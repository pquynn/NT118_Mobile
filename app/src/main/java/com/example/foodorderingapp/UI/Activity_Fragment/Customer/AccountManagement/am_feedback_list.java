package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.Data.Repository.AccountManagement.MyOrders.OrdersListFeedbackRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Adapter.FeedbackItemAdapter;
import com.example.foodorderingapp.Data.Model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

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

        OrdersListFeedbackRepository repository = new OrdersListFeedbackRepository();
        repository.getlistFeedback("2", new OrdersListFeedbackRepository.orderItemCallback() {
            @Override
            public void loadOrderItemsSuccess(List<OrderDetail> orderItems) {

            }

            @Override
            public void loadOrderItemsError(Exception e) {

            }
        });
    }

    private void recyclerViewOrderFeedBack() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerFeedbackList);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<>();

        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
        productList.add(new OrderDetail("Bánh", 60000, "Lớn", "a", 2));
        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "a", 3));
        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
        productList.add(new OrderDetail("Bánh", 60000, "Lớn", "a", 2));
        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "a", 3));

        adapter = new FeedbackItemAdapter(productList);
        recyclerViewList.setAdapter(adapter);
    }
}