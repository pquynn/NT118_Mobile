package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.databinding.ActivityCheckoutAddressBinding;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.AM_AddAddressActivity;
import com.example.foodorderingapp.ui.adapter.CheckoutAddressAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutAddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class CheckoutAddressActivity extends AppCompatActivity implements CheckoutAddressAdapter.OnItemClickListener {
    private String addressId;
    private UserAddress selectedAddress;
    private List<UserAddress> userAddressList;
    private CheckoutAddressAdapter adapter;
    private ActivityCheckoutAddressBinding binding;
    private CheckoutAddressViewModel viewModel;
    private FrameLayout btnBack;
    private TextView screenName;
    String userId;
    private static final int REQUEST_ADD_ADDRESS = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout_address);
        binding.setLifecycleOwner(this);

        // get data through Intent
        if (getIntent().getExtras() != null) {
            userId = getIntent().getExtras().getString("userId");
            addressId = getIntent().getExtras().getString("addressId");
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Thay đổi địa chỉ nhận hàng");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // init viewmodel
        viewModel = new CheckoutAddressViewModel(userId, this);

//        // init adapter
        userAddressList = new ArrayList<>();
        selectedAddress = new UserAddress();
        selectedAddress.setId("");

        adapter = new CheckoutAddressAdapter(selectedAddress, userAddressList, viewModel, this::onItemClick);
        binding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAddress.setAdapter(adapter);

        // Observe the selected address
        viewModel.getSelectedAddressLiveData(addressId).observe(this, userAddress -> {
            if(userAddress != null) {
                selectedAddress = userAddress;
                adapter.setSelectedAddress(selectedAddress); // Update the adapter with the new selected address
                adapter.notifyDataSetChanged();
            }
        });

        // observe change in user address list
        viewModel.getUserAddressesLiveData().observe(this, userAddresses -> {
            userAddressList.clear();
            if(userAddresses != null && !userAddresses.isEmpty()){
                userAddressList.addAll(userAddresses);
                binding.btnAdd.setEnabled(true);
            }
            else{
                binding.btnAdd.setEnabled(false);
            }
            adapter.notifyDataSetChanged();

        });

        // button add click event
        binding.btnAdd.setOnClickListener(v -> {
            if(selectedAddress == null || selectedAddress.getId().isEmpty()){
                Toast.makeText(this, "Bạn chưa chọn địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
            }
            else{
                // Create an intent to hold the address data
                Intent resultIntent = new Intent();
                resultIntent.putExtra("addressId", selectedAddress.getId());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // button add address click event --> open add adress activity
        binding.btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AM_AddAddressActivity.class);
            intent.putExtra("user_id", userId);
            startActivityIfNeeded(intent, REQUEST_ADD_ADDRESS);
        });

    }

    // on viewholder click event
    @Override
    public void onItemClick(UserAddress address) {
        selectedAddress = address;
        viewModel.getSelectedAddressLiveData().setValue(address);
    }

    // on activity result from add address activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @javax.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_ADDRESS && resultCode == RESULT_OK) {
            viewModel.loadUserAddressList(userId);

        }
    }

}
