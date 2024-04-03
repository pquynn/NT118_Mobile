package com.example.foodorderingapp.customer_module.checkout;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class checkout extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Xác nhận đơn hàng");
    }
}
