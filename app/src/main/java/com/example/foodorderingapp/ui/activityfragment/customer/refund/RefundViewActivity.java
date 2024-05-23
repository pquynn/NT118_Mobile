package com.example.foodorderingapp.ui.activityfragment.customer.refund;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
    private String orderId = "3", userId = "";
    private RefundViewModel viewModel;
    private ActivityRefundViewBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_view);
        binding.setLifecycleOwner(this);

        // get order id, user id through intent
        if(getIntent().getExtras() != null){
            orderId = getIntent().getExtras().getString("orderId");
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chi tiết hoàn tiền");

        // set button back click event
        // todo: nút back chỉ trở về trang order detail thoi
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // init adapter
        refundItemMap = new HashMap<>();
        orderItemMap = new HashMap<>();
        adapter = new RefundItemAdapter(orderItemMap, refundItemMap, this);
        binding.recyclerViewRefundProgress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRefundProgress.setAdapter(adapter);

        viewModel = new RefundViewModel(orderId, this);
        // observe change in refund live data
        viewModel.getRefundLiveData().observe(this, new Observer<Refund>() {
            @Override
            public void onChanged(Refund refund) {
                binding.setRefundVM(viewModel);
                refundItemMap.clear();
                refundItemMap.putAll(refund.getRefundItemMap());
            }
        });

        // observe change in order live data
        viewModel.getOrderLiveData().observe(this, order -> {
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });
    }
}
