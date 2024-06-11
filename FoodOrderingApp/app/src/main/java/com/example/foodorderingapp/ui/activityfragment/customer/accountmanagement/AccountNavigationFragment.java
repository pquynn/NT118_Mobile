package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.repository.authentication.AuthRepository;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.adapter.OrderItemAdapter;
import com.example.foodorderingapp.data.model.OrderItem;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

import java.util.ArrayList;

public class AccountNavigationFragment extends Fragment {
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    private Button btnLogout;
    private RecyclerView recyclerViewList;
    private OrderItemAdapter Adapter;
    private ArrayList<OrderItem> listOrderItem;
    private MainActivity activity;
    AlertDialog progressDialog;


    public AccountNavigationFragment() {
        // Required empty public constructor
    }
    LinearLayout llAM_userInfo, llAM_userpoint, llAM_userAddress, llAM_userOrders, llAM_faqs;
    UserInfoVM viewModel;
    TextView tvNameAcc;

    private final ActivityResultLauncher<Intent> editNameLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String updatedName = data.getStringExtra("updatedName");
                        tvNameAcc.setText(updatedName);
                    }
                    activity.reloadBadge();
                }
            }
    );
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            Intent intent = new Intent(getContext(), activity_login.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
            return null;
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_am_navigation, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (MainActivity) getActivity();
//        activity.reloadBadge();

        btnLogout = view.findViewById(R.id.btn_log_out);

        llAM_userInfo = view.findViewById(R.id.llAM_userInfo);
        llAM_userpoint = view.findViewById(R.id.llAM_userpoint);
        llAM_userAddress = view.findViewById(R.id.llAM_userAddress);
        llAM_userOrders = view.findViewById(R.id.llAM_userOrders);
        llAM_faqs = view.findViewById(R.id.llAM_faqs);

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(true);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        llAM_userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_user_info.class);
                myIntent.putExtra("user_id", userId);
                editNameLauncher.launch(myIntent);
            }
        });

        llAM_userpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_point_history.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });

        llAM_userAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), AM_AddressActivity.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });

        llAM_userOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_my_orders.class);
//                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });

        llAM_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_faqs.class);
                startActivity(myIntent);
            }
        });


        viewModel = new UserInfoVM(userId, getContext());

        tvNameAcc = view.findViewById(R.id.textView_name_acc);
        progressDialog.show();
        viewModel.getUserInfoLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvNameAcc.setText(user.getUserName());
                activity.reloadBadge();
                progressDialog.dismiss();
            }
        });

        // Đăng xuất ứng dụng
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
                String userID = sharedPreferences.getString(KEY_USER_ID, null);
                if (userID != null){
                    AuthRepository authRepository = new AuthRepository();
                    authRepository.logOut(userID);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    // open login activity

                    //reset badge
                    activity.reloadBadge();

                    Intent intent = new Intent(requireContext(), activity_login.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
//                    getActivity().finish();
                }
            }
        });
    }

    //receive result from login activity
    // method to get result from activity through intent (activity2 -> activity1)

    private static final int LOGIN_REQUEST_CODE = 2;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK){
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_area);
            if(data != null && data.hasExtra("isLogin")){
                if(!data.getBooleanExtra("isLogin", false)){
                    navController.navigateUp(); // This will navigate back to the previous fragment
                }
                else {
                    navController.navigate(R.id.accountFragment);

                }
            }
        }

    }
}