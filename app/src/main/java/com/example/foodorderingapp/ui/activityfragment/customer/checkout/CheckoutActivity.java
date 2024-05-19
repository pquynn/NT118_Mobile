package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.databinding.ActivityCheckoutBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    TextView screenName;
    FrameLayout btnBack;
    private Map<String, OrderItem> orderItemMap;
    private String userId ="3", orderId;
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private OrderDetailAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        binding.setLifecycleOwner(this);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Xác nhận đơn hàng");

        // order item map for adapter
        orderItemMap = new HashMap<>();
        adapter = new OrderDetailAdapter(orderItemMap);
        binding.recyclerViewOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewOrderDetail.setAdapter(adapter);

        viewModel = new CheckoutViewModel(userId, this);
//        orderId = viewModel.getOrderLiveData().getValue().getId();
        viewModel.getOrderLiveData().observe(this, order -> {
            binding.setCheckoutVM(viewModel);

            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set button ChangeAddress click event
        binding.btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckoutAddressActivity.class));
            }
        });

        // set button SeePayment click event
        binding.btnSeePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class));
            }
        });

        // set button SeeCoupons click event
        binding.btnSeeCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CouponActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("orderId", orderId);
//                bundle.putInt("orderPrice",100000); //todo: đổi thành order price sau
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // set button Buy click event
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // set Switch choose point check event
        binding.btnChoosePoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int totalPoint = viewModel.getPointTotalLiveData().getValue();
                viewModel.loadPointUsed(totalPoint, isChecked);
            }
        });


    }

}