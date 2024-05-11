package com.example.foodorderingapp.ui.activityfragment.admin.adminfragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.AdminHomeVM;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

    private static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    private void initLineChart() {
        xValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"); // Danh sách tháng

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f); // Đặt kích thước chữ cho trục x
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(xValues.size());

        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
        yAxis.setTextSize(14f); // Đặt kích thước chữ cho trục y

        List<Entry> data = new ArrayList<>(); // Dữ liệu khởi tạo cho giá trị cột Y
        for (int i = 0; i < 12; i++)
            data.add(new Entry(i, 0));

        LineDataSet dataSet1 = new LineDataSet(data, "Số hóa đơn năm " + lineChartYear.getYear());
        dataSet1.setColor(Color.BLUE);
        dataSet1.setValueTextSize(16f); // Đặt kích thước chữ cho thông tin

        LineData lineData = new LineData(dataSet1);

        lineChart.setData(lineData);

        lineChart.invalidate();
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

        LineDataSet dataSet = new LineDataSet(entries, "Số hóa đơn năm " + lineChartYear.getYear());
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextSize(16f); // Đặt kích thước chữ cho các điểm dữ liệu

        LineData lineData = new LineData(dataSet);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(max + 2f); // Giá trị cao nhất của cột Y là giá trị lớn nhất trong bộ dữ liệu lấy về + 2

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
