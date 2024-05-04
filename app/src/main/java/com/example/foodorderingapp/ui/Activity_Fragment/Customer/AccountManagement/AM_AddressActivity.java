package com.example.foodorderingapp.ui.Activity_Fragment.Customer.AccountManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.AccountAddressAdapter;
import com.example.foodorderingapp.data.model.entity.UserAddress;

import java.util.ArrayList;

public class AM_AddressActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private TextView screenName;
    private ImageView btnEdit;
    ArrayList<UserAddress> addresses;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_address);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Địa chỉ của tôi");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set button Edit address click event
        btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AM_AddAddressActivity.class));
            }
        });

        recyclerViewAddress();
    }

    private void recyclerViewAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewAddress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        addresses = new ArrayList<UserAddress>();
        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 7", "Nguyễn A", "0123456789"));
        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 8", "Nguyễn A", "0123456789"));
        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 9", "Nguyễn A", "0123456789"));

        adapter = new AccountAddressAdapter(addresses);
        recyclerViewList.setAdapter(adapter);
    }
}