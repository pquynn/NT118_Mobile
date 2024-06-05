package com.example.javajoyadmin.ui.activityfragment.admin.accountmanagement;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.User;
import com.example.javajoyadmin.data.repository.authentication.AuthRepository;
import com.example.javajoyadmin.ui.activityfragment.authentication.activity_login;
import com.example.javajoyadmin.ui.viewmodel.admin.accountmanagement.UserInfoVM;

public class AccountManagementFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    TextView screenName;
    TextView tvNameAcc, tvName, tvPhone;
    Button btn_log_out;

    LinearLayout llAM_userInfo_phone, llAM_userInfo_name, llAM_userInfo_password;
    String userId;
    private UserInfoVM viewModel;
    AlertDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            getActivity().finish();
            Intent intent = new Intent(getContext(), activity_login.class);
            startActivity(intent);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        screenName = view.findViewById(R.id.screen_name);
        tvNameAcc = view.findViewById(R.id.textView_name_acc);
        tvName = view.findViewById(R.id.textView_name);
        tvPhone = view.findViewById(R.id.textView_phone);
        btn_log_out = view.findViewById(R.id.btn_log_out);

        llAM_userInfo_name = view.findViewById(R.id.llAM_userInfo_name);
        llAM_userInfo_phone = view.findViewById(R.id.llAM_userInfo_phone);
        llAM_userInfo_password = view.findViewById(R.id.llAM_userInfo_password);

        screenName.setText("Quản lý tài khoản");

        // Create AlertDialog with ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setLayout(50, 50);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Initialize ViewModel
        viewModel = new UserInfoVM(userId, requireContext());

        progressDialog.show();
        viewModel.getUserInfoLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    tvNameAcc.setText(user.getUserName());
                    tvName.setText(user.getUserName());
                    tvPhone.setText(user.getPhone());
                }
                progressDialog.dismiss();
            }
        });

        llAM_userInfo_name.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), am_change_userinfo_name.class);
            myIntent.putExtra("user_id", userId);
            editNameLauncher.launch(myIntent);
        });

        llAM_userInfo_phone.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), am_change_userinfo_phone.class);
            myIntent.putExtra("user_id", userId);
            startActivity(myIntent);
        });

        llAM_userInfo_password.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), am_change_password.class);
            myIntent.putExtra("user_id", userId);
            startActivity(myIntent);
        });

        btn_log_out.setOnClickListener(v -> {
            sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
            String userID = sharedPreferences.getString(KEY_USER_ID, null);
            if (userID != null){
                AuthRepository authRepository = new AuthRepository();
                authRepository.logOut(userID);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                // open login activity
                Intent intent = new Intent(requireContext(), activity_login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private final ActivityResultLauncher<Intent> editNameLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String updatedName = data.getStringExtra("updatedName");
                        tvNameAcc.setText(updatedName);
                        tvName.setText(updatedName);
                    }
                }
            }
    );
}
