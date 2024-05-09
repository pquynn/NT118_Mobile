package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.CheckoutAddressAdapter;
import com.example.foodorderingapp.data.model.entity.UserAddress;

import java.util.ArrayList;

public class CheckoutAddressActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private TextView screenName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_address);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Thay đổi địa chỉ nhận hàng");

        recyclerViewAddress();

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void recyclerViewAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewAddress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<UserAddress> addresses = new ArrayList<UserAddress>();
        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 7", "Nguyễn A", "0123456789"));
        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 8", "Nguyễn A", "0123456789"));
        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 9", "Nguyễn A", "0123456789"));

        adapter = new CheckoutAddressAdapter(addresses);
        recyclerViewList.setAdapter(adapter);
    }
}