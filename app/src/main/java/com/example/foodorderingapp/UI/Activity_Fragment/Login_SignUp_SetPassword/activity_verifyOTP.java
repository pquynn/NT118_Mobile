package com.example.foodorderingapp.UI.Activity_Fragment.Login_SignUp_SetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;

public class activity_verifyOTP extends AppCompatActivity {

    private EditText firstInput, secondInput, thirdInput, fourthInput;
    private Button btnCofirm;
    private FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init(){
        firstInput = findViewById(R.id.editTextName);
        secondInput = findViewById(R.id.editTextPhone);
        thirdInput = findViewById(R.id.editTextPhone);
        fourthInput = findViewById(R.id.editTextPhone);
        btnCofirm = findViewById(R.id.btnSignUp); // Nút xác thực
        btnBack = findViewById(R.id.btn_back); // Nút quay lại

        firstInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                secondInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        secondInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                thirdInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        thirdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                fourthInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
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
        btnCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String one = firstInput.getText().toString().trim();
                String two = secondInput.getText().toString().trim();
                String three = thirdInput.getText().toString().trim();
                String four = fourthInput.getText().toString().trim();

                if (!one.isEmpty() && !two.isEmpty() && !three.isEmpty() && !four.isEmpty()) {
                    // Xử lý xác thực
                    Toast.makeText(getApplicationContext(), "Đang xử lý xác thực!", Toast.LENGTH_SHORT).show();

                    Log.d("OTP", one+two+three+four);

                    // Chuyển qua màn hình đặt lại mật khẩu
                    Intent intent = new Intent(activity_verifyOTP.this, activity_setpassword.class);
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo khi có thiếu thông tin
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ mã OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}