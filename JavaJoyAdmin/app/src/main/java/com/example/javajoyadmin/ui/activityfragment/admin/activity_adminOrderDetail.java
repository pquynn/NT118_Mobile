package com.example.javajoyadmin.ui.activityfragment.admin;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.Order;
import com.example.javajoyadmin.data.model.entity.OrderItem;
import com.example.javajoyadmin.ui.activityfragment.admin.refund.RefundProgressActivity;
import com.example.javajoyadmin.ui.activityfragment.authentication.activity_login;
import com.example.javajoyadmin.ui.viewmodel.admin.order.AdminOrderDetailVM;
import com.example.javajoyadmin.ui.adapter.OrderDetailAdapter;

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
    private Button btnConfirm, btnDelivering, btnDelivered, btnRefund;
    private String orderID, orderStatus;

    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";

    private static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            finish();
            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);
        }

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
        btnRefund = findViewById(R.id.btnRefund);

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

        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút được nhấn
                Intent intent = new Intent(getApplicationContext(), RefundProgressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void checkOrderStatus(String status) {
        switch (status) {
            case "Chờ xác nhận":  // Trường hợp đơn hàng mới, chưa được xác nhận
                btnConfirm.setVisibility(View.VISIBLE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                btnRefund.setVisibility(View.GONE);
                break;
            case "Đã xác nhận":  // Trường hợp đơn hàng đã được xác nhận
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.VISIBLE);
                btnDelivered.setVisibility(View.GONE);
                btnRefund.setVisibility(View.GONE);
                break;
            case "Đang giao":  // Trường hợp đơn hàng dang được giao
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.VISIBLE);
                btnRefund.setVisibility(View.GONE);
                break;
            case "Đã giao":  // Trường hợp đơn hàng đã giao thành công
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                btnRefund.setVisibility(View.GONE);
                break;
            case "Hoàn tiền": // Trường hợp đơn hàng hoàn tiền
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                btnRefund.setVisibility(View.VISIBLE);
                break;
            default: // Mặc định là các nút đều biến mất
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                btnRefund.setVisibility(View.GONE);
        }
    }
}