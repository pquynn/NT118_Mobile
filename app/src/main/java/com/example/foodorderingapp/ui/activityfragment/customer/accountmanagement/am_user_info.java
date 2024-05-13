package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
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
    TextView tvNameAcc, tvName, tvPhone, tvGoEditInfo;
    String userId = "";
    private UserInfoVM viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_user_info);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Thông tin cá nhân");

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
        tvGoEditInfo = findViewById(R.id.txt_goto_edit);

        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvNameAcc.setText(user.getUserName());
                tvName.setText(user.getUserName());
                tvPhone.setText(user.getPhone());
            }
        });

    }
}