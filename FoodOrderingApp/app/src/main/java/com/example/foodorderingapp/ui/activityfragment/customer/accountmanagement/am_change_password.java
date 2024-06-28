package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.repository.authentication.AuthRepository;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_forgetpassword;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class am_change_password extends AppCompatActivity {

    FrameLayout btnBack;
    EditText input_old_password, input_new_password;
    Button btn_savechange_password;
    TextView notice_password, forgot_oldpassword;
    ImageButton imgBtnVisibilityOld, imgBtnVisibilityNew;
    String userId = "";
    UserInfoVM viewModel;
    AlertDialog progressDialog;
    AuthRepository repository = new AuthRepository();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_change_password);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đổi mật khẩu");

        btnBack = findViewById(R.id.btn_back);
        btn_savechange_password = findViewById(R.id.btn_savechange_password);
        input_old_password = findViewById(R.id.input_old_password);
        input_new_password = findViewById(R.id.input_new_password);
        notice_password = findViewById(R.id.notice_password);
        forgot_oldpassword = findViewById(R.id.forgot_oldpassword);
        imgBtnVisibilityOld = findViewById(R.id.imgBtnVisibilityOld);
        imgBtnVisibilityNew = findViewById(R.id.imgBtnVisibilityNew);
        notice_password.setVisibility(View.GONE);

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(true);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }

        viewModel = new UserInfoVM(userId, this);

        progressDialog.show();
        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String phone = user.getPhone();
                progressDialog.dismiss();

                input_old_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            String oldPassword = md5(String.valueOf(input_old_password.getText()));
                            Log.d("check oldpass", "Old password" + oldPassword);
                            progressDialog.show();
                            repository.validateUser(phone, oldPassword, new AuthRepository.AuthCallback() {
                                @Override
                                public void onLoginSuccess(String data) {
                                    if(notice_password.getVisibility() == View.VISIBLE){
                                        notice_password.setVisibility(View.GONE);
                                    }
                                    progressDialog.dismiss();
                                }
                                @Override
                                public void onLoginFailure(Exception e) {
                                    progressDialog.dismiss();
                                    notice_password.setVisibility(View.VISIBLE);
                                    notice_password.setText("*Sai mật khẩu cũ");
                                }
                            });
                        }
                    }
                });

                btn_savechange_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input_old = input_old_password.getText().toString().trim();
                        String input_new = input_new_password.getText().toString().trim();
                        if(notice_password.getVisibility() == View.VISIBLE){
                            Toast.makeText(getApplicationContext(), "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                        }
                        else if(input_old.isEmpty() && !input_new.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
                        }
                        else if(input_new.isEmpty() && !input_old.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                        }
                        else if(input_new.isEmpty() && input_old.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String newPassword = md5(String.valueOf(input_new_password.getText()));

                            progressDialog.show();
                            repository.changePassword(phone, newPassword, new AuthRepository.AuthCallbackUpdatePassword() {
                                @Override
                                public void onUpdateSuccess() {
                                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();
                                }

                                @Override
                                public void onUpdateFailure(Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Lỗi đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        forgot_oldpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_forgetpassword.class);
                startActivity(intent);
            }
        });

        final boolean[] passwordVisibleOld = {false};
        final boolean[] passwordVisibleNew = {false};

        imgBtnVisibilityOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (passwordVisibleOld[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    input_old_password.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisibleOld[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    imgBtnVisibilityOld.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    input_old_password.setSelection(input_old_password.getText().length());
                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    input_old_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordVisibleOld[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    imgBtnVisibilityOld.setImageResource(R.drawable.visibility_off);
                    // Di chuyển con trỏ về cuối chuỗi
                    input_old_password.setSelection(input_old_password.getText().length());
                }
            }
        });

        imgBtnVisibilityNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi kiểu hiển thị của EditText
                if (passwordVisibleNew[0]) {
                    // Nếu mật khẩu đang hiển thị, ẩn nó
                    input_new_password.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisibleNew[0] = false;
                    // Đổi hình ảnh của ImageButton thành biểu tượng ẩn mật khẩu
                    imgBtnVisibilityNew.setImageResource(R.drawable.visibility);
                    // Di chuyển con trỏ về cuối chuỗi
                    input_new_password.setSelection(input_new_password.getText().length());
                } else {
                    // Nếu mật khẩu đang ẩn, hiển thị nó
                    input_new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordVisibleNew[0] = true;
                    // Đổi hình ảnh của ImageButton thành biểu tượng hiển thị mật khẩu
                    imgBtnVisibilityNew.setImageResource(R.drawable.visibility_off);
                    // Di chuyển con trỏ về cuối chuỗi
                    input_new_password.setSelection(input_new_password.getText().length());
                }
            }
        });


    }

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