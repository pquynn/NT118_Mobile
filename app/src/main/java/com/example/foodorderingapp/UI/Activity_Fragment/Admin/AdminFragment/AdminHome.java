package com.example.foodorderingapp.UI.Activity_Fragment.Admin.AdminFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Adapter.AdminHomeAdapter;

public class AdminHome extends Fragment {
    private RecyclerView recyclerViewList;
    private AdminHomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        return view;
    }
}