package com.example.foodorderingapp.ui.activityfragment.customer.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodorderingapp.ui.adapter.CartAdapter;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.viewmodel.cart.CartViewModel;
import com.example.foodorderingapp.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {
    private Button btnCheckout;
    private TextView screenName;
    private RecyclerView recyclerView;
//
//    private ArrayList<OrderDetail> productList;

    private Map<String, OrderItem> orderItemMap;
    private String userId ="3";
    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;
    private Order cart;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = FragmentCartBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//
//
//
////
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
////        recyclerViewList = view.findViewById(R.id.recyclerViewCart);
////        recyclerViewList.setLayoutManager(linearLayoutManager);
////        productList = new ArrayList<OrderDetail>();
////        adapter = new CartAdapter(productList);
////        recyclerViewList.setAdapter(adapter);
////
//////        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "", 3));
//////        productList.add(new OrderDetail("Bánh", 45000, "Lớn", "", 2));
//////        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "", 3));
////        adapter.notifyDataSetChanged();
////
////
////
////        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        //set top navigation text
//        screenName = view.findViewById(R.id.screen_name);
//        screenName.setText("Giỏ hàng");
//
//        // btn checkout click event: open Checkout activity
//        btnCheckout = view.findViewById(R.id.btn_checkout);
//        btnCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), CheckoutActivity.class));
//            }
//        });
//
//
//
//
//        // Initialize ViewModel
//        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
//
//        // Initialize RecyclerView and adapter
//        adapter = new CartAdapter();
//        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.recyclerViewCart.setAdapter(adapter);
//
//        // Observe the LiveData from the ViewModel
////        viewModel.getOrderItemById(userId).observe(getViewLifecycleOwner(), orderItems -> adapter.submitList(orderItems));
//
////
////         List<OrderItem> newOrderItems = // Get your list of OrderItems here
////         viewModel.updateOrderItems(newOrderItems);
//
//        // Observe the LiveData from the ViewModel
////        viewModel.getOrderItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<OrderItem>>() {
////            @Override
////            public void onChanged(List<OrderItem> orderItems) {
////                // Update the adapter with the new list of order items
////                adapter.setData(orderItems);
////            }
////        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
        orderItemMap = new HashMap<>();
//        OrderItem orderItem1 = new OrderItem();
//        OrderItem orderItem2 = new OrderItem();
//        orderItem1.setSize("Lớn");
//        orderItem2.setSize("Nhỏ");
//        orderItem1.setPrice(1000);
//        orderItem2.setPrice(1000);
//        orderItemMap.put("1", orderItem1);
//        orderItemMap.put("2", orderItem2);

        adapter = new CartAdapter(orderItemMap);
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewCart.setAdapter(adapter);

        // Initialize ViewModel using ViewModelProvider
        viewModel = new CartViewModel("3");
//        adapter.setData(viewModel.getOrderMutableLiveData().getValue().getOrderItem());
        viewModel.getOrderMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                binding.setCartVM(viewModel);
//                adapter.setData(order.getOrderItem());
                orderItemMap.clear();
                orderItemMap.putAll(order.getOrderItem());
                adapter.notifyDataSetChanged();
            }
        });


        // Set top navigation text
//        binding.screenName.setText("Giỏ hàng");

        // Button checkout click event: open Checkout activity
//        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), CheckoutActivity.class));
//            }
//        });

        // Initialize RecyclerView and adapter
        // Observe the LiveData from the ViewModel and update UI accordingly
//        ArrayList<OrderItem> orderItems = new ArrayList<>();
//        adapter = new CartAdapter(orderItems);
//        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.recyclerViewCart.setAdapter(adapter);




    }

}