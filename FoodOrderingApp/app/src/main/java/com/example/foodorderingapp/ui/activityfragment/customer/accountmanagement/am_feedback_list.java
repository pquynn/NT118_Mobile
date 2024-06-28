package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
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
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    TextView txt_orderId, txt_orderStatus;
    Context context = this;
    FrameLayout btnBack;
    AlertDialog progressDialog;
    private static final int REQUEST_CODE_FEEDBACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userID = sharedPreferences.getString(KEY_USER_ID, null);
        if (userID == null) {
            // User ID not found, handle this case
            finish();
            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_list);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá đơn hàng");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("order_id")) {
            orderId = intent.getStringExtra("order_id");
        }

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(true);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Log.d("GET ORDER ID", "orderId: " +orderId);
        txt_orderId = findViewById(R.id.txt_orderID);

        progressDialog.show();
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
                progressDialog.dismiss();

            }
        });
        txt_orderStatus = findViewById(R.id.txt_order_status);
        txt_orderStatus.setText("");
        viewModel.getOrderStatusLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                orderStatus = s;
                txt_orderStatus.setText(orderStatus);
                progressDialog.dismiss();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FEEDBACK && resultCode == RESULT_OK) {
            // Cập nhật lại RecyclerView khi người dùng hoàn thành đánh giá
            adapter.notifyDataSetChanged();
        }
    }

}