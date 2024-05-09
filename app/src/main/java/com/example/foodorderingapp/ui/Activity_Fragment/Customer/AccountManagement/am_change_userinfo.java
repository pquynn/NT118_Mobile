package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class am_change_userinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_change_userinfo);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chỉnh sửa thông tin");
    }
}