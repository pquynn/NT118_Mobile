package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

public class am_change_userinfo_name extends AppCompatActivity {
    String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    private UserInfoVM viewModel;
    Button btn_saveChanges;
    EditText textinput_name;
    private FrameLayout btnBack;
    UserInfoRepository repository_user  = new UserInfoRepository();
    AlertDialog progressDialog;
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
        setContentView(R.layout.activity_am_change_userinfo_name);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chỉnh sửa thông tin");

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

        progressDialog.show();
        viewModel = new UserInfoVM(userId, this);
        textinput_name = findViewById(R.id.textinputAM_userinfo_name);
        viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textinput_name.setText(user.getUserName());
                progressDialog.dismiss();
            }
        });

        btn_saveChanges = findViewById(R.id.btnAM_savechangeUserInfoName);

        progressDialog.show();
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = String.valueOf(textinput_name.getText());

                if(user_name.equals("")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên của bạn!", Toast.LENGTH_SHORT).show();
                }else{
                    repository_user.updateUserName(userId, user_name, new UserInfoRepository.updateNameCallback() {
                        @Override
                        public void updateNameSuccess() {
                            Toast.makeText(getApplicationContext(), "Cập nhật tên của bạn thành công!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            // Trả kết quả về Activity
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("updatedName", user_name);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }

                        @Override
                        public void updateNameError(Exception e) {
                            Toast.makeText(getApplicationContext(), "Xảy ra lỗi khi cập nhật tên!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}