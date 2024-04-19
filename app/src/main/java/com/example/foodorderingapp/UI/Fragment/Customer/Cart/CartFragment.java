package com.example.foodorderingapp.UI.Fragment.Customer.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Activity.Customer.Checkout.CheckoutActivity;
import com.example.foodorderingapp.adapter.CartAdapter;
import com.example.foodorderingapp.domain.OrderDetail;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private Button btnCheckout;
    private TextView screenName;
    private RecyclerView recyclerViewList;
    private CartAdapter adapter;
    private ArrayList<OrderDetail> productList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // set top navigation text
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.recyclerViewCart);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        productList = new ArrayList<OrderDetail>();
        adapter = new CartAdapter(productList);
        recyclerViewList.setAdapter(adapter);

        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "", 3));
        productList.add(new OrderDetail("Bánh", "60.000 đ", "Lớn", "", 2));
        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "", 3));
        adapter.notifyDataSetChanged();

        return view;
    }
}