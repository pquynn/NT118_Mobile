package com.example.foodorderingapp.UI.Activity_Fragment.Login_SignUp_SetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public class activity_signup extends AppCompatActivity {

    EditText inputName, inputPhone, inputPassword, inputConfirmPassword;
    Button btnSignUp;
    ImageButton btnViewPassword, btnViewConfirmPassword;
    FrameLayout btnBack;

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
    private void init(){
        inputName = findViewById(R.id.editTextName); // Tên người dùng
        inputPhone = findViewById(R.id.editTextPhone); // Số điện thoại
        inputPassword = findViewById(R.id.editTextPassword); // Mật khẩu
        inputConfirmPassword = findViewById(R.id.editTextConfirmPassword); // Xác nhận mật khẩu
        btnBack = findViewById(R.id.btn_back); // Nút quay lại
        btnSignUp = findViewById(R.id.btnSignUp); // Nút đăng ký
        btnViewPassword = findViewById(R.id.imgBtnVisibility); // Nút xem mật khẩu
        btnViewConfirmPassword = findViewById(R.id.imgBtnVisibilityConfirm); // Nút xem xác nhận mật khẩu

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
                String name = inputName.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();

                if (!name.isEmpty() && !phone.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                    if (phone.length() != 10 || !phone.startsWith("0")) {
                        // Thông báp khi nhập thiếu số điện thoại
                        Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else if (!password.isEmpty() || !confirmPassword.isEmpty()) {
                        // Thông báp khi chưa nhập mật khẩu
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirmPassword)) {
                        // Thông báo khi xác nhận mật khẩu sai
                        inputConfirmPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Vui lòng xác nhận lại mật khẩu!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý đăng nhập khi cả hai EditText được điền đầy đủ
                        // Mã hóa MD5, giá trị trả về là chuỗi gồm 32 kí tự
                        password = md5(password);

                        // Xử lý đăng ký
                        Toast.makeText(getApplicationContext(), "Đang xử lý đăng ký!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Hiển thị thông báo khi có thiếu thông tin
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin và mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Biến kiểm tra trạng thái xem mật khẩu
        final boolean[] passwordVisible = {false};

        // Xử lý nút xem mật khẩu
        btnViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (passwordVisible[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisible[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    btnViewPassword.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputPassword.setSelection(inputPassword.getText().length());
                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordVisible[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    btnViewPassword.setImageResource(R.drawable.visibility_off);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputPassword.setSelection(inputPassword.getText().length());
                }
            }
        });

        // Biến kiểm tra trạng thái xem mật khẩu
        final boolean[] confirmPasswordVisible = {false};

        // Xử lý nút xem mật khẩu
        btnViewConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (confirmPasswordVisible[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    inputConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPasswordVisible[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    btnViewConfirmPassword.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputConfirmPassword.setSelection(inputConfirmPassword.getText().length());
                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    inputConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    confirmPasswordVisible[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    btnViewConfirmPassword.setImageResource(R.drawable.visibility_off);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputConfirmPassword.setSelection(inputConfirmPassword.getText().length());
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