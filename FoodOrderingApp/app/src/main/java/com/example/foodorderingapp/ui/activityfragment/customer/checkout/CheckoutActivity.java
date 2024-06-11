package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.SelectLocation;
import com.example.foodorderingapp.ui.adapter.OrderDetailAdapter;
import com.example.foodorderingapp.databinding.ActivityCheckoutBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPaySDK;

public class CheckoutActivity extends AppCompatActivity {
    TextView screenName;
    FrameLayout btnBack;
    private Map<String, OrderItem> orderItemMap;
    private String orderId = "4", couponId = "";
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private OrderDetailAdapter adapter;
    private static final int COUPON_REQUEST_CODE = 1;
    private static final int ADDRESS_REQUEST_CODE = 2;
    private static final int PAYMENT_REQUEST_CODE = 3;
    private Coupon selectedCoupon;
    private Context context;
    String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            finish();
        }

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        binding.setLifecycleOwner(this);
        viewModel = new CheckoutViewModel(userId, this, CheckoutActivity.this);

        // init environment for zalopay and momo
        viewModel.initializeEnvironment();

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Xác nhận đơn hàng");

        // order item map for adapter
        orderItemMap = new HashMap<>();
        adapter = new OrderDetailAdapter(orderItemMap);
        binding.recyclerViewOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewOrderDetail.setAdapter(adapter);

        viewModel.getOrderLiveData().observe(this, order -> {
            binding.setCheckoutVM(viewModel);
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });

        viewModel.getCouponLiveData().observe(this, new Observer<Coupon>() {
            @Override
            public void onChanged(Coupon coupon) {
                if(coupon != null && coupon.getIdCoupon() != null)
                    couponId = coupon.getIdCoupon();
            }
        });

        // Trung: observe user address to calculate delivery cost
        // Observe user address to calculate delivery cost
        viewModel.getUserAddressLiveData().observe(this, new Observer<UserAddress>() {
            @Override
            public void onChanged(UserAddress userAddress) {
                calculateDeliveryCost(userAddress);
            }
        });
        // observe if delivery cost is change --> update order price
        viewModel.getDeliveryCostLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewModel.getOrderPriceLiveData().setValue(viewModel.calculateOrderPrice());
            }
        });

        // set button back click eventa
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> showBackToCartAlert(context));

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

        //on back pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                showBackToCartAlert(context);
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }


    //method to show alert dialog when click btn back
    public void showBackToCartAlert(Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Bạn muốn quay về giỏ hàng?");
        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                turnBack();
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

    //start: calculate delivery cost
    private void calculateDeliveryCost(UserAddress userAddress) {
        String location = userAddress.getAddressDetail() + "," + userAddress.getWard() + "," + userAddress.getDistrict() + "," + userAddress.getCity();
        new Thread(() -> {
            Geocoder geocoder = new Geocoder(CheckoutActivity.this);
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show());
                return;
            }
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0); // Get the first address
                // Calculate distance between two points
                double distance = haversine(10.8700122, 106.802871, address.getLatitude(), address.getLongitude());

                // Calculate delivery cost
                double deliveryCost = (distance >= 1) ? (distance * 10000) : 0;

                runOnUiThread(() -> viewModel.getDeliveryCostLiveData().setValue((int) deliveryCost));
            } else {
                runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Address not found", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
    private static final double EARTH_RADIUS = 6371.0; // Đơn vị đo: km

    public static double haversine(double latA, double lonA, double latB, double lonB) {
        double dLat = Math.toRadians(latB - latA);
        double dLon = Math.toRadians(lonB - lonA);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
    //end: calculate delivery cost


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
        //GET DATA FROM MOMO PAYMENT GATEWAY
        else if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("momo", "momo thanh toan thanh cong " + data.getStringExtra("message"));
//                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("momo", "khong thanh cong");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("momo", "khong thanh cong");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("momo", "khong thanh cong");
                } else {
                    //TOKEN FAIL
                    Log.d("momo", "khong thanh cong");
                }
            } else {
                Log.d("momo", "khong thanh cong");
            }
        } else {
            Log.d("momo", "khong thanh cong");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    protected void turnBack(){
        // Create an intent to hold the coupon data
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
