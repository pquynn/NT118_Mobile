package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.AccountAddressAdapter;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

import java.util.ArrayList;

public class AM_AddressActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FrameLayout btnBack;
    private TextView screenName;
    private ImageView btnEdit;
    Button btnAddAddress;
    private UserInfoVM viewModel;
    private String userId = "";
    ArrayList<UserAddress> addresses;
    @SuppressLint("WrongViewCast")
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

        // set button Edit address click event --> set in adapter
        btnEdit = findViewById(R.id.btn_edit);
        if (btnEdit == null) {
            Log.e(TAG, "btnEdit is null. Check the layout and ID."); // Debug thông tin để xác định vấn đề
        } else {
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), AM_AddAddressActivity.class));
                }
            });
        }
        btnAddAddress = findViewById(R.id.btn_addAddress);
        if(btnAddAddress == null){
            Log.e(TAG,"btnAddAddress is null. Check layout and ID");
        }else{
            btnAddAddress.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getApplicationContext(), AM_AddAddressActivity.class);
                    myIntent.putExtra("user_id", userId);
                    startActivity(myIntent);
                }
            }));
        }
        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }

        viewModel = new UserInfoVM(userId, this);
        recyclerViewAddress();
    }

    private void recyclerViewAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewAddress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        addresses = new ArrayList<>();
//        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 7", "Nguyễn A", "0123456789"));
//        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 8", "Nguyễn A", "0123456789"));
//        addresses.add(new UserAddress("28 đường Nguyễn Văn Quỳ phường PT quận 9", "Nguyễn A", "0123456789"));
        viewModel.getUserAddressesLiveData().observe(this, new Observer<ArrayList<UserAddress>>() {
            @Override
            public void onChanged(ArrayList<UserAddress> userAddresses) {
                for (UserAddress i : userAddresses){
                    addresses.add(i);
                }
                adapter = new AccountAddressAdapter(addresses);
                recyclerViewList.setAdapter(adapter);
            }
        });

    }
}