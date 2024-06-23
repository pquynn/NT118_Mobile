package com.example.foodorderingapp.ui.activityfragment.customer.refund;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Refund;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.databinding.ActivityRefundViewBinding;
import com.example.foodorderingapp.ui.adapter.RefundItemAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.refund.RefundViewModel;

import java.util.HashMap;
import java.util.Map;

public class RefundViewActivity extends AppCompatActivity {
    private RefundItemAdapter adapter;
    private Map<String, RefundItem> refundItemMap;
    private Map<String, OrderItem> orderItemMap;
    private FrameLayout btnBack;
    private TextView screenName;
    private String orderId = "3";
    private RefundViewModel viewModel;
    private ActivityRefundViewBinding binding;
    private AlertDialog progressDialog;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_view);
        binding.setLifecycleOwner(this);

        // Create AlertDialog with ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false); // Prevents dialog from being dismissed
        progressDialog = builder.create();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Get order id, user id through intent
        if (getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString("orderId");
        }

        // Set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chi tiết hoàn tiền");

        // Set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // Init adapter
        refundItemMap = new HashMap<>();
        orderItemMap = new HashMap<>();
        adapter = new RefundItemAdapter(orderItemMap, refundItemMap, this);
        binding.recyclerViewRefundProgress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRefundProgress.setAdapter(adapter);

        // Init view model
        viewModel = new RefundViewModel(orderId, this, this);

        // Show progress dialog
        progressDialog.show();

        // Observe changes in refund live data
        viewModel.getRefundLiveData().observe(this, new Observer<Refund>() {
            @Override
            public void onChanged(Refund refund) {
                binding.setRefundVM(viewModel);
                refundItemMap.clear();
                refundItemMap.putAll(refund.getRefundItemMap());
                adapter.notifyDataSetChanged(); // Notify adapter to refresh data
                progressDialog.dismiss();
            }
        });

        // Observe changes in order live data
        viewModel.getOrderLiveData().observe(this, order -> {
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });
    }
}
