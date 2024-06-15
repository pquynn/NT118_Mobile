package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;


public class SelectLocation extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapFragment;
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
        currentLocation = null;

        progressBar = findViewById(R.id.progressBar);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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
//                Geocoder geocoder = new Geocoder(SelectLocation.this);
//                List<Address> addressList = null;
//                try {
//                    addressList = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                String location = addressList != null ? addressList.get(0).getAddressLine(0) : null;

                String location = String.valueOf(txtAddress.getText());

                Log.d("location", location);

                // Tìm các vị trí của dấu phẩy
                int lastComma = location.lastIndexOf(",");
                int secondLastComma = location.lastIndexOf(",", lastComma - 1);
                int thirdLastComma = location.lastIndexOf(",", secondLastComma - 1);
                int fourthLastComma = location.lastIndexOf(",", thirdLastComma - 1);

                // Cắt chuỗi lấy theo city, district, ward, address_detail
                String city = location.substring(secondLastComma + 2, lastComma).trim();
                String district = location.substring(thirdLastComma + 2, secondLastComma).trim();
                String ward = location.substring(fourthLastComma + 2, thirdLastComma).trim();
                String address_detail = location.substring(0, fourthLastComma).trim();

                Log.d("city", city);
                Log.d("district", district);
                Log.d("ward", ward);
                Log.d("address_detail", address_detail);


                // Tạo intent để chứa dữ liệu kết quả
                Intent resultIntent = new Intent();
                resultIntent.putExtra("city", city);
                resultIntent.putExtra("district", district);
                resultIntent.putExtra("ward", ward);
                resultIntent.putExtra("address_detail", address_detail);

                // Trả về dữ liệu và chuyển về activity trước đó.
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        iconAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện lấy vị trí hiện tại
                progressBar.setVisibility(View.VISIBLE);
                GetLocation();
                progressBar.setVisibility(View.GONE);
            }
        });

        txtAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        actionId == EditorInfo.IME_ACTION_GO ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    // Đảm bảo chỉ xử lý một lần khi nhận sự kiện
                    if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) {
                        return false;
                    }

                    List<Address> addressList = null;

                    Geocoder geocoder = new Geocoder(SelectLocation.this);
                    try {
                        // Lấy địa chỉ từ dữ liệu tìm kiếm
                        addressList = geocoder.getFromLocationName(String.valueOf(txtAddress.getText()), 1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0); // Lấy địa chỉ đầu tiên

                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                myMap.clear();

                                currentLocation = new Location("");
                                currentLocation.setLatitude(address.getLatitude());
                                currentLocation.setLongitude(address.getLongitude());

                                PrintLocation();

                                myMap.addMarker(new MarkerOptions().position(latLng).title("Vị trí của bạn"));
                                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
                            }
                        });

                        // Ẩn bàn phím
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    } else {
                        Toast.makeText(getApplicationContext(), "Không tìm thấy địa chỉ. Hãy kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // Lấy địa chỉ in địa chỉ lên ô tìm kiếm
    private void PrintLocation() {
        List<Address> addressList = null;

        Geocoder geocoder = new Geocoder(SelectLocation.this);
        try {
            addressList = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            if (addressList != null && !addressList.isEmpty()) {
                String addressLine = addressList.get(0).getAddressLine(0);

                txtAddress.setText(addressLine);
            } else {
                Log.d("Geocoder", "Không tìm thấy địa chỉ từ tọa độ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(this)
                    .setTitle("GPS chưa bật")
                    .setMessage("GPS chưa được bật. Hãy bật GPS để lấy vị trí của bạn!")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        if (location != null) {
                            currentLocation = location;

                            PrintLocation();

                            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            myMap.clear();
                            myMap.addMarker(new MarkerOptions().position(latLng).title("Vị trí của bạn"));
                            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
                        }
                    }
                });

            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng location = new LatLng(10.8700122, 106.802871); // Địa chỉ mặc định là địa chỉ trường
        myMap.addMarker(new MarkerOptions().position(location).title("JavaJoy"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));  // 16f là mức độ phóng to

        currentLocation = new Location("");
        currentLocation.setLatitude(location.latitude);
        currentLocation.setLongitude(location.longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GetLocation();
            } else {
                Toast.makeText(this, "Hãy cấp quyền truy cập vị trí trong Cài đặt!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}