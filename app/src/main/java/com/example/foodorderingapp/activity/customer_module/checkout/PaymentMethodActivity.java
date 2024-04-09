package com.example.foodorderingapp.activity.customer_module.checkout;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;

public class PaymentMethodActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn phương thức thanh toán");
    }
}