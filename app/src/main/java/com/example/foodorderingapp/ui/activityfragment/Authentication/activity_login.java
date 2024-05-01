package com.example.foodorderingapp.ui.activityfragment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class activity_login extends AppCompatActivity {

    private EditText inputPhone, inputPassword;
    private Button btnLogin, btnForgetPass, btnSignUp;
    private ImageButton imgBtnVisibility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init(){
        inputPhone = findViewById(R.id.editTextPhone); // Số điện thoại
        inputPassword = findViewById(R.id.editTextPassword); // Mật khẩu
        btnLogin = findViewById(R.id.btnLogin); // Nút đăng nhập
        btnForgetPass = findViewById(R.id.btnForgetPassword); // Nút quên mật khẩu
        btnSignUp = findViewById(R.id.btnSignUp); // Nút đăng ký
        imgBtnVisibility = findViewById(R.id.imgBtnVisibility); // Nút xem mật khẩu

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

        // Xử lý nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = inputPhone.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (phone.length()!=10 || !phone.startsWith("0")) {
                    // Thông báp khi nhập thiếu số điện thoại
                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                } else if (!password.isEmpty()) {
                    // Thông báp khi chưa nhập mật khẩu
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                } else {
                        // Xử lý đăng nhập khi cả hai EditText được điền đầy đủ
                        // Mã hóa MD5, giá trị trả về là chuỗi gồm 32 kí tự
                        password = md5(password);

                        // Xử lý đăng nhập
                        Toast.makeText(getApplicationContext(), "Đang xử lý đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý nút quên mật khẩu
        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút được nhấn
                Intent intent = new Intent(activity_login.this, activity_forgetpassword.class);
                startActivity(intent);
            }
        });

        // Xử lý nút tạo tài khoản mới
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút được nhấn
                Intent intent = new Intent(activity_login.this, activity_signup.class);
                startActivity(intent);
            }
        });

        // Biến kiểm tra trạng thái xem mật khẩu
        final boolean[] passwordVisible = {false};

        // Xử lý nút xem mật khẩu
        imgBtnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (passwordVisible[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisible[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    imgBtnVisibility.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputPassword.setSelection(inputPassword.getText().length());

                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordVisible[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    imgBtnVisibility.setImageResource(R.drawable.visibility_off);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputPassword.setSelection(inputPassword.getText().length());
                }
            }
        });
    }


    // Hàm để mã hóa chuỗi thành MD5
    private String md5(String input) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // Cập nhật dữ liệu đầu vào
            digest.update(input.getBytes());
            // Lấy bản mã đã mã hóa
            byte[] messageDigest = digest.digest();

            // Chuyển bản mã thành dạng hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}