package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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

import javax.annotation.Nullable;

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

    AlertDialog progressDialog;

    private static final int REQUEST_CHANGE_ADDRESS = 1;
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

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnAddAddress = findViewById(R.id.btn_addAddress);
        if(btnAddAddress == null){
            Log.e(TAG,"btnAddAddress is null. Check layout and ID");
        }else{
            btnAddAddress.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getApplicationContext(), AM_AddAddressActivity.class);
                    myIntent.putExtra("user_id", userId);
                    //startActivity(myIntent);
                    startActivityIfNeeded(myIntent, REQUEST_CHANGE_ADDRESS);
                }
            }));
        }
        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }


        recyclerViewAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHANGE_ADDRESS && resultCode == RESULT_OK) {
            Log.d("get in activity resilt", "get in");
            // Cập nhật danh sách địa chỉ sau khi thêm mới thành công
            // Gọi lại phương thức recyclerViewAddress() để cập nhật danh sách địa chỉ
            recyclerViewAddress();
        }
    }

    private void recyclerViewAddress(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewAddress);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        addresses = new ArrayList<>();

        progressDialog.show();
        viewModel = new UserInfoVM(userId, this);
        viewModel.getUserAddressesLiveData().observe(this, new Observer<ArrayList<UserAddress>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArrayList<UserAddress> userAddresses) {
                for (UserAddress i : userAddresses){
                    addresses.add(i);
                    Log.d("print list: ", "address: "+i.getAllAddress());
                }
                adapter = new AccountAddressAdapter(addresses);
                recyclerViewList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        });

    }
}