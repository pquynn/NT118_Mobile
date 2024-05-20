package com.example.foodorderingapp.ui.activityfragment.customer.refund;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.ActivityChooseRefundprodBinding;
import com.example.foodorderingapp.ui.adapter.RefundProductAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.refund.AddRefundProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRefundProductActivity extends AppCompatActivity implements RefundProductAdapter.OnItemClickListener {
    private RefundProductAdapter adapter;
    private String orderId = "2";
    private Map<String, OrderItem> orderItemMap;
    private AddRefundProductViewModel viewModel;
    private ActivityChooseRefundprodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_refundprod);
        binding.setLifecycleOwner(this);

        // get order id through intent
        if(getIntent().getExtras() != null){
            orderId = getIntent().getExtras().getString("orderId");
        }

        // Set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn sản phẩm hoàn tiền");

        // Initialize maps and adapter
        orderItemMap = new HashMap<>();
        adapter = new RefundProductAdapter(orderItemMap, this::onItemClick);
        binding.recyclerViewRefundProd.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRefundProd.setAdapter(adapter);

        // Initialize view model
        viewModel = new AddRefundProductViewModel(orderId, this);
        viewModel.getOrderLiveData().observe(this, order -> {
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });

        // Button add click event
        binding.btnAdd.setOnClickListener(v -> {
            if(viewModel.getSelectedOrderItemId().getValue().isEmpty()){
                Toast.makeText(this, "Bạn chưa chọn sản phẩm muốn hoàn tiền", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(getApplicationContext(), RefundRequestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                bundle.putStringArrayList("selectedOrderItemId", new ArrayList<>(viewModel.getSelectedOrderItemId().getValue()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(List<String> selectedOrderItemIds) {
        viewModel.getSelectedOrderItemId().setValue(selectedOrderItemIds);
    }
}
