package com.example.foodorderingapp.ui.Activity_Fragment.Customer.Checkout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderingapp.R;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BuySuccessActivity extends AppCompatActivity {
    private FrameLayout btnBack;
    private TextView screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Đặt hàng thành công");

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
