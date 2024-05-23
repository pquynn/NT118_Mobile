package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.databinding.ActivityCheckoutBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    TextView screenName;
    FrameLayout btnBack;
    private Map<String, OrderItem> orderItemMap;
    private String userId = "3", orderId = "4", couponId = "";
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private OrderDetailAdapter adapter;
    private static final int COUPON_REQUEST_CODE = 1;
    private static final int ADDRESS_REQUEST_CODE = 2;
    private static final int PAYMENT_REQUEST_CODE = 3;
    private Coupon selectedCoupon;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        binding.setLifecycleOwner(this);

        if(getIntent().getExtras() != null){
            orderId = getIntent().getExtras().getString("orderId");
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Xác nhận đơn hàng");

        // order item map for adapter
        orderItemMap = new HashMap<>();
        adapter = new OrderDetailAdapter(orderItemMap);
        binding.recyclerViewOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewOrderDetail.setAdapter(adapter);

        viewModel = new CheckoutViewModel(userId, orderId, this);
        viewModel.getOrderLiveData().observe(this, order -> {
            binding.setCheckoutVM(viewModel);
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });



        // set button back click eventa
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // set button ChangeAddress click event
        binding.btnChangeAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CheckoutAddressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            bundle.putString("addressId", viewModel.getUserAddressLiveData().getValue().getId());
            intent.putExtras(bundle);
            startActivityForResult(intent, ADDRESS_REQUEST_CODE);
        });

        // set button SeePayment click event
        binding.btnSeePayment.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("paymentMethod", viewModel.getPaymentMethodLiveData().getValue());
            intent.putExtras(bundle);
            startActivityForResult(intent, PAYMENT_REQUEST_CODE);
        });

        // set button SeeCoupons click event
        binding.btnSeeCoupons.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CouponActivity.class);
            orderId = viewModel.getOrderLiveData().getValue().getId();
            int orderPrice = viewModel.getOrderPriceLiveData().getValue();
            Bundle bundle = new Bundle();
            bundle.putString("orderId", orderId);
            bundle.putInt("orderPrice", orderPrice);
            bundle.putString("couponId", couponId);
            bundle.putDouble("discountValue", viewModel.getDiscountValueLiveData().getValue());
            intent.putExtras(bundle);
            startActivityForResult(intent, COUPON_REQUEST_CODE);
        });


        // set Switch choose point check event
        binding.btnChoosePoint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.getPointTotalLiveData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    viewModel.loadPointUsed(integer, isChecked);
                }
            });

        });

        //start: button buy click event
        context = this;
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(context);
            }
        });


        //end: button buy click event
    }

    // method to show alert dialog when click btn buy
    public void showAlertDialog(Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//        alert.setTitle("Mua hàng");
        alert.setMessage("Xác nhận đặt mua hàng?");
        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                viewModel.checkout();
            }
        });

        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    // method to get result from activity through intent (activity2 -> activity1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GET DATA FROM COUPON ACTIVITY
        if (requestCode == COUPON_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("couponId")) {
                // set discount value if coupon is chosen
                viewModel.getCouponLiveData(data.getStringExtra("couponId"))
                        .observe(this, new Observer<Coupon>() {
                            @Override
                            public void onChanged(Coupon coupon) {
                                couponId = coupon.getIdCoupon();
                                viewModel.loadDiscountValue(coupon.getDiscountValue());
                                // Update the ViewModel or UI with the new coupon information
                            }
                        });
            }
        }
        //GET DATA FROM ADDRESS ACTIVITY
        else if (requestCode == ADDRESS_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("addressId")) {
                String addressId = data.getStringExtra("addressId");
                viewModel.loadUserAddress(addressId);
            }
        }
        //GET DATA FROM PAYMENT METHOD ACTIVITY
        else if (requestCode == PAYMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("paymentMethod")) {
                String paymentMethod = data.getStringExtra("paymentMethod");
                viewModel.loadPaymentMethod(paymentMethod);
            }
        }
    }
}
