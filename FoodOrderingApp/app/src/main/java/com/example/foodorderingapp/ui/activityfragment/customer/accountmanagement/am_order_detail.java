package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.MyOrderDetailVM;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class am_order_detail extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private Context context = this;
    private RecyclerView recyclerViewList;
    MyOrderDetailVM viewModel;
    Map<String, OrderItem> orderItemMap;
    Button btnCancel, btnFeedback, btnRefund;
    TextView txt_orderId, txt_orderStatus;
    TextView txt_cusName, txt_cusPhone, txt_cusAddress, txt_Payment;
    TextView txt_orderTotal, txt_orderDiscount, txt_orderPoint, txt_orderPrice;
    LinearLayout ll_orderDiscount, ll_orderPoint;
    String orderId = "", orderStatus = "";
    AlertDialog progressDialog;
    FrameLayout btnBack;
    OrderRepository orderRepository = new OrderRepository();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_order_detail);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chi tiết đơn hàng");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txt_orderId = findViewById(R.id.txt_orderDT_ID);
        txt_orderStatus = findViewById(R.id.txt_orderDT_status);

        txt_cusName = findViewById(R.id.txtDT_cusName);
        txt_cusPhone = findViewById(R.id.txtDT_cusPhone);
        txt_cusAddress = findViewById(R.id.txtDT_cusAddress);
        txt_Payment = findViewById(R.id.txtDT_payment);

        txt_orderTotal = findViewById(R.id.txt_orderDT_total);
        txt_orderDiscount = findViewById(R.id.txt_orderDT_discount);
        txt_orderPoint = findViewById(R.id.txt_order_discount_point);
        txt_orderPrice = findViewById(R.id.txt_orderDT_price);

        ll_orderDiscount = findViewById(R.id.ll_orderDiscount);
        ll_orderDiscount.setVisibility(View.GONE);
        ll_orderPoint = findViewById(R.id.ll_orderPoint);
        ll_orderPoint.setVisibility(View.GONE);

        btnCancel = findViewById(R.id.btn_order_cancel);
        btnFeedback = findViewById(R.id.btn_order_feedback);
        btnRefund = findViewById(R.id.btn_order_refund);


        //Get order_id in Intent
        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("order_id")){
                orderId = intent.getStringExtra("order_id");
            }
        }
        Log.d("GET ORDER ID", "orderId: " +orderId);
        txt_orderId.setText(orderId);



        //
        viewModel = new MyOrderDetailVM(orderId, this);

        recyclerViewOrderDetail();
        txt_orderStatus.setText("");

        progressDialog.show();
        viewModel.getOrderStatusLiveDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                orderStatus = s;
                txt_orderStatus.setText(orderStatus);
                SetVisibilityButton(orderStatus);
                progressDialog.dismiss();
            }
        });


        progressDialog.show();
        viewModel.getOrderMutableLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                txt_cusAddress.setText(order.getAddress());
                txt_cusName.setText(order.getRecipientName());
                txt_cusPhone.setText(order.getRecipientPhone());
                txt_Payment.setText(order.getPayment());

                setPriceFormatted(txt_orderTotal, order.getTotalPrice());
                if(order.getPoint()>0){
                    ll_orderPoint.setVisibility(View.VISIBLE);
                    setPriceFormatted(txt_orderPoint, order.getPoint());
                }
                if(order.getDiscountValue() > 0){
                    ll_orderDiscount.setVisibility(View.VISIBLE);
                    setPriceFormatted(txt_orderDiscount, order.getDiscountValue());
                }
                progressDialog.dismiss();
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), am_feedback_list.class);
                myIntent.putExtra("order_id", orderId);
                startActivity(myIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(context)
                        .setTitle("Xác nhận hủy đơn hàng")
                        .setMessage("Bạn có chắc muốn hủy đơn hàng này?")
                        .setPositiveButton("Xác nhận hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orderRepository.updateOrderStatusById(orderId, "Đã hủy", new IOrderRepository.OrderChangedCallback() {
                                    @Override
                                    public void onOrderChanged() {
                                        Toast.makeText(context, "Đã hủy đơn hàng!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(context, "Hủy đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy bỏ", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
    private void SetVisibilityButton(String status){
        btnCancel.setVisibility(View.GONE);
        btnFeedback.setVisibility(View.GONE);
        btnRefund.setVisibility(View.GONE);

        switch (status) {
            case "Chờ xác nhận": //pending
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case "Đã giao": //completed
                btnFeedback.setVisibility(View.VISIBLE);
                btnRefund.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void recyclerViewOrderDetail() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerProductListDT);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        Log.d("order detail", "get in make order detail: " + orderId);
        orderItemMap = new HashMap<>();


        adapter = new OrderDetailAdapter(orderItemMap);
        recyclerViewList.setAdapter(adapter);
        progressDialog.show();
            viewModel.getOrderMutableLiveData().observe(this, new Observer<Order>() {
                @Override
                public void onChanged(Order order) {
                orderItemMap.clear();
                orderItemMap.putAll(order.getOrderItem());

                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                Log.d("Get in load detail", "GET IN LOAD DETAIL");
                }
            });
    }

    private void setPriceFormatted(TextView textView, int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        textView.setText(formattedPrice);
    }
}