package com.example.foodorderingapp.ui.activityfragment.customer.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodorderingapp.data.model.BuyingProduct;
import com.example.foodorderingapp.ui.adapter.CartAdapter;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.FragmentCartBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {
    private Button btnCheckout;
    private TextView screenName;
//
//    private ArrayList<OrderDetail> productList;

    private Map<String, OrderItem> orderItemMap;
    private Map<String, BuyingProduct> buyingProducts;
    private String userId ="3";
    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;


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
        buyingProducts = new HashMap<>();

//        adapter = new CartAdapter(buyingProducts);
//        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.recyclerViewCart.setAdapter(adapter);

        // Initialize ViewModel using ViewModelProvider
        viewModel = new CartViewModel(userId);
        viewModel.getBuyingProducts().observe(getViewLifecycleOwner(), new Observer<Map<String, BuyingProduct>>() {
            @Override
            public void onChanged(Map<String, BuyingProduct> buyingProductMap) {
                binding.setCartVM(viewModel);
                Log.d("firebase", "activity = " + buyingProductMap.toString());
                adapter = new CartAdapter(buyingProducts);
                binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.recyclerViewCart.setAdapter(adapter);
//                buyingProducts.clear();
//                buyingProducts.putAll(buyingProductMap);
//                adapter.notifyDataSetChanged();

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




    }

}