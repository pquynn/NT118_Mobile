package com.example.foodorderingapp.activity.customer_module.checkout;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.AddressAdapter;
import com.example.foodorderingapp.domain.AddressDomain;

import java.util.ArrayList;

public class CheckoutAddressActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_address);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Thay đổi địa chỉ nhận hàng");

        recyclerViewAddress();
    }

    private void recyclerViewAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewAddress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<AddressDomain> addresses = new ArrayList<AddressDomain>();
        addresses.add(new AddressDomain("25 đường Nguyễn Văn Quỳ phường PT quận 7"));
        addresses.add(new AddressDomain("26 đường Nguyễn Văn Quỳ phường PT quận 7"));
        addresses.add(new AddressDomain("27 đường Nguyễn Văn Quỳ phường PT quận 7"));

        adapter = new AddressAdapter(addresses);
        recyclerViewList.setAdapter(adapter);
    }
}