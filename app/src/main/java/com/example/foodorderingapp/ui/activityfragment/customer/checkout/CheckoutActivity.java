package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
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
    private String userId = "3", orderId, couponId = "";
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private OrderDetailAdapter adapter;
    private static final int COUPON_REQUEST_CODE = 1;
    private Coupon selectedCoupon;

    @Override
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
        viewModel.getOrderLiveData().observe(this, order -> {
            binding.setCheckoutVM(viewModel);
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // set button ChangeAddress click event
        binding.btnChangeAddress.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CheckoutAddressActivity.class)));

        // set button SeePayment click event
        binding.btnSeePayment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class)));

        // set button SeeCoupons click event
        binding.btnSeeCoupons.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CouponActivity.class);
            orderId = viewModel.getOrderLiveData().getValue().getId();
            int orderPrice = viewModel.getOrderPriceLiveData().getValue();
            Bundle bundle = new Bundle();
            bundle.putString("orderId", orderId);
            bundle.putInt("orderPrice", orderPrice);
            bundle.putString("couponId", couponId);
            intent.putExtras(bundle);
            startActivityForResult(intent, COUPON_REQUEST_CODE);
        });


        // set button Buy click event
        binding.btnBuy.setOnClickListener(v -> {
            // Handle the buy action here
        });

        // set Switch choose point check event
        binding.btnChoosePoint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int totalPoint = viewModel.getPointTotalLiveData().getValue();
            viewModel.loadPointUsed(totalPoint, isChecked);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COUPON_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("couponId")) {
                // set discount value if coupon is chosen
                viewModel.getCouponLiveData(data.getStringExtra("couponId"))
                        .observe(this, new Observer<Coupon>() {
                    @Override
                    public void onChanged(Coupon coupon) {
                        Log.d("firestore", " coupon onActivityResult: " + coupon.getIdCoupon().toString());
                        couponId = coupon.getIdCoupon();
                    }
                });
                Log.d("firestore", "onActivityResult: " + couponId);
            }
        }
    }
}
