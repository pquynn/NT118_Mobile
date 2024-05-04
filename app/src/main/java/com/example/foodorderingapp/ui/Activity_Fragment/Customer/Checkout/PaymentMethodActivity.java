package com.example.foodorderingapp.ui.Activity_Fragment.Customer.Checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.PaymentMethodAdapter;
import com.example.foodorderingapp.data.model.PaymentMethod;

import java.util.ArrayList;

public class PaymentMethodActivity extends AppCompatActivity {
    private TextView screenName;
    private ArrayList<PaymentMethod> methodList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private Button btnConfirm;
    private FrameLayout btnBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn phương thức thanh toán");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerViewList = findViewById(R.id.recyclerViewPayment);
        btnConfirm = findViewById(R.id.btn_confirm);
        methodList = new ArrayList<PaymentMethod>();
        adapter = new PaymentMethodAdapter(this, methodList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        recyclerViewList.setAdapter(adapter);

        methodList.add(new PaymentMethod("Thanh toán khi nhận hàng", R.drawable.cash));
        methodList.add(new PaymentMethod("Paypal", R.drawable.paypal));
        adapter.notifyDataSetChanged();
    }
}