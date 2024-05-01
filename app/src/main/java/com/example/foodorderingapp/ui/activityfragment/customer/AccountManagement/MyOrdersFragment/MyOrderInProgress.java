package com.example.foodorderingapp.ui.activityfragment.customer.AccountManagement.MyOrdersFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.OrderItemAdapter;
import com.example.foodorderingapp.data.model.OrderItem;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link MyOrderInProgress#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MyOrderInProgress extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<OrderItem> listOrderItem;
    private RecyclerView recyclerViewList;
    private OrderItemAdapter Adapter;

    public MyOrderInProgress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrderInProgress.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderInProgress newInstance(String param1, String param2) {
        MyOrderInProgress fragment = new MyOrderInProgress();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
//    private RecyclerView.Adapter OrderItemAdapter;
//    private RecyclerView recyclerViewList;
//    private ArrayList<OrderItemDomain> listOrderItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        recyclerViewOrderItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order_in_progress, container, false);
    }

//    private void recyclerViewOrderItem(){
//        recyclerViewList =
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerViewList.setLayoutManager(linearLayoutManager);
//
//        listPoint = new ArrayList<>();
//        createPointList();
//
//        PointAdapter = new PointAdapter(listPoint);
//        recyclerViewList.setAdapter(PointAdapter);
//
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();
        recyclerViewList = view.findViewById(R.id.recyclerViewOrderItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);

        Adapter = new OrderItemAdapter(listOrderItem);
        recyclerViewList.setAdapter(Adapter);

    }

    private void dataInitialize() {
        listOrderItem = new ArrayList<>();
        listOrderItem.add(new OrderItem("#order001", 200000, 5));
        listOrderItem.add(new OrderItem("#order002", 300000, 6));
        listOrderItem.add(new OrderItem("#order003", 400000, 7));
        listOrderItem.add(new OrderItem("#order004", 500000, 8));
        listOrderItem.add(new OrderItem("#order005", 600000, 9));
        listOrderItem.add(new OrderItem("#order006", 700000, 10));
    }




}