package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class AM_AddAddressActivity extends AppCompatActivity {
    private FrameLayout btnBack;
    private TextView screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Thêm địa chỉ");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}