package com.example.foodorderingapp.ui.activityfragment.customer.refund;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.databinding.ActivityRefundRequestBinding;
import com.example.foodorderingapp.ui.adapter.RefundRequestAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.refund.RefundViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefundRequestActivity extends AppCompatActivity {
    private RefundRequestAdapter adapter;
    private Map<String, OrderItem> orderItemMap;
    private Map<String, RefundItem> refundItemMap;
    private FrameLayout btnBack;
    private TextView screenName;
    private String orderId = "2";
    private List<String> selectedOrderItemId;
    private RefundViewModel viewModel;
    private ActivityRefundRequestBinding binding;
    private Context context;
    private LifecycleOwner lifecycle;
    private ActivityResultLauncher<Intent> selectImgLauncher;
    private ActivityResultLauncher<Intent> selectVideoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_request);
        binding.setLifecycleOwner(this);

        selectedOrderItemId = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getString("orderId");
            selectedOrderItemId = getIntent().getExtras().getStringArrayList("selectedOrderItemId");
        }

        // Set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Yêu cầu hoàn tiền");

        // Set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // Initialize adapter
        orderItemMap = new HashMap<>();
        refundItemMap = new HashMap<>();

        // Initialize the image launcher
        selectImgLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                Uri imageUri = result.getData().getData();
                                adapter.setImageUri(adapter.getCurrentPosition(), imageUri);
                            }
                        } else {
                            Toast.makeText(RefundRequestActivity.this, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Initialize the video launcher
        selectVideoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                Uri videoUri = result.getData().getData();
                                adapter.setVideoUri(adapter.getCurrentPosition(), videoUri);
                            }
                        } else {
                            Toast.makeText(RefundRequestActivity.this, "Bạn chưa chọn video", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        adapter = new RefundRequestAdapter(orderItemMap, this, selectImgLauncher, selectVideoLauncher);
        binding.recyclerViewRefundRequest.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRefundRequest.setAdapter(adapter);

        viewModel = new RefundViewModel(orderId, this);

        // Observe change in selected order item live data
        lifecycle = binding.getLifecycleOwner();
        viewModel.getOrderLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                viewModel.getSelectedOrderItemMapLiveData(selectedOrderItemId, order).observe(lifecycle, new Observer<Map<String, OrderItem>>() {
                    @Override
                    public void onChanged(Map<String, OrderItem> newOrderItemMap) {
                        orderItemMap.clear();
                        orderItemMap.putAll(newOrderItemMap);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        context = this;
        // Button send click event
        binding.btnSend.setOnClickListener(v -> showAlertDialog(context, binding.btnSend.getText().toString()));
    }

    // Function to show alert dialog when click button
    public void showAlertDialog(Context context, String btnName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("");
        alert.setMessage("Xác nhận gửi yêu cầu?");
        alert.setPositiveButton("Đồng ý", (dialog, which) -> {
            dialog.dismiss();
            // Handle update status

            boolean isValid = updateRefundItemMap();
            if(isValid){
                viewModel.sendRefundRequest(refundItemMap);
                Intent intent = new Intent(this, SendRefundSucessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
//                Log.d("firestore", "không được gửi");
            }
        });

        alert.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public boolean updateRefundItemMap(){
        boolean isValid = true;
        for (int i = 0; i < binding.recyclerViewRefundRequest.getChildCount(); i++) {
            RefundRequestAdapter.ViewHolder viewHolder = (RefundRequestAdapter.ViewHolder) binding.recyclerViewRefundRequest.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                List<String> keys = new ArrayList<>(orderItemMap.keySet());
                String key = keys.get(i);
                OrderItem orderItem = orderItemMap.get(key);
//                RefundItem refundItem = refundItemMap.get(key);
                RefundItem refundItem = new RefundItem();

                String reason = viewHolder.getBinding().txtReason.getText().toString().trim();
                String describe = viewHolder.getBinding().txtDescription.getText().toString().trim();
                Uri proofImage = viewHolder.getImageUri();
                Uri proofVideo = viewHolder.getVideoUri();

                //Check if there is any NULL property:
                if(reason.isEmpty() || reason.equals("Chọn lý do")){
                    Toast.makeText(context, "Bạn chưa chọn lý do cho " + orderItem.getProductName(), Toast.LENGTH_LONG).show();
                    isValid = false;
                    break;
                }
                if(describe == null){
                    describe = "";
                }
                if(proofImage == null){
                    Toast.makeText(context, "Bạn chưa chọn hình ảnh cho " + orderItem.getProductName(), Toast.LENGTH_LONG).show();
                    isValid = false;
                    break;
                }
                if(proofVideo == null){
                    Toast.makeText(context, "Bạn chưa chọn video cho " + orderItem.getProductName(), Toast.LENGTH_LONG).show();
                    isValid = false;
                    break;
                }

                // if valid
                refundItem.setReason(reason);
                refundItem.setDescribe(describe);
                refundItem.setProofImage(proofImage.toString());
                refundItem.setProofVideo(proofVideo.toString());
                refundItem.setMoney(orderItem.getPrice());
                refundItemMap.put(key, refundItem);
//                Log.d("firestore", "updateRefundItemMap: " + updatedRefundItemMap.get(key));
            }
        }

        if(!isValid)
            refundItemMap.clear();

        return isValid;
    }
}
