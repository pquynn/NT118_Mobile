package com.example.foodorderingapp.ui.activityfragment.customer.refund;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.databinding.ActivityChooseRefundprodBinding;
import com.example.foodorderingapp.ui.adapter.RefundProductAdapter;
import com.example.foodorderingapp.ui.adapter.RefundRequestAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.refund.AddRefundProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRefundProductActivity extends AppCompatActivity {
    private RefundProductAdapter adapter;
    private String orderId = "";
    private Map<String, OrderItem> orderItemMap;
    private AddRefundProductViewModel viewModel;
    private ActivityChooseRefundprodBinding binding;
    private static final int REFUNDREQUEST_REQUEST_CODE = 1;
    private FrameLayout btnBack;

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

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Initialize maps and adapter
        orderItemMap = new HashMap<>();
        adapter = new RefundProductAdapter(orderItemMap);
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
            onButtonAddClick();
            if(viewModel.getSelectedOrderItemId().getValue().isEmpty()){
                Toast.makeText(this, "Bạn chưa chọn sản phẩm muốn hoàn tiền", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(getApplicationContext(), RefundRequestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                bundle.putStringArrayList("selectedOrderItemId", new ArrayList<>(viewModel.getSelectedOrderItemId().getValue()));
                bundle.putIntegerArrayList("selectedQuantity", new ArrayList<>(viewModel.getSelectedQuantity().getValue()));
                intent.putExtras(bundle);
//                startActivity(intent);
                startActivityForResult(intent, REFUNDREQUEST_REQUEST_CODE);
            }
        });
    }

    public void onButtonAddClick(){
        List<String> selectedId = new ArrayList<>();
        List<Integer> selectedQuantity = new ArrayList<>();
        for (int i = 0; i < binding.recyclerViewRefundProd.getChildCount(); i++) {
            RefundProductAdapter.ViewHolder viewHolder = (RefundProductAdapter.ViewHolder) binding.recyclerViewRefundProd.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                List<String> keys = new ArrayList<>(orderItemMap.keySet());
                String key = keys.get(i);
                if(viewHolder.getBinding().checkBox.isChecked()){
                    selectedId.add(key);
                    selectedQuantity.add(Integer.parseInt(viewHolder.getBinding().txtQuantity.getText().toString()));
                }
            }
        }
        viewModel.getSelectedQuantity().setValue(selectedQuantity);
        viewModel.getSelectedOrderItemId().setValue(selectedId);
    }

    // method to get result from activity through intent (activity2 -> activity1)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GET DATA FROM COUPON ACTIVITY
        if (requestCode == REFUNDREQUEST_REQUEST_CODE && resultCode == RESULT_OK) {

        }
        else {
            finish();
        }

    }
}
