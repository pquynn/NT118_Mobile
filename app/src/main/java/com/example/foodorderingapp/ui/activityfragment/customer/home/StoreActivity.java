package com.example.foodorderingapp.ui.activityfragment.customer.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;

public class StoreActivity extends AppCompatActivity {
    private TextView screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        View includeView = findViewById(R.id.include4);
        screenName = includeView.findViewById(R.id.screen_name);
        screenName.setText("Cửa hàng");
    }
}