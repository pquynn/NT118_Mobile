package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.databinding.ActivityCouponBinding;
import com.example.foodorderingapp.ui.adapter.CouponAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.coupon.CouponViewModel;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class CouponActivity extends AppCompatActivity implements CouponAdapter.OnItemClickListener {
    private String orderId;
    private int orderPrice;
    private CouponAdapter adapter;
    private FrameLayout btnBack;
    private TextView screenName;

    private ActivityCouponBinding binding;
    private CouponViewModel viewModel;
    private List<Coupon> couponList;

    private Boolean isSelected = false;
    private Coupon selectedCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon);
        binding.setLifecycleOwner(this);

        // get order id, order price through Intent
        if (getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString("orderId");
            orderPrice = getIntent().getExtras().getInt("orderPrice");
            selectedCoupon = new Coupon();
            selectedCoupon.setIdCoupon(getIntent().getExtras().getString("couponId"));
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn mã khuyến mãi");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        viewModel = new CouponViewModel(orderId, orderPrice, this);
        couponList = new ArrayList<>();
        adapter = new CouponAdapter(selectedCoupon, couponList, viewModel, this::onItemClick);
        binding.recyclerViewCoupon.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCoupon.setAdapter(adapter);

        // get coupon list live data
        viewModel.getCouponListMutableLiveData().observe(this, new Observer<List<Coupon>>() {
            @Override
            public void onChanged(List<Coupon> coupons) {
                binding.setCouponVM(viewModel);
                couponList.clear();
                couponList.addAll(coupons);
                adapter.notifyDataSetChanged();
            }
        });

        //set isSelected and discountvalue live data
        if(!selectedCoupon.getIdCoupon().isEmpty()){
            viewModel.getIsSelectedLiveData().setValue(true);
            viewModel.getDiscountValueLiveData().setValue( -1 * (int)(orderPrice * selectedCoupon.getDiscountValue()/100.0));
        }
        else{
            viewModel.getIsSelectedLiveData().setValue(false);
        }

        // observe change in isSelected live data to hide or show discount info bar
//        viewModel.getIsSelectedLiveData().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if(aBoolean)
//                    binding.selectedCouponBox.setVisibility(View.VISIBLE);
//                else
//                    binding.selectedCouponBox.setVisibility(View.GONE);
//            }
//        });


        binding.btnAdd.setOnClickListener(v -> {
            if (!viewModel.getIsSelectedLiveData().getValue()) {
                Toast.makeText(getBaseContext(), "Bạn chưa chọn mã giảm giá!", Toast.LENGTH_SHORT).show();
            } else {
                // Create an intent to hold the coupon data
                Intent resultIntent = new Intent();
                resultIntent.putExtra("couponId", selectedCoupon.getIdCoupon());
                setResult(RESULT_OK, resultIntent);
                finish(); // Close the CouponActivity
            }
        });
    }

    @Override
    public void onItemClick(Boolean isSelected, Coupon selectedCoupon) {
        viewModel.getIsSelectedLiveData().setValue(isSelected);
        viewModel.getDiscountValueLiveData().setValue( -1 * (int)(orderPrice * selectedCoupon.getDiscountValue()/100.0));
        this.selectedCoupon = selectedCoupon;
    }
}
