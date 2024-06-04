package com.example.javajoyadmin.ui.activityfragment.admin.accountmanagement;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.User;
import com.example.javajoyadmin.ui.viewmodel.admin.accountmanagement.UserInfoVM;

public class AccountManagementFragment extends Fragment {

    TextView screenName;
    TextView tvNameAcc, tvName, tvPhone;
    Button btn_log_out;

    LinearLayout llAM_userInfo_phone, llAM_userInfo_name, llAM_userInfo_password;
    String userId = "";
    private UserInfoVM viewModel;
    AlertDialog progressDialog;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_management, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        screenName = view.findViewById(R.id.screen_name);
        screenName.setText("Quản lý tài khoản");

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        userId = "4";

        viewModel = new UserInfoVM(userId, requireContext());

        tvNameAcc = view.findViewById(R.id.textView_name_acc);
        tvName = view.findViewById(R.id.textView_name);
        tvPhone = view.findViewById(R.id.textView_phone);

        progressDialog.show();
        viewModel.getUserInfoLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvNameAcc.setText(user.getUserName());
                tvName.setText(user.getUserName());
                tvPhone.setText(user.getPhone());
                progressDialog.dismiss();
            }
        });


        llAM_userInfo_name = view.findViewById(R.id.llAM_userInfo_name);
        llAM_userInfo_phone = view.findViewById(R.id.llAM_userInfo_phone);
        llAM_userInfo_password = view.findViewById(R.id.llAM_userInfo_password);

        llAM_userInfo_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_change_userinfo_name.class);
                myIntent.putExtra("user_id", userId);
                editNameLauncher.launch(myIntent);
            }
        });

        llAM_userInfo_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_change_userinfo_phone.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });
        llAM_userInfo_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_change_password.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}