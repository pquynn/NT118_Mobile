package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.PointAdapter;
import com.example.foodorderingapp.domain.Point;

import java.util.ArrayList;

public class am_point_history extends AppCompatActivity {
    private RecyclerView.Adapter PointAdapter;
    private RecyclerView recyclerViewList;
    private ArrayList<Point> listPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_point_history);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Tích điểm");

        recyclerViewPoint();
    }
    private void recyclerViewPoint(){
        recyclerViewList = findViewById(R.id.recyclerViewPoint);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        listPoint = new ArrayList<>();
        createPointList();

        PointAdapter = new PointAdapter(listPoint);
        recyclerViewList.setAdapter(PointAdapter);

    }
    private void createPointList(){
        listPoint.add(new Point(-100, "2024-04-07"));
        listPoint.add(new Point(200, "2024-04-01"));
        listPoint.add(new Point(0, "2024-04-03"));
        listPoint.add(new Point(-400, "2024-04-02"));
        listPoint.add(new Point(500, "2024-03-07"));
        listPoint.add(new Point(-600, "2024-02-07"));
        listPoint.add(new Point(700, "2024-01-07"));
        listPoint.add(new Point(-800, "2024-03-08"));
        listPoint.add(new Point(100, "2024-04-07"));
        listPoint.add(new Point(200, "2024-04-01"));
        listPoint.add(new Point(-300, "2024-04-03"));
        listPoint.add(new Point(400, "2024-04-02"));
        listPoint.add(new Point(500, "2024-03-07"));
        listPoint.add(new Point(600, "2024-02-07"));
        listPoint.add(new Point(700, "2024-01-07"));
        listPoint.add(new Point(800, "2024-03-08"));
        listPoint.add(new Point(100, "2024-04-07"));
        listPoint.add(new Point(200, "2024-04-01"));
        listPoint.add(new Point(300, "2024-04-03"));
        listPoint.add(new Point(400, "2024-04-02"));
        listPoint.add(new Point(500, "2024-03-07"));
        listPoint.add(new Point(600, "2024-02-07"));
        listPoint.add(new Point(700, "2024-01-07"));
        listPoint.add(new Point(800, "2024-03-08"));
    }


}