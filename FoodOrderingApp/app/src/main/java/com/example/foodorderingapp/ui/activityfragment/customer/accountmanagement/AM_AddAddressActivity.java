package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserAddressVM;

public class AM_AddAddressActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private FrameLayout btnBack;
    private TextView screenName;
    String userId, userAddressId;
    UserAddressVM userAddressVM;
    EditText input_recipient_name, input_recipient_phone, input_address_detail, input_ward, input_district, input_city;
    Button btn_saveAddress;
    AlertDialog progressDialog;
    LinearLayout btn_getAddress; //nút chuyển activity lấy địa chỉ

    UserInfoRepository repository_user = new UserInfoRepository();

    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";

    @Override
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
        btn_saveAddress = findViewById(R.id.btn_saveAddress);
        btn_getAddress = findViewById(R.id.ll_getAddress);

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_address_id")){
                progressDialog.show();
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
                        progressDialog.dismiss();
                    }
                });

                btn_saveAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = String.valueOf(input_recipient_name.getText());
                        String phone = String.valueOf(input_recipient_phone.getText());
                        String addressDetail = String.valueOf(input_address_detail.getText());
                        String ward = String.valueOf(input_ward.getText());
                        String district = String.valueOf(input_district.getText());
                        String city = String.valueOf(input_city.getText());
                        String userId = intent.getStringExtra("user_id");
                        UserAddress userAddress = new UserAddress(name, addressDetail, city, district, ward, phone, userId);
                        progressDialog.show();

                        repository_user.updateAddress(userAddressId, userAddress, new UserInfoRepository.userAddressCallback() {
                            @Override
                            public void loadUserAddressSuccess(UserAddress userAddress) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();

                                Intent resultIntent = new Intent();
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }

                            @Override
                            public void loadUsserAddressError(Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Cập nhật địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            if(intent.hasExtra("user_id")){
                screenName.setText("Thêm địa chỉ");

                btn_saveAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = String.valueOf(input_recipient_name.getText());
                        String phone = String.valueOf(input_recipient_phone.getText());
                        String addressDetail = String.valueOf(input_address_detail.getText());
                        String ward = String.valueOf(input_ward.getText());
                        String district = String.valueOf(input_district.getText());
                        String city = String.valueOf(input_city.getText());
                        String userId = intent.getStringExtra("user_id");
                        UserAddress userAddress = new UserAddress(name, addressDetail, city, district, ward, phone, userId);
                        progressDialog.show();
                        repository_user.addAddress(userAddress, new UserInfoRepository.userAddressCallback() {
                            @Override
                            public void loadUserAddressSuccess(UserAddress userAddress) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();

                                Intent resultIntent = new Intent();
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }

                            @Override
                            public void loadUsserAddressError(Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Thêm địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
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

        // Đăng ký ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        // Hiện dữ liệu từ trang chọn địa chỉ
                        input_address_detail.setText(data.getStringExtra("address_detail"));
                        input_ward.setText(data.getStringExtra("ward"));
                        input_district.setText(data.getStringExtra("district"));
                        input_city.setText(data.getStringExtra("city"));
                    }
                }
        );

        // Nút chuyển qua trang chọn địa chỉ
        btn_getAddress.setOnClickListener(v -> {
            Intent intentNew = new Intent(AM_AddAddressActivity.this, SelectLocation.class);
            activityResultLauncher.launch(intentNew);
        });
    }

}