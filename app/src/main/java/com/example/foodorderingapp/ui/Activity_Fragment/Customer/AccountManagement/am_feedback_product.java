package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class am_feedback_product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_product);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá");
    }
}