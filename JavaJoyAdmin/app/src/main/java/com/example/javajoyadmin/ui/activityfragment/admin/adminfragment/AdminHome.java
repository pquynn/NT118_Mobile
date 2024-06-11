package com.example.javajoyadmin.ui.activityfragment.admin.adminfragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.ui.activityfragment.admin.AdminMainActivity;
import com.example.javajoyadmin.ui.activityfragment.authentication.activity_login;
import com.example.javajoyadmin.ui.viewmodel.admin.home.AdminHomeVM;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminHome extends Fragment {

    private TextView txtRevenue, txtTotalOrder, txtRefund;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private AdminHomeVM viewModel;
    private Date selectedDate;
    private Date lineChartYear;
    private ProgressBar progressBar;
    private LineChart lineChart;
    private List<String> xValues;
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";

    private static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            getActivity().finish();
            Intent intent = new Intent(getContext(), activity_login.class);
            startActivity(intent);
        }

        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        lineChartYear = new Date();

        initDatePicker(view);
        initVariables(view);
        initLineChart();

        progressBar.setVisibility(View.VISIBLE);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(AdminHomeVM.class)) {
                    return (T) new AdminHomeVM(selectedDate, lineChartYear);
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(AdminHomeVM.class);

        updateViewModel();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateViewModel() {
        viewModel.getTotalOrder().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalOrder) {
                txtTotalOrder.setText(totalOrder + " đơn");
                progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getTotalRefund().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double refund) {
                txtRefund.setText(formatNumber(refund) + " Đ");
            }
        });

        viewModel.getTotalRevenue().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double revenue) {
                txtRevenue.setText(formatNumber(revenue) + " Đ");
            }
        });

        viewModel.getDataLineChart().observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] ints) {
                updateDateLineChart(ints);
            }
        });
    }

    private void initVariables(View view) {
        txtRevenue = view.findViewById(R.id.txtRevenue);
        txtRefund = view.findViewById(R.id.txtRefund);
        txtTotalOrder = view.findViewById(R.id.txtTotalOrder);
        dateButton = view.findViewById(R.id.datePickerButton);

        if (selectedDate == null) // Trường hợp chưa chọn ngày
            dateButton.setText(getTodaysDate());
        else // Tránh trường hợp khi chuyển trang với Bottom Navigation thì hiển thị ngày bị đổi
            dateButton.setText("Ngày " + selectedDate.getDate() + " - " + String.valueOf(selectedDate.getMonth() + 1) + " - " + String.valueOf(selectedDate.getYear() + 1900));
        // Date: Tháng = Tháng + 1,
        // Năm = Năm + 1900 (VD: 2024 = 124 + 1900)

        progressBar = view.findViewById(R.id.progressBar);
        lineChart = view.findViewById(R.id.lineChart);
    }

//    private void initLineChart() {
//
//        int white = ContextCompat.getColor(getContext(), R.color.white);
//        xValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"); // Danh sách tháng
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(14f); // Đặt kích thước chữ cho trục x
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
//        xAxis.setLabelCount(xValues.size());
//        xAxis.setGridColor(white);
//        xAxis.setGranularity(1f);
//
//        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setAxisMinimum(0f);
//        yAxis.setAxisMaximum(0f);
//        yAxis.setAxisLineWidth(2f);
//        yAxis.setAxisLineColor(Color.BLACK);
//        yAxis.setLabelCount(10);
//        yAxis.setGridColor(white);
//        yAxis.setTextSize(14f); // Đặt kích thước chữ cho trục y
//        yAxis.setValueFormatter(new IntegerValueFormatter()); // Sử dụng IntegerValueFormatter để định dạng số nguyên
//
//        // Right Y-axis settings
//        lineChart.getAxisRight().setEnabled(false); // Disable the right Y-axis
//
//        List<Entry> data = new ArrayList<>(); // Dữ liệu khởi tạo cho giá trị cột Y
//        for (int i = 0; i < 12; i++)
//            data.add(new Entry(i, 0));
//
//        LineDataSet dataSet1 = new LineDataSet(data, "Số hóa đơn năm " + String.valueOf(lineChartYear.getYear() + 1900));
//        dataSet1.setColor(Color.BLUE);
//        dataSet1.setValueTextSize(16f); // Đặt kích thước chữ cho thông tin
//
//        LineData lineData = new LineData(dataSet1);
//
//        lineChart.setData(lineData);
//
//        lineChart.invalidate();
//    }

    private void initLineChart(){
        int white = ContextCompat.getColor(getContext(), R.color.white);
        int black = ContextCompat.getColor(getContext(), R.color.black);
        int primary = ContextCompat.getColor(getContext(), R.color.primary);
        int lightGray = ContextCompat.getColor(getContext(), R.color.lightgray);

        xValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"); // Danh sách tháng

// X-axis settings
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f); // Set text size for X-axis
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(xValues.size());
        xAxis.setGridColor(lightGray); // Set grid color for X-axis
        xAxis.setGranularity(1f);
        xAxis.setTextColor(black); // Set text color for X-axis

// Y-axis settings
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f); // Assuming 100 is a reasonable max value, adjust accordingly
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(black);
        yAxis.setLabelCount(10);
        yAxis.setTextSize(14f); // Đặt kích thước chữ cho trục y
        yAxis.setGranularity(1f); // Đảm bảo mỗi nhãn trên trục Y là một giá trị duy nhất
        yAxis.setValueFormatter(new IntegerValueFormatter()); // Sử dụng IntegerValueFormatter để định dạng số nguyên
        // yAxis.setGridColor(lightGray); // Set grid color for Y-axis
        // yAxis.setTextSize(14f); // Set text size for Y-axis
        // yAxis.setTextColor(black); // Set text color for Y-axis
        // yAxis.setValueFormatter(new IntegerValueFormatter()); // Use IntegerValueFormatter for integer formatting

// Right Y-axis settings
        lineChart.getAxisRight().setEnabled(false); // Disable the right Y-axis

// Data preparation
        List<Entry> data = new ArrayList<>(); // Initialize data for Y-axis values
        for (int i = 0; i < 12; i++) {
            data.add(new Entry(i, 0));
        }

        LineDataSet dataSet1 = new LineDataSet(data, "Số hóa đơn năm " + String.valueOf(lineChartYear.getYear() + 1900));
        dataSet1.setColor(Color.BLUE);
        dataSet1.setValueTextSize(26f); // Đặt kích thước chữ cho thông tin
//         dataSet1.setColor(primary);
//         dataSet1.setValueTextSize(16f); // Set text size for data values
//         dataSet1.setValueTextColor(black); // Set text color for data values
//         dataSet1.setLineWidth(2f); // Set line width
//         dataSet1.setCircleColor(primary); // Set circle color
//         dataSet1.setCircleRadius(5f); // Set circle radius
//         dataSet1.setDrawCircleHole(false); // Disable hole in the circle

// // Highlight settings
//         dataSet1.setHighlightEnabled(true);
//         dataSet1.setHighLightColor(primary);
//         dataSet1.setDrawHighlightIndicators(true);
//         dataSet1.setDrawValues(false); // Disable values on data points

        LineData lineData = new LineData(dataSet1);

// General chart settings
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false); // Disable description label
        lineChart.setDrawGridBackground(false); // Disable grid background
        lineChart.setNoDataText("No data available"); // Text when no data is available
        lineChart.setTouchEnabled(true); // Enable touch gestures
        lineChart.setDragEnabled(true); // Enable dragging
        lineChart.setScaleEnabled(true); // Enable scaling
        lineChart.setPinchZoom(true); // Pinch to zoom
        lineChart.setBackgroundColor(white); // Set chart background color

// Legend settings
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(14f);
        legend.setTextColor(black);

// Animation
        lineChart.animateX(1000); // Animate chart horizontally for 1000 milliseconds

        lineChart.invalidate(); // Refresh the chart

    }

    private void updateDateLineChart(int[] data) {
        float max = Float.MIN_VALUE; // Khởi tạo giá trị max ban đầu

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) { // So sánh với giá trị max hiện tại
                max = data[i]; // Cập nhật giá trị max nếu cần
            }
            entries.add(new Entry(i, data[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Số hóa đơn năm " + String.valueOf(lineChartYear.getYear() + 1900));
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextSize(16f); // Đặt kích thước chữ cho các điểm dữ liệu
        dataSet.setValueFormatter(new IntegerValueFormatter()); // Sử dụng IntegerValueFormatter để định dạng số nguyên

        LineData lineData = new LineData(dataSet);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(max + 2f); // Giá trị cao nhất của cột Y là giá trị lớn nhất trong bộ dữ liệu lấy về + 2
        yAxis.setValueFormatter(new IntegerValueFormatter()); // Sử dụng IntegerValueFormatter để định dạng số nguyên

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day);
        selectedDate = cal.getTime(); // Ngày được chọn

        month = month + 1;

        return makeDateString(day, month, year);
    }

    private void initDatePicker(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            selectedDate = cal.getTime(); // Ngày được chọn

            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);

            progressBar.setVisibility(View.VISIBLE);
            viewModel.changeDate(selectedDate, lineChartYear);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(view.getContext(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return "Ngày " + day + " - " + month + " - " + year;
    }
}