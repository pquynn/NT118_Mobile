package com.example.foodorderingapp.activity.customer_module.home;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CategoryAdapter;
import com.example.foodorderingapp.adapter.CategoryHomeAdapter;
import com.example.foodorderingapp.domain.CategoryDomain;
import com.example.foodorderingapp.domain.HomeCategory;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    RecyclerView rcv_homeCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcv_homeCategory = findViewById(R.id.rcv_homeCategory);
        rcv_homeCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        CategoryHomeAdapter adapter = new CategoryHomeAdapter(this, getHomeCategory());
        rcv_homeCategory.setAdapter(adapter);

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
