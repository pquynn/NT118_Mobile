package com.example.foodorderingapp.UI.Activity_Fragment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.Data.Repository.Authentication.AuthRepository;
import com.example.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class activity_verifyOTP extends AppCompatActivity {

    private EditText firstInput, secondInput, thirdInput, fourthInput, fifthInput, sixthInput;
    private Button btnCofirm;
    private FrameLayout btnBack;
    private TextView txtResendOTP;
    private ProgressBar progressBar;
    private String phone;
    private Intent intent;
    private String verificationCode;
    private AuthRepository authRepository = new AuthRepository();
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
        btnBack = findViewById(R.id.btn_back); // Nút quay lại
        txtResendOTP = findViewById(R.id.txtResendOTP);
        progressBar = findViewById(R.id.progressBar);

        intent = getIntent();
        phone = intent.getStringExtra("phone");

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

        // Xử lý khi ấn nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        txtResendOTP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                authRepository.sendOTP(phone, true, activity_verifyOTP.this, new AuthRepository.AuthCallbackOTP() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//
//                    }
//                });
//            }
//        });

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
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String verificationCode;
//                            while (true) {
//                                verificationCode = authRepository.getVerificationCode();
//                                if (verificationCode != null) {
//                                    break;
//                                }
//                                try {
//                                    Thread.sleep(100); // Chờ 100 milliseconds trước khi kiểm tra lại
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.d("OTP", verificationCode);
//                            Log.d("OTP input", inputOTP);
//
//                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, inputOTP);
//
//                            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        progressBar.setVisibility(View.GONE);
//
//                                        // Chuyển qua màn hình đặt lại mật khẩu
//                                        Intent intent = new Intent(activity_verifyOTP.this, activity_setpassword.class);
//                                        intent.putExtra("phone", phone);
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        progressBar.setVisibility(View.GONE);
//
//                                        Toast.makeText(getApplicationContext(), "Vui lòng kiếm tra mã OTP!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    }).start();

//                    if (verificationCode != null){
                    Log.d("OTP", verificationCode);
                    Log.d("OTP input", inputOTP);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(authRepository.getVerificationCode(), inputOTP);

                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);

                                // Chuyển qua màn hình đặt lại mật khẩu
                                Intent intent = new Intent(activity_verifyOTP.this, activity_setpassword.class);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Vui lòng kiếm tra mã OTP!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//                    } else {
//
//                    }
                } else {
                    // Hiển thị thông báo khi có thiếu thông tin
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ mã OTP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}