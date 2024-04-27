package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Adapter.OrderItemAdapter;
import com.example.foodorderingapp.Data.Model.OrderItem;
import com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement.am_user_info;

import java.util.ArrayList;

public class AccountNavigationFragment extends Fragment {

    private RecyclerView recyclerViewList;
    private OrderItemAdapter Adapter;
    private ArrayList<OrderItem> listOrderItem;

    public AccountNavigationFragment() {
        // Required empty public constructor
    }

//    public static MyOrderCancelled newInstance(String param1, String param2) {
//        MyOrderCancelled fragment = new MyOrderCancelled();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    ImageView btn_goto1, btn_goto2, btn_goto3, btn_goto4, btn_goto5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_am_navigation, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_goto1 = view.findViewById(R.id.am_btn_goto1);
        btn_goto2 = view.findViewById(R.id.am_btn_goto2);
        btn_goto3 = view.findViewById(R.id.am_btn_goto3);
        btn_goto4 = view.findViewById(R.id.am_btn_goto4);
        btn_goto5 = view.findViewById(R.id.am_btn_goto5);

        btn_goto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_user_info.class);
                startActivity(myIntent);
            }
        });

        btn_goto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_point_history.class);
                startActivity(myIntent);
            }
        });

        btn_goto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), AM_AddressActivity.class);
                startActivity(myIntent);
            }
        });

        btn_goto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(requireContext(), am_my_orders.class);
                startActivity(myIntent);
            }
        });

//        btn_goto5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(requireContext(), am_user_info.class);
//                startActivity(myIntent);
//            }
//        });
    }
}