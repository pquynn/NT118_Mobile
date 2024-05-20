package com.example.foodorderingapp.ui.activityfragment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.ui.AdminOrderDetailVM;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class activity_adminOrderDetail extends AppCompatActivity {

    private Map<String, OrderItem> orderItemMap;
    private TextView txtStatus, txtCustomerName, txtCustomerPhonne, txtAddress, txtPayment, txtViewTotalProduct, txtTotalProduct, txtCoupon, txtIntoPrice, txtPoint;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private AdminOrderDetailVM adminOrderDetailVM;
    private ImageView btnBack;
    private Button btnConfirm, btnDelivering, btnDelivered;
    private String orderID, orderStatus;

    private static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_order_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    public void init() {
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("CHI TIẾT ĐƠN HÀNG");

        btnBack = findViewById(R.id.img_lessthan);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerProductList);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        orderItemMap = new HashMap<>();
        adapter = new OrderDetailAdapter(orderItemMap);
        recyclerViewList.setAdapter(adapter);

        txtStatus = findViewById(R.id.txtStatus);
        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtCustomerPhonne = findViewById(R.id.txtCustomerPhonne);
        txtAddress = findViewById(R.id.txtAddress);
        txtPayment = findViewById(R.id.txtPayment);
        txtViewTotalProduct = findViewById(R.id.txtViewTotalProduct);
        txtTotalProduct = findViewById(R.id.txtTotalPrice);
        txtCoupon = findViewById(R.id.txtCoupon);
        txtIntoPrice = findViewById(R.id.txtIntoPrice);
        txtPoint = findViewById(R.id.txtPoint);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnDelivering = findViewById(R.id.btnDelivering);
        btnDelivered = findViewById(R.id.btnDelivered);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("order_id")) {
                orderID = intent.getStringExtra("order_id");
            }
        }

        adminOrderDetailVM = new AdminOrderDetailVM(orderID);

        adminOrderDetailVM.getOrder().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                orderStatus = order.getStatus();

                // Gán dữ liệu cho giao diện
                txtStatus.setText(orderStatus);
                txtCustomerName.setText(order.getRecipientName());
                txtCustomerPhonne.setText(order.getRecipientPhone());
                txtAddress.setText(order.getAddress());
                txtPayment.setText(order.getPayment());
                txtViewTotalProduct.setText("TỔNG CỘNG(" + order.getTotalProduct() + " MÓN)");
                txtTotalProduct.setText(formatNumber(order.getTotalPrice()) + " Đ");
                txtCoupon.setText(formatNumber(order.getDiscountValue()) + " Đ");
                txtPoint.setText(formatNumber(order.getPoint()));
                txtIntoPrice.setText(formatNumber(order.getOrderPrice()) + " Đ");

                orderItemMap.clear();
                orderItemMap.putAll(order.getOrderItem());

                adapter.notifyDataSetChanged();

                // Kiểm tra trạng thái hóa đơn để hiện ra nút bấm phù hợp
                checkOrderStatus(orderStatus);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái và các nút
                orderStatus = "Đã xác nhận";
                adminOrderDetailVM.updateOrderStatusByID(orderID, orderStatus, getApplicationContext());
                txtStatus.setText(orderStatus);
                checkOrderStatus(orderStatus);
            }
        });

        btnDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái và các nút
                orderStatus = "Đang giao";
                adminOrderDetailVM.updateOrderStatusByID(orderID, orderStatus, getApplicationContext());
                txtStatus.setText(orderStatus);
                checkOrderStatus(orderStatus);
            }
        });

        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái và các nút
                orderStatus = "Đã giao";
                adminOrderDetailVM.updateOrderStatusByID(orderID, orderStatus, getApplicationContext());
                txtStatus.setText(orderStatus);
                checkOrderStatus(orderStatus);
            }
        });
    }

    private void checkOrderStatus(String status) {
        switch (status) {
            case "Chờ xác nhận":  // Trường hợp đơn hàng mới, chưa được xác nhận
                btnConfirm.setVisibility(View.VISIBLE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                break;
            case "Đã xác nhận":  // Trường hợp đơn hàng đã được xác nhận
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.VISIBLE);
                btnDelivered.setVisibility(View.GONE);
                break;
            case "Đang giao":  // Trường hợp đơn hàng dang được giao
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.VISIBLE);
                break;
            case "Đã giao":  // Trường hợp đơn hành đã giao thành công
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                break;
        }
    }
}