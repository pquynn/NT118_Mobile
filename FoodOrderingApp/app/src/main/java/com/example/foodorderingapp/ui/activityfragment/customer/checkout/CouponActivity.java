package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ActivityCheckoutBinding;
import com.example.foodorderingapp.databinding.ActivityCouponBinding;
import com.example.foodorderingapp.ui.adapter.CouponAdapter;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel;
import com.example.foodorderingapp.ui.viewmodel.customer.coupon.CouponViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponActivity extends AppCompatActivity {
    private String orderId = "4";
    private int orderPrice = 100000;
    private CouponAdapter adapter;
    private FrameLayout btnBack;
    private TextView screenName;

    private ActivityCouponBinding binding;
    private CouponViewModel viewModel;
    private List<Coupon> couponList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon);
        binding.setLifecycleOwner(this);

        // get order id, order price through Intent
//        orderId = getIntent().getExtras().getString("orderId");
//        orderPrice = getIntent().getExtras().getInt("orderPrice");

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn mã khuyến mãi");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        couponList = new ArrayList<>();
        adapter = new CouponAdapter(couponList, viewModel);
        binding.recyclerViewCoupon.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCoupon.setAdapter(adapter);


//        Coupon coupon = new Coupon(
//                "1", "ten", new Date(), new Date(), 0.6, 100000.0, 2.0, "mota"
//        );
//        adapter.notifyDataSetChanged();

        viewModel = new CouponViewModel(orderId, orderPrice, this);
        viewModel.getCouponListMutableLiveData().observe(this, new Observer<List<Coupon>>() {
            @Override
            public void onChanged(List<Coupon> coupons) {
                binding.setCouponVM(viewModel);
                couponList.clear();
                couponList.addAll(coupons);
                adapter.notifyDataSetChanged();
            }
        });

    }

}
