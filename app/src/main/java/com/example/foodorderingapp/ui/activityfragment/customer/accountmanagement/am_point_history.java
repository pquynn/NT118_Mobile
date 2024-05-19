package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.PointAdapter;
import com.example.foodorderingapp.data.model.entity.UserPoint;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.PointVM;

import java.util.ArrayList;
import java.util.Collections;

public class am_point_history extends AppCompatActivity {
    private RecyclerView.Adapter PointAdapter;
    private RecyclerView recyclerViewList;
    private ArrayList<UserPoint> listPoint;
    private String userId = "";
    private PointVM viewModel;
    private TextView tvTotalPoint;
    private FrameLayout btnBack;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_point_history);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Tích điểm");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("user_id")){
                userId = intent.getStringExtra("user_id");
            }
        }

        viewModel = new PointVM(userId, this);

        recyclerViewPoint();
    }
    private void recyclerViewPoint(){
        recyclerViewList = findViewById(R.id.recyclerViewPoint);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        listPoint = new ArrayList<>();
        viewModel.getPointList().observe(this, new Observer<ArrayList<UserPoint>>() {
            @Override
            public void onChanged(ArrayList<UserPoint> userPoints) {
                for(UserPoint i : userPoints){
                    listPoint.add(i);

                }
                Collections.reverse(listPoint);
                PointAdapter = new PointAdapter(listPoint);
                recyclerViewList.setAdapter(PointAdapter);
            }
        });

        tvTotalPoint = findViewById(R.id.total_point);
        viewModel.getTotalPoint().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String total = integer.toString();
                tvTotalPoint.setText(total);
            }
        });

    }

}