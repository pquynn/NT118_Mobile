package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.ui.adapter.FeedbackItemAdapter;
import com.example.foodorderingapp.data.model.OrderDetail;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.OrderFeedbackListVM;

import java.util.ArrayList;
import java.util.List;

public class am_feedback_list extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private OrderFeedbackListVM viewModel;
    ArrayList<OrderItem> productList = new ArrayList<>();
    String orderId = "", orderStatus = "", userID = "";

    TextView txt_orderId, txt_orderStatus;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_list);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá đơn hàng");

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("order_id")) {
            orderId = intent.getStringExtra("order_id");
        }
        Log.d("GET ORDER ID", "orderId: " +orderId);
        txt_orderId = findViewById(R.id.txt_orderID);
        txt_orderId.setText(orderId);

        viewModel = new OrderFeedbackListVM(orderId, this);

        recyclerViewOrderFeedBack();

    }

    private void recyclerViewOrderFeedBack() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerFeedbackList);
        recyclerViewList.setLayoutManager(linearLayoutManager);


        //Lấy danh sách từ viewModel
        viewModel.getOrderDetailListLiveData().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderDetails) {
                for(OrderItem i : orderDetails){
                    productList.add(i);
                }
                viewModel.getUserIDLiveData().observe((LifecycleOwner) context, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        userID = s;
                        Log.d("get User ID: ", userID);
                        adapter = new FeedbackItemAdapter(productList, userID);
                        recyclerViewList.setAdapter(adapter);
                    }
                });

            }
        });
        txt_orderStatus = findViewById(R.id.txt_order_status);
        txt_orderStatus.setText("");
        viewModel.getOrderStatusLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                orderStatus = s;
                txt_orderStatus.setText(orderStatus);
            }
        });


    }
}