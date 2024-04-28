package com.example.foodorderingapp.UI.Activity_Fragment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.Data.Repository.Authentication.AuthRepository;
import com.example.foodorderingapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class activity_setpassword extends AppCompatActivity {

    private EditText inputPassword, inputConfirmPassword;
    private Button btnConfirm;
    private FrameLayout btnBack;
    private ImageButton btnViewOne, btnViewTwo;
    private ProgressBar progressBar;
    private Intent intent;
    private String phone;
    private AuthRepository authRepository = new AuthRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setpassword);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    private void init(){
        inputPassword = findViewById(R.id.editTextPassword); // Mật khẩu
        inputConfirmPassword = findViewById(R.id.editTextConfirmPassword); // Xác nhận mật khẩu
        btnConfirm = findViewById(R.id.btnConfirm); // Nút xác nhận
        btnBack = findViewById(R.id.btn_back); // Nút quay lại
        btnViewOne = findViewById(R.id.imgBtnVisibility);
        btnViewTwo = findViewById(R.id.imgBtnVisibilityConfirm);
        progressBar = findViewById(R.id.progressBar);

        intent = getIntent();
        phone = intent.getStringExtra("phone");

        // Xử lý khi ấn nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();

                if (!password.isEmpty() || !confirmPassword.isEmpty()) {
                    // Xử lý khi người dùng đã nhập tất cả các thông tin
                    if (!password.equals(confirmPassword)) {
                        // Thông báo khi xác nhận mật khẩu sai
                        progressBar.setVisibility(View.GONE);
                        inputConfirmPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Vui lòng xác nhận lại mật khẩu!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý đăng nhập khi cả hai EditText được điền đầy đủ
                        // Mã hóa MD5, giá trị trả về là chuỗi gồm 32 kí tự
                        password = md5(password);
                        Log.d("Set Password", password);

                        authRepository.changePassword(phone, password, new AuthRepository.AuthCallbackUpdatePassword() {
                            @Override
                            public void onUpdateSuccess() {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(activity_setpassword.this, activity_login.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onUpdateFailure(Exception e) {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Đã gặp sự cố trong quá trình đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    // Hiển thị thông báo khi có thiếu thông tin
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ mật khẩu và xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Biến kiểm tra trạng thái xem mật khẩu
        final boolean[] passwordVisible = {false};

        // Xử lý nút xem mật khẩu
        btnViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (passwordVisible[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisible[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    btnViewOne.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputPassword.setSelection(inputPassword.getText().length());
                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordVisible[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    btnViewOne.setImageResource(R.drawable.visibility_off);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputPassword.setSelection(inputPassword.getText().length());
                }
            }
        });

        // Biến kiểm tra trạng thái xem mật khẩu
        final boolean[] confirmPasswordVisible = {false};

        // Xử lý nút xem mật khẩu
        btnViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (confirmPasswordVisible[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    inputConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPasswordVisible[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    btnViewTwo.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    inputConfirmPassword.setSelection(inputConfirmPassword.getText().length());
                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    inputConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    confirmPasswordVisible[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    btnViewTwo.setImageResource(R.drawable.visibility_off);
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