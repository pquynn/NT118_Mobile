package com.example.foodorderingapp.activity.customer_module.checkout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.MainActivity;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.PaymentMethodAdapter;
import com.example.foodorderingapp.domain.PaymentMethod;

import java.util.ArrayList;

public class PaymentMethodActivity extends AppCompatActivity {
    private TextView screenName;
    private ArrayList<PaymentMethod> methodList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private Button btnConfirm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn phương thức thanh toán");

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