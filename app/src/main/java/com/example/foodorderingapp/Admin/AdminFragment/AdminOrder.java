package com.example.foodorderingapp.Admin.AdminFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.AdminOrderAdapter;

public class AdminOrder extends Fragment {
    private RecyclerView recyclerViewList;
    private AdminOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_order, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.recyclerAdminOrderView);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        adapter = new AdminOrderAdapter();
        recyclerViewList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}