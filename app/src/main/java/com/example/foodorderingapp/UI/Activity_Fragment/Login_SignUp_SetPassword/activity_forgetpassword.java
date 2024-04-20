package com.example.foodorderingapp.UI.Activity_Fragment.Login_SignUp_SetPassword;

import android.content.Intent;
import android.os.Bundle;
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

public class activity_forgetpassword extends AppCompatActivity {

    private EditText inputPhone;
    private Button btnSendOTP;
    private FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpassword);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init(){
        inputPhone = findViewById(R.id.editTextPhone);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnBack = findViewById(R.id.btn_back);

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

        // Xử lý gửi nút gửi mã OTP
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút được nhấn
                Intent intent = new Intent(activity_forgetpassword.this, activity_verifyOTP.class);
                startActivity(intent);
            }
        });

        // Xử lý khi ấn nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}