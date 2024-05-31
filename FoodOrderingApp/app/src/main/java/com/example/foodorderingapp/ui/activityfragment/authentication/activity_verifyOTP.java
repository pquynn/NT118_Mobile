package com.example.foodorderingapp.ui.activityfragment.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.repository.authentication.AuthRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;

public class activity_verifyOTP extends AppCompatActivity {

    private EditText firstInput, secondInput, thirdInput, fourthInput, fifthInput, sixthInput;
    private Button btnCofirm;
    private ProgressBar progressBar;
    private String phone, userName, oldPhone;
    private Intent intent;
    private String verificationCode;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
        init();
    }

    private void init() {
        firstInput = findViewById(R.id.editTextFirstCode);
        secondInput = findViewById(R.id.editTextSecondCode);
        thirdInput = findViewById(R.id.editTextThirdCode);
        fourthInput = findViewById(R.id.editTextFourthCode);
        fifthInput = findViewById(R.id.editTextFifthCode);
        sixthInput = findViewById(R.id.editTextSixthCode);
        btnCofirm = findViewById(R.id.btnConfirm); // Nút xác thực
        progressBar = findViewById(R.id.progressBar);

        intent = getIntent();
        phone = intent.getStringExtra("phone");
        userName = "";
        if (intent.getStringExtra("userName") != null) {
            userName = intent.getStringExtra("userName");
        }
        oldPhone = "";
        if (intent.getStringExtra("oldPhone") != null) {
            oldPhone = intent.getStringExtra("oldPhone");
        }

        firstInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                secondInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        secondInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                thirdInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        thirdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                fourthInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        fourthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                fifthInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        fifthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sixthInput.requestFocus(); // Chuyển con trỏ tới EditText tiếp theo
            }
        });

        // Xử lý tài khoản mới
        btnCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String inputOTP = firstInput.getText().toString().trim()
                        + secondInput.getText().toString().trim()
                        + thirdInput.getText().toString().trim()
                        + fourthInput.getText().toString().trim()
                        + fifthInput.getText().toString().trim()
                        + sixthInput.getText().toString().trim();

                if (!inputOTP.isEmpty()) {

                    verificationCode = intent.getStringExtra("verificationCode");

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, inputOTP);

                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);

                                Intent intent = null;

                                if (Objects.equals(oldPhone, "")){
                                    // Trường hợp dành cho các trang quên mật khẩu, tạo tài khoản
                                    intent = new Intent(activity_verifyOTP.this, activity_setpassword.class);

                                    intent.putExtra("phone", phone);

                                    if (!Objects.equals(userName, "")) {
                                        intent.putExtra("userName", userName);
                                    }
                                } else {
                                    // Trường hợp cho đổi số điện thoại khách hàng
                                    AuthRepository authRepository = new AuthRepository();

                                    authRepository.changePhone(oldPhone, phone, new AuthRepository.AuthCallbackUpdatePassword() {
                                        @Override
                                        public void onUpdateSuccess() {
                                            Toast.makeText(getApplicationContext(), "Cập nhật số điện thoại thành công!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onUpdateFailure(Exception e) {
                                            Toast.makeText(getApplicationContext(), "Quá trình đổi số điện thoại đã gặp lỗi!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    intent = new Intent(activity_verifyOTP.this, MainActivity.class);

                                }

                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Vui lòng kiếm tra mã OTP!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Hiển thị thông báo khi có thiếu thông tin
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ mã OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}