package com.example.javajoyadmin.ui.activityfragment.admin.refund;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.OrderItem;
import com.example.javajoyadmin.data.model.entity.Refund;
import com.example.javajoyadmin.data.model.entity.RefundItem;
import com.example.javajoyadmin.databinding.ActivityRefundProgressBinding;
import com.example.javajoyadmin.ui.activityfragment.authentication.activity_login;
import com.example.javajoyadmin.ui.adapter.RefundItemAdapter;
import com.example.javajoyadmin.ui.viewmodel.admin.refund.RefundViewModel;

import java.util.HashMap;
import java.util.Map;

public class RefundProgressActivity extends AppCompatActivity {
    private RefundItemAdapter adapter;
    private Map<String, RefundItem> refundItemMap;
    private Map<String, OrderItem> orderItemMap;
    private FrameLayout btnBack;
    private TextView screenName;
    private String orderId;
    private RefundViewModel viewModel;
    private ActivityRefundProgressBinding binding;
    private Context context;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";

    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            finish();
            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_progress);
        binding.setLifecycleOwner(this);

        // get order id through intent
        if(getIntent().getExtras() != null){
            orderId = getIntent().getExtras().getString("orderId");
        }

        // Create AlertDialog with ProgressBar
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(true); // Prevents dialog from being dismissed
        progressDialog = builder.create();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Yêu cầu hoàn tiền");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // init adapter
        refundItemMap = new HashMap<>();
        orderItemMap = new HashMap<>();
        adapter = new RefundItemAdapter(orderItemMap, refundItemMap, this);
        binding.recyclerViewRefundProgress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRefundProgress.setAdapter(adapter);

        viewModel = new RefundViewModel(orderId, this);
        progressDialog.show();
        // observe change in refund live data
        viewModel.getRefundLiveData().observe(this, new Observer<Refund>() {
            @Override
            public void onChanged(Refund refund) {
                binding.setRefundVM(viewModel);
                refundItemMap.clear();
                refundItemMap.putAll(refund.getRefundItemMap());
                progressDialog.dismiss();
            }
        });

        // observe change in order live data
        viewModel.getOrderLiveData().observe(this, order -> {
            orderItemMap.clear();
            orderItemMap.putAll(order.getOrderItem());
            adapter.notifyDataSetChanged();
        });

        context = this;
        // btn accept refund
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(context, binding.btnAccept.getText().toString());
            }
        });

        // btn refuse refund
        binding.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(context, binding.btnRefuse.getText().toString());
            }
        });


    }

    // Function to show alert dialog when click button
    public void showAlertDialog(Context context, String btnName){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("");
        alert.setMessage("Xác nhận " + btnName + "?");

        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // xử lý cập nhật trạng thái
                viewModel.updateRefundStatus(btnName);
//                binding.btnRefuse.setVisibility(View.GONE);
//                if (btnName.equals("Chấp nhận hoàn tiền"))
//                    binding.btnAccept.setText("Đã hoàn tiền");
//                else
//                    binding.btnAccept.setVisibility(View.GONE);
            }
        });

        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
