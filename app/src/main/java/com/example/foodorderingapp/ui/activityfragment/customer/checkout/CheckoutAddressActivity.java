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
import com.example.foodorderingapp.databinding.ActivityCheckoutAddressBinding;
import com.example.foodorderingapp.ui.adapter.CheckoutAddressAdapter;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutAddressViewModel;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckoutAddressActivity extends AppCompatActivity {
    private String userId = "3";
    private List<UserAddress> userAddressList;
    private CheckoutAddressAdapter adapter;
    private ActivityCheckoutAddressBinding binding;
    private CheckoutAddressViewModel viewModel;
    private FrameLayout btnBack;
    private TextView screenName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout_address);
        binding.setLifecycleOwner(this);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Thay đổi địa chỉ nhận hàng");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // adapter
        userAddressList = new ArrayList<>();
        adapter = new CheckoutAddressAdapter(userAddressList, viewModel);
        binding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAddress.setAdapter(adapter);

        // viewmodel
        viewModel = new CheckoutAddressViewModel(userId, this);
        viewModel.getUserAddressesLiveData().observe(this, new Observer<ArrayList<UserAddress>>() {
            @Override
            public void onChanged(ArrayList<UserAddress> userAddresses) {
                userAddressList.clear();
                userAddressList.addAll(userAddresses);
                adapter.notifyDataSetChanged();
            }
        });

    }
}