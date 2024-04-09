package com.example.foodorderingapp.activity.admin_module.notification;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CouponAdapter;
import com.example.foodorderingapp.adapter.NotificationAdapter;
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_admin);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Thông báo");

        // set recyler list
        recyclerViewNotification();
    }

    private void recyclerViewNotification() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewNotification);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<Notification> notiList = new ArrayList<Notification>();

        // Original date string
        String dateString = "06-03-2025";

        // Define the date format of your input string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Parse the string to obtain a Date object
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        notiList.add(new Notification("Bạn có đơn hàng mới", "Đơn hàng 000 của bạn đã giao thành công", date));
        notiList.add(new Notification("Bạn có yêu cầu hoàn tiền từ đơn hàng 1", "Đơn hàng 000 đang trên đuờng giao", date));
        adapter = new NotificationAdapter(notiList);
        recyclerViewList.setAdapter(adapter);
    }
}
