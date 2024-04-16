package com.example.foodorderingapp.activity.customer_module;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class AddAddressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Thêm địa chỉ");
    }
}