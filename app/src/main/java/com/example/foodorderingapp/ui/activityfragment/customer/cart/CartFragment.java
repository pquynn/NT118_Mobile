package com.example.foodorderingapp.ui.activityfragment.customer.cart;

import android.content.Intent;
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

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.BuyingProduct;
import com.example.foodorderingapp.ui.activityfragment.customer.checkout.CheckoutActivity;
import com.example.foodorderingapp.ui.adapter.CartAdapter;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.FragmentCartBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {
    TextView screenname;
    private Map<String, OrderItem> orderItemMap;
    private Map<String, BuyingProduct> buyingProducts;
    private String userId ="3";
    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set top navigation text
        screenname = view.findViewById(R.id.screen_name);
        screenname.setText("Giỏ hàng");

        //button checkout
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            }
        });

        // View model
        viewModel = new CartViewModel(userId);
        //Adapter
        buyingProducts = new HashMap<>();
        adapter = new CartAdapter(buyingProducts, viewModel);
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewCart.setAdapter(adapter);


        viewModel.getBuyingProducts().observe(getViewLifecycleOwner(), new Observer<Map<String, BuyingProduct>>() {
            @Override
            public void onChanged(Map<String, BuyingProduct> buyingProductMap) {
                binding.setCartVM(viewModel);
                buyingProducts.clear();
                buyingProducts.putAll(buyingProductMap);
                adapter.notifyDataSetChanged();

            }
        });

    }

}