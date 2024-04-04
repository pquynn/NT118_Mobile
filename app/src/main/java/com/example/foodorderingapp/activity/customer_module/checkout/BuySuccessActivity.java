package com.example.foodorderingapp.activity.customer_module.checkout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderingapp.R;
import android.widget.TextView;

public class BuySuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Đặt hàng thành công");
    }
}
