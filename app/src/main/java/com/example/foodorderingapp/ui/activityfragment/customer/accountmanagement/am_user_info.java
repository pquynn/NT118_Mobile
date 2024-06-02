package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

public class am_user_info extends AppCompatActivity {
//    private UserInfoVM userInfoVM;
    TextView tvNameAcc, tvName, tvPhone;

    LinearLayout llAM_userInfo_phone, llAM_userInfo_name, llAM_userInfo_password;
    String userId = "";
    private UserInfoVM viewModel;

    private FrameLayout btnBack;

    private static final int EDIT_NAME_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_user_info);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Thông tin cá nhân");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trả kết quả về MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedName", String.valueOf(tvName.getText()));
                setResult(RESULT_OK, resultIntent);
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

        tvNameAcc = findViewById(R.id.textView_name_acc);
        tvName = findViewById(R.id.textView_name);
        tvPhone = findViewById(R.id.textView_phone);

        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvNameAcc.setText(user.getUserName());
                tvName.setText(user.getUserName());
                tvPhone.setText(user.getPhone());
            }
        });



        llAM_userInfo_name = findViewById(R.id.llAM_userInfo_name);
        llAM_userInfo_phone = findViewById(R.id.llAM_userInfo_phone);
        llAM_userInfo_password = findViewById(R.id.llAM_userInfo_password);

        llAM_userInfo_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), am_change_userinfo_name.class);
                myIntent.putExtra("user_id", userId);
                //startActivity(myIntent);
                startActivityIfNeeded(myIntent, EDIT_NAME_REQUEST_CODE);
            }
        });

        llAM_userInfo_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), am_change_userinfo_phone.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });
        llAM_userInfo_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), am_change_password.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NAME_REQUEST_CODE && resultCode == RESULT_OK) {
            String updatedName = data.getStringExtra("updatedName");
            tvName.setText(updatedName);
            tvNameAcc.setText(updatedName);
        }
    }
}