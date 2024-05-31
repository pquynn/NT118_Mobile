package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserAddressVM;

public class AM_AddAddressActivity extends AppCompatActivity {
    private FrameLayout btnBack;
    private TextView screenName;
    String userId, userAddressId;
    UserAddressVM userAddressVM;
    EditText input_recipient_name, input_recipient_phone, input_address_detail, input_ward, input_district, input_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // set top navigation text
        screenName = findViewById(R.id.screen_name);
        screenName.setText("");

        input_recipient_name = findViewById(R.id.input_recipient_name);
        input_recipient_phone = findViewById(R.id.input_recipient_phone);
        input_address_detail = findViewById(R.id.input_address_detail);
        input_ward = findViewById(R.id.input_ward);
        input_district = findViewById(R.id.input_district);
        input_city = findViewById(R.id.input_city);

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_address_id")){
                userAddressId = intent.getStringExtra("user_address_id");
                screenName.setText("Sửa địa chỉ");

                userAddressVM = new UserAddressVM(userAddressId, this);
                userAddressVM.getUserAddressMutableLiveData().observe(this, new Observer<UserAddress>() {
                    @Override
                    public void onChanged(UserAddress userAddress) {
                        input_recipient_name.setText(userAddress.getRecipientName());
                        input_recipient_phone.setText(userAddress.getRecipientPhone());
                        input_address_detail.setText(userAddress.getAddressDetail());
                        input_ward.setText(userAddress.getWard());
                        input_district.setText(userAddress.getDistrict());
                        input_city.setText(userAddress.getCity());

                    }
                });
            }
            if(intent.hasExtra("user_id")){
                screenName.setText("Thêm địa chỉ");
            }
        }


        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}