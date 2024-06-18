package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
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


public class MyOrderCancelled extends Fragment {
    //Đã hủy
    private RecyclerView recyclerViewList;
    private OrderItemAdapter Adapter;
    private ArrayList<OrderItem> listOrderItem;
    private MyOrdersVM viewModel;
    private String userId = "";
    private ConstraintLayout no_orders_container;
    public MyOrderCancelled() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order_cancelled, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        no_orders_container = view.findViewById(R.id.no_orders_container);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("user_id");
        }
        Log.d("get user id in fragment order", "user id: " + userId);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory(){
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MyOrdersVM.class)) {
                    return (T) new MyOrdersVM(userId, "Đã hủy");
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(MyOrdersVM.class);

        recyclerViewList = view.findViewById(R.id.recyclerViewOrderItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);

        listOrderItem = new ArrayList<>();
        Adapter = new OrderItemAdapter(listOrderItem);
        recyclerViewList.setAdapter(Adapter);
    }

    public void loadListOrder(){
        viewModel.getOrderListLiveData().removeObservers(this);

        viewModel.getOrderListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                listOrderItem.clear();
                Adapter.notifyDataSetChanged();

                if(orders != null){
                    for(Order i : orders){
                        if(i!=null){
                            listOrderItem.add(new OrderItem(i.getId(), (int)i.getOrderPrice(), i.getTotalProduct()));
                        }
                    }
                    Adapter.notifyDataSetChanged();
                }
                else{
                    no_orders_container.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Order in progress: ", "get in resume");
        loadListOrder();
    }
}