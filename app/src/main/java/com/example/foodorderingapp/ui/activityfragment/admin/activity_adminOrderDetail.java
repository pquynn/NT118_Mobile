package com.example.foodorderingapp.ui.activityfragment.admin;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.data.model.OrderDetail;

import java.util.ArrayList;

public class activity_adminOrderDetail extends AppCompatActivity {

    TextView txtStatus;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ImageView btnBack;
    private Button btnConfirm, btnDelivering, btnDelivered;

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

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();
//        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "50% đường", 3));
//        productList.add(new OrderDetail("Bánh", 60000, "Lớn", "a", 2));
        adapter = new OrderDetailAdapter(productList);
        recyclerViewList.setAdapter(adapter);

        txtStatus = findViewById(R.id.txtStatus);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnDelivering = findViewById(R.id.btnDelivering);
        btnDelivered = findViewById(R.id.btnDelivered);

        checkOrderStatus(String.valueOf(txtStatus.getText()));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái và các nút
                txtStatus.setText("Đã xác nhận");
                checkOrderStatus(String.valueOf(txtStatus.getText()));

            }
        });

        btnDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái và các nút
                txtStatus.setText("Giao hàng");
                checkOrderStatus(String.valueOf(txtStatus.getText()));

            }
        });

        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật trạng thái và các nút
                txtStatus.setText("Hoàn tất");
                checkOrderStatus(String.valueOf(txtStatus.getText()));

            }
        });
    }

    private void checkOrderStatus(String status) {
        switch (status) {
            case "Mới":  // Trường hợp đơn hàng mới, chưa được xác nhận
                btnConfirm.setVisibility(View.VISIBLE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                break;
            case "Đã xác nhận":  // Trường hợp đơn hàng đã được xác nhận
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.VISIBLE);
                btnDelivered.setVisibility(View.GONE);
                break;
            case "Giao hàng":  // Trường hợp đơn hàng dang được giao
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.VISIBLE);
                break;
            case "Hoàn tất":  // Trường hợp đơn hành đã giao thành công
                btnConfirm.setVisibility(View.GONE);
                btnDelivering.setVisibility(View.GONE);
                btnDelivered.setVisibility(View.GONE);
                break;
        }

    }
}