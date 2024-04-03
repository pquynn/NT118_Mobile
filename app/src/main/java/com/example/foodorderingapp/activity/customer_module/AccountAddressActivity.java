package com.example.foodorderingapp.activity.customer_module;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.AccountAddressRefactor;
import com.example.foodorderingapp.adapter.CheckoutAddressRefactor;
import com.example.foodorderingapp.domain.AddressDomain;

import java.util.ArrayList;

public class AccountAddressActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_address);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Địa chỉ của tôi");

        recyclerViewAddress();
    }

    private void recyclerViewAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewAddress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<AddressDomain> addresses = new ArrayList<AddressDomain>();
        addresses.add(new AddressDomain("28 đường Nguyễn Văn Quỳ phường PT quận 7"));
        addresses.add(new AddressDomain("29 đường Nguyễn Văn Quỳ phường PT quận 7"));
        addresses.add(new AddressDomain("20 đường Nguyễn Văn Quỳ phường PT quận 7"));

        adapter = new AccountAddressRefactor(addresses);
        recyclerViewList.setAdapter(adapter);
    }
}