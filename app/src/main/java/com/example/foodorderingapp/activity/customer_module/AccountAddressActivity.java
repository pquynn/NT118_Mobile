package com.example.foodorderingapp.activity.customer_module;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.AccountAddressAdapter;
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
        recyclerViewList = findViewById(R.id.recyclerViewCoupon);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<AddressDomain> addresses = new ArrayList<AddressDomain>();
        addresses.add(new AddressDomain("28 đường Nguyễn Văn Quỳ phường PT quận 7", "Nguyễn A", "0123456789"));
        addresses.add(new AddressDomain("28 đường Nguyễn Văn Quỳ phường PT quận 8", "Nguyễn A", "0123456789"));
        addresses.add(new AddressDomain("28 đường Nguyễn Văn Quỳ phường PT quận 9", "Nguyễn A", "0123456789"));

        adapter = new AccountAddressAdapter(addresses);
        recyclerViewList.setAdapter(adapter);
    }
}