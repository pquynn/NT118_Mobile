package com.example.foodorderingapp.UI.Activity_Fragment.Customer.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Activity_Fragment.Customer.Checkout.CheckoutActivity;
import com.example.foodorderingapp.UI.Adapter.CartAdapter;
import com.example.foodorderingapp.Data.Model.OrderDetail;
import com.example.foodorderingapp.UI.ViewModel.Customer.Cart.CartViewModel;
import com.example.foodorderingapp.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private Button btnCheckout;
    private TextView screenName;
//    private RecyclerView recyclerViewList;
//
//    private ArrayList<OrderDetail> productList;


    private String userId;
    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();



//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerViewList = view.findViewById(R.id.recyclerViewCart);
//        recyclerViewList.setLayoutManager(linearLayoutManager);
//        productList = new ArrayList<OrderDetail>();
//        adapter = new CartAdapter(productList);
//        recyclerViewList.setAdapter(adapter);
//
////        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "", 3));
////        productList.add(new OrderDetail("Bánh", 45000, "Lớn", "", 2));
////        productList.add(new OrderDetail("Trà sữa trân châu", 45000, "Lớn", "", 3));
//        adapter.notifyDataSetChanged();
//
//
//
//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set top navigation text
        screenName = view.findViewById(R.id.screen_name);
        screenName.setText("Giỏ hàng");

        // btn checkout click event: open Checkout activity
        btnCheckout = view.findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            }
        });








        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);

        // Initialize RecyclerView and adapter
        adapter = new CartAdapter();
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewCart.setAdapter(adapter);

        // Observe the LiveData from the ViewModel
//        viewModel.getOrderItemById(userId).observe(getViewLifecycleOwner(), orderItems -> adapter.submitList(orderItems));

//
//         List<OrderItem> newOrderItems = // Get your list of OrderItems here
//         viewModel.updateOrderItems(newOrderItems);
    }
}