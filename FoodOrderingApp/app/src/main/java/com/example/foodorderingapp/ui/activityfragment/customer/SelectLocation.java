package com.example.foodorderingapp.ui.activityfragment.customer;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SelectLocation extends AppCompatActivity implements OnMapReadyCallback {

//    private final int FINE_PERMISSION_CODE = 1;
//    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView screenName;
    private EditText txtAddress;
    private ProgressBar progressBar;
    private GoogleMap myMap;
    private ImageView btnBack, iconAddress;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        screenName = findViewById(R.id.screen_name);
        screenName.setText("Địa chỉ nhận hàng");
        btnBack = findViewById(R.id.img_lessthan);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtAddress = findViewById(R.id.txtAddress);
        iconAddress = findViewById(R.id.img_location);

        progressBar = findViewById(R.id.progressBar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi ấn nút Back
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi xác nhận địa chỉ

                finish(); // Quay về trang trước
            }
        });

        iconAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện lấy vị trí hiện tại

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng location = new LatLng(10.8700122, 106.802871); // Địa chỉ mặc định là địa chỉ trường
        myMap.addMarker(new MarkerOptions().position(location).title("UIT"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));  // 16f là mức độ phóng to
    }
}