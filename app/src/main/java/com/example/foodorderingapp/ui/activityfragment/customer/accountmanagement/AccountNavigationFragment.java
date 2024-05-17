package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.OrderItemAdapter;
import com.example.foodorderingapp.data.model.OrderItem;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

import java.util.ArrayList;

public class AccountNavigationFragment extends Fragment {

    private RecyclerView recyclerViewList;
    private OrderItemAdapter Adapter;
    private ArrayList<OrderItem> listOrderItem;

    public AccountNavigationFragment() {
        // Required empty public constructor
    }
    LinearLayout llAM_userInfo, llAM_userpoint, llAM_userAddress, llAM_userOrders, llAM_faqs;
    UserInfoVM viewModel;
    String userId;
    TextView tvNameAcc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_am_navigation, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        llAM_userInfo = view.findViewById(R.id.llAM_userInfo);
        llAM_userpoint = view.findViewById(R.id.llAM_userpoint);
        llAM_userAddress = view.findViewById(R.id.llAM_userAddress);
        llAM_userOrders = view.findViewById(R.id.llAM_userOrders);
        llAM_faqs = view.findViewById(R.id.llAM_faqs);

        userId = "3";

//        Intent intent = getIntent();
//        if (intent != null) {
//            if(intent.hasExtra("user_id")){
//                userId = intent.getStringExtra("user_id");
//            }
//        }
        llAM_userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_user_info.class);
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
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
                myIntent.putExtra("user_id", userId);
                startActivity(myIntent);
            }
        });

//        llAM_faqs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(requireContext(), am_user_info.class);
//        myIntent.putExtra("user_id", userId);
//                startActivity(myIntent);
//            }
//        });


        viewModel = new UserInfoVM(userId, getContext());

        tvNameAcc = view.findViewById(R.id.textView_name_acc);
        viewModel.getUserInfoLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvNameAcc.setText(user.getUserName());
            }
        });
    }
}