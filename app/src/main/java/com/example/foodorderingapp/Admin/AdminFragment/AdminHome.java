package com.example.foodorderingapp.Admin.AdminFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.AdminHomeAdapter;

public class AdminHome extends Fragment {
    private RecyclerView recyclerViewList;
    private AdminHomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.recyclerAdminHomeView);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        long revenue = 999999;
        long order = 999;
        long refund = 999999;

        adapter = new AdminHomeAdapter(revenue, order, refund);
        recyclerViewList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}