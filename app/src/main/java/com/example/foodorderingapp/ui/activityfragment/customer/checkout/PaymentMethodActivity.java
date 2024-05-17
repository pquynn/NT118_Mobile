package com.example.foodorderingapp.ui.activityfragment.customer.checkout;

import android.content.Intent;
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

public class PaymentMethodActivity extends AppCompatActivity implements PaymentMethodAdapter.OnItemClickListener {
    private String selectedPayment = "";
    private TextView screenName;
    private ArrayList<PaymentMethod> methodList;
    private PaymentMethodAdapter adapter;
    private RecyclerView recyclerViewList;
    private Button bthAdd;
    private FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        // get data through Intent
        if (getIntent().getExtras() != null) {
            selectedPayment = getIntent().getExtras().getString("paymentMethod", "");
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn phương thức thanh toán");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        recyclerViewList = findViewById(R.id.recyclerViewPayment);
        methodList = new ArrayList<>();
        adapter = new PaymentMethodAdapter(this, methodList, selectedPayment, this::onItemClick);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        recyclerViewList.setAdapter(adapter);

        methodList.add(new PaymentMethod("Thanh toán khi nhận hàng", R.drawable.cash));
        methodList.add(new PaymentMethod("Paypal", R.drawable.paypal));
        adapter.notifyDataSetChanged();

        // button add click event
        bthAdd = findViewById(R.id.btn_add);
        bthAdd.setOnClickListener(v -> {
//             Create an intent to hold the payment data
            Intent resultIntent = new Intent();
            resultIntent.putExtra("paymentMethod", selectedPayment);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    // viewholder click event
    @Override
    public void onItemClick(String selectedPayment) {
        this.selectedPayment = selectedPayment;
    }
}
