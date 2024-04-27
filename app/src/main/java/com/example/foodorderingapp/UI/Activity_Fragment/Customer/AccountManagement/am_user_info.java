package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodorderingapp.Data.Model.Entity.User;
import com.example.foodorderingapp.Data.Model.OrderDetail;
import com.example.foodorderingapp.Data.Repository.AccountManagement.MyOrders.OrdersListFeedbackRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.ViewModel.Customer.AccountManagement.UserInfoVM;

import java.util.List;

public class am_user_info extends AppCompatActivity {
    private UserInfoVM userInfoVM;
    TextView tvNameAcc, tvName, tvPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_user_info);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Thông tin cá nhân");

        tvNameAcc = findViewById(R.id.textView_name_acc);
        tvName = findViewById(R.id.textView_name);
        tvPhone = findViewById(R.id.textView_phone);


//        userInfoVM = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
//                .getInstance(getApplication())).get(UserInfoVM.class);
//        userInfoVM.getUserLiveData().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                if (user != null) {
//                    tvName.setText(user.getUserName());
//                    tvPhone.setText(user.getPhone());
//                    tvNameAcc.setText(user.getUserName());
//                } else {
//                    tvName.setText("Đang tải...");
//                    tvPhone.setText("Đang tải...");
//                    tvNameAcc.setText("Đang tải...");
//                }
//            }
//        });
//        userInfoVM.loadUser("1");
    }
}