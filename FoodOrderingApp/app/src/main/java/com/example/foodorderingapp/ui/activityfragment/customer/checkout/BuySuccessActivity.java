package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_my_orders;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_order_detail;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderInProgress;
import com.example.foodorderingapp.ui.activityfragment.customer.cart.CartFragment;
import com.example.foodorderingapp.ui.activityfragment.customer.refund.RefundViewActivity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BuySuccessActivity extends AppCompatActivity {
    private FrameLayout btnBack;
    private TextView screenName;
    private Button btnTrack;
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);
        if(getIntent().getExtras() != null){
            userId = getIntent().getExtras().getString("userId");
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Đặt hàng thành công");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartFragment.class));
            }
        });

        //on back pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), CartFragment.class));
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);




        btnTrack = findViewById(R.id.btn_tracking);
        btnTrack.setOnClickListener(new View.OnClickListener() {
            // todo: check lại chỗ này xem đến trang nào
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), am_my_orders.class);
//                intent.putExtra("user_id", userId);
                startActivity(intent);
                finish();
            }
        });
    }
}
