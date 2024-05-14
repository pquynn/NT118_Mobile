package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

public class am_change_userinfo_name extends AppCompatActivity {
    String userId = "";
    private UserInfoVM viewModel;
    Button btn_saveChanges;
    EditText textinput_name;
    private FrameLayout btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_change_userinfo_name);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chỉnh sửa thông tin");

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
        textinput_name = findViewById(R.id.textinputAM_userinfo_name);
        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textinput_name.setText(user.getUserName());
            }
        });

        btn_saveChanges = findViewById(R.id.btnAM_savechangeUserInfoName);
    }
}