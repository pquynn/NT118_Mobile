package com.example.foodorderingapp.UI.Activity_Fragment.Customer.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderingapp.Data.Model.HomeCategory;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Adapter.CategoryHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rcv_homeCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rcv_homeCategory = view.findViewById(R.id.rcv_homeCategory);
        rcv_homeCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryHomeAdapter adapter = new CategoryHomeAdapter(getActivity(), getHomeCategory());
        rcv_homeCategory.setAdapter(adapter);

        return view;
    }

    private List<HomeCategory> getHomeCategory() {
        List<HomeCategory> list = new ArrayList<>();

        list.add(new HomeCategory("All"));
        list.add(new HomeCategory("Cà Phê"));
        list.add(new HomeCategory("Trà Sữa"));
        list.add(new HomeCategory("Trà"));
        list.add(new HomeCategory("Bánh"));

        return list;
    }
}