package com.example.foodorderingapp.UI.Activity_Fragment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.Data.Repository.Authentication.AuthRepository;
import com.example.foodorderingapp.R;

public class activity_signup extends AppCompatActivity {

    private EditText inputName, inputPhone;
    private Button btnSignUp;
    private FrameLayout btnBack;
    private ProgressBar progressBar;
    private AuthRepository authRepository = new AuthRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }


    private void init() {
        inputName = findViewById(R.id.editTextName); // Tên người dùng
        inputPhone = findViewById(R.id.editTextPhone); // Số điện thoại
        btnBack = findViewById(R.id.btn_back); // Nút quay lại
        btnSignUp = findViewById(R.id.btnSignUp); // Nút đăng ký
        progressBar = findViewById(R.id.progressBar);

        // Xử lý khi sau người dùng nhập số điện thoại(Con trỏ chuyển qua phần nhập dữ liệu khác)
        inputPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Khi EditText không còn trong trạng thái focus
                    String phone = inputPhone.getText().toString().trim();
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

        // Xử lý khi ấn nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Xử lý tài khoản mới
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name = inputName.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    if (phone.length() != 10 || !phone.startsWith("0")) {
                        // Thông báp khi nhập thiếu số điện thoại
                        Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else {
                        authRepository.checkPhoneNumber(phone, new AuthRepository.AuthCallback() {
                            @Override
                            public void onLoginSuccess(String data) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Số điện thoại sai hoặc đã được sử dụng!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLoginFailure(Exception e) {
                                authRepository.sendOTP(phone, false, activity_signup.this, new AuthRepository.AuthCallbackOTP() {
                                    @Override
                                    public void onSuccess() {
                                        Intent intent = new Intent(activity_signup.this, activity_verifyOTP.class);
                                        intent.putExtra("phone", phone);
                                        intent.putExtra("verificationCode", authRepository.getVerificationCode());
                                        intent.putExtra("userName", name);

                                        progressBar.setVisibility(View.GONE);

                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(getApplicationContext(), "Nhập sai số điện thoại hoặc quá trình đã gặp sự cố!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    }
                } else {
                    // Hiển thị thông báo khi có thiếu thông tin
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}