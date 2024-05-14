package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

public class am_change_userinfo_phone extends AppCompatActivity {
    String userId = "";
    private UserInfoVM viewModel;
    Button btn_saveChanges;
    EditText textinput_phone;
    FrameLayout btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_change_userinfo_phone);
         TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chỉnh sửa số điện thoại");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }

        viewModel = new UserInfoVM(userId, this);
        textinput_phone = findViewById(R.id.textinputAM_userinfo_phone);
        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textinput_phone.setText(user.getPhone());
            }
        });

        btn_saveChanges = findViewById(R.id.btnAM_savechangeUserInfoPhone);
    }
}