package com.example.foodorderingapp.ui.activityfragment.customer.refund;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderingapp.R;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SendRefundSucessActivity extends AppCompatActivity {
    private TextView screenName;
    private Button btnReview;
    private String orderId = "", refundId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_refund_success);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Gửi yêu cầu thành công");

        if(getIntent().getExtras() != null){
            orderId = getIntent().getExtras().getString("orderId");
        }

        btnReview = findViewById(R.id.btn_review);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RefundViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
