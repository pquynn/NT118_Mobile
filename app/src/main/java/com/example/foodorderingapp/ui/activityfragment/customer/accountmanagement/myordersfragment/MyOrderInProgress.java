package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.ui.adapter.OrderItemAdapter;
import com.example.foodorderingapp.data.model.OrderItem;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.MyOrdersVM;

import java.util.ArrayList;
import java.util.List;

public class MyOrderInProgress extends Fragment {
    //Đang xử lý
    private ArrayList<OrderItem> listOrderItem = new ArrayList<>();
    private RecyclerView recyclerViewList;
    private OrderItemAdapter Adapter;
    private MyOrdersVM viewModel, viewModel2;
    private String userId = "";
    AlertDialog progressDialog;

    public MyOrderInProgress() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order_in_progress, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("user_id");
        }

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory(){
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MyOrdersVM.class)) {
                    return (T) new MyOrdersVM(userId, "Chờ xác nhận");
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(MyOrdersVM.class);

        viewModel2 = new ViewModelProvider(this, new ViewModelProvider.Factory(){
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MyOrdersVM.class)) {
                    return (T) new MyOrdersVM(userId, "Đã xác nhận");
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(MyOrdersVM.class);

        recyclerViewList = view.findViewById(R.id.recyclerViewOrderItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);
        Adapter = new OrderItemAdapter(listOrderItem);
        recyclerViewList.setAdapter(Adapter);
        //progressDialog.show();
        viewModel.getOrderListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                Log.d("MyOrderInProgress", "Received data from viewModel1: " + orders.size() + " orders");
                updateOrderList(orders);
            }
        });

        viewModel2.getOrderListLiveData2().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                Log.d("MyOrderInProgress", "Received data from viewModel2: " + orders.size() + " orders");
                updateOrderList(orders);
            }
        });

    }
    private void updateOrderList(List<Order> orders) {
        for (Order order : orders) {
            if (order != null) {
                listOrderItem.add(new OrderItem(order.getId(), order.getTotalPrice(), order.getTotalProduct()));
            }
        }
        Adapter.notifyDataSetChanged();
    }




}