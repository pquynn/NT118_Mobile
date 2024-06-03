package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
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
import com.example.foodorderingapp.ui.adapter.CheckoutAddressAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutAddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class CheckoutAddressActivity extends AppCompatActivity implements CheckoutAddressAdapter.OnItemClickListener {
    private String userId, addressId;
    private UserAddress selectedAddress;
    private List<UserAddress> userAddressList;
    private CheckoutAddressAdapter adapter;
    private ActivityCheckoutAddressBinding binding;
    private CheckoutAddressViewModel viewModel;
    private FrameLayout btnBack;
    private TextView screenName;

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

        // init adapter
        userAddressList = new ArrayList<>();
        selectedAddress = new UserAddress();
        selectedAddress.setId("");

        adapter = new CheckoutAddressAdapter(selectedAddress, userAddressList, viewModel, this::onItemClick);
        binding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAddress.setAdapter(adapter);

        // Observe the selected address
        if (addressId != null && !addressId.isEmpty()) {
            viewModel.getSelectedAddressLiveData(addressId).observe(this, userAddress -> {
                selectedAddress = userAddress;
                adapter.setSelectedAddress(selectedAddress); // Update the adapter with the new selected address
                adapter.notifyDataSetChanged();
            });
        } else {
            UserAddress userAddress = new UserAddress();
            userAddress.setId("");
            viewModel.getSelectedAddressLiveData().setValue(userAddress);
        }

        // observe change in user address list
        viewModel.getUserAddressesLiveData().observe(this, userAddresses -> {
            userAddressList.clear();
            userAddressList.addAll(userAddresses);
            adapter.notifyDataSetChanged();
        });

        // button add click event
        binding.btnAdd.setOnClickListener(v -> {
            // Create an intent to hold the address data
            Intent resultIntent = new Intent();
            resultIntent.putExtra("addressId", selectedAddress.getId());
            setResult(RESULT_OK, resultIntent);
            finish();

        });
    }

    // on viewholder click event
    @Override
    public void onItemClick(UserAddress address) {
        selectedAddress = address;
        viewModel.getSelectedAddressLiveData().setValue(address);
    }
}
