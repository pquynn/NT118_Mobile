package com.example.foodorderingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodorderingapp.adapter.CartAdapter;
import com.example.foodorderingapp.domain.OrderDetail;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    TextView screenName;
    RecyclerView recyclerViewList;
    CartAdapter adapter;
    ArrayList<OrderDetail> productList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // set top navigation text
        screenName = view.findViewById(R.id.screen_name);
        screenName.setText("Giỏ hàng");

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