package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private MyOrdersVM viewModel;
    private String userId = "";

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("user_id");
        }

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory(){
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MyOrdersVM.class)) {
                    return (T) new MyOrdersVM(userId, "Đang xử lý");
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(MyOrdersVM.class);
        recyclerViewList = view.findViewById(R.id.recyclerViewOrderItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);

        viewModel.getOrderListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                for(Order i : orders){
                    if(i!=null){
                        listOrderItem.add(new OrderItem(i.getId(), i.getTotalPrice(), i.getTotalProduct()));
                    }
                }
                Adapter = new OrderItemAdapter(listOrderItem);
                recyclerViewList.setAdapter(Adapter);
            }
        });

    }





}