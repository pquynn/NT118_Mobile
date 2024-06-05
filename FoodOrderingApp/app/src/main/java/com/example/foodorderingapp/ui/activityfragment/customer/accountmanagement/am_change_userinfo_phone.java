package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.repository.authentication.AuthRepository;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_forgetpassword;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_verifyOTP;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

import java.util.Objects;

public class am_change_userinfo_phone extends AppCompatActivity {
    String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    private UserInfoVM viewModel;
    private AuthRepository authRepository = new AuthRepository();
    private String userPhone;
    Button btn_saveChanges;
    EditText textinput_phone;
    FrameLayout btnBack;
    AlertDialog progressDialog;
    @SuppressLint("MissingInflatedId")
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
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }

        viewModel = new UserInfoVM(userId, this);
        textinput_phone = findViewById(R.id.textinputAM_userinfo_phone);
        progressDialog.show();
        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userPhone = user.getPhone();
                textinput_phone.setText(userPhone);
                progressDialog.dismiss();
            }
        });

        btn_saveChanges = findViewById(R.id.btnAM_savechangeUserInfoPhone);

        // Kiểm tra định dạng sau khi nhập số điện thoại.
        textinput_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Khi EditText không còn trong trạng thái focus
                    String phone = textinput_phone.getText().toString().trim();
                    // Kiểm tra định dạng của số điện thoại
                    if (!phone.startsWith("0")) {
                        Toast.makeText(getApplicationContext(), "Số điện thoại bắt đầu với số 0! Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else if (phone.length() != 10) { // Kiểm tra độ dài của văn bản sau khi đã thay đổi
                        // Nếu đã không đủ 10 ký tự
                        Toast.makeText(getApplicationContext(), "Số điện thoại thiếu ký tự! Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Xử lý gửi nút gửi mã OTP
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = String.valueOf(textinput_phone.getText());

                if (phone.length() != 10 || !phone.startsWith("0")) {
                    // Thông báp khi nhập thiếu số điện thoại
                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                } else if (Objects.equals(userPhone, phone)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại mới!", Toast.LENGTH_SHORT).show();
                }  else {
                    // Xử lý đăng nhập khi EditText được điền đầy đủ
                    progressDialog.show();
                    authRepository.checkPhoneNumber(phone, new AuthRepository.AuthCallback() {
                        @Override
                        public void onLoginSuccess(String data) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Số điện thoại đã được sử dụng!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onLoginFailure(Exception e) {
                            authRepository.sendOTP(phone, false, am_change_userinfo_phone.this, new AuthRepository.AuthCallbackOTP() {
                                @Override
                                public void onSuccess() {
                                    Intent intent = new Intent(am_change_userinfo_phone.this, activity_verifyOTP.class);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("oldPhone", userPhone);
                                    intent.putExtra("verificationCode", authRepository.getVerificationCode());
                                    progressDialog.dismiss();
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Nhập sai số điện thoại hoặc quá trình đã gặp sự cố!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}