package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class am_change_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_change_password);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đổi mật khẩu");
    }
}