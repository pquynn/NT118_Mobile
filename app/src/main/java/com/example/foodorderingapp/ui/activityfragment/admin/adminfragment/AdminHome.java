package com.example.foodorderingapp.ui.activityfragment.admin.adminfragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminHome extends Fragment {

    private TextView txtRevenue, txtTotalOrder, txtRefund;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private AdminHomeVM viewModel;
    private Date selectedDate;
    private ProgressBar progressBar;

    public static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        initDatePicker(view);
        txtRevenue = view.findViewById(R.id.txtRevenue);
        txtRefund = view.findViewById(R.id.txtRefund);
        txtTotalOrder = view.findViewById(R.id.txtTotalOrder);
        dateButton = view.findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        progressBar = view.findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(AdminHomeVM.class)) {
                    return (T) new AdminHomeVM(selectedDate);
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(AdminHomeVM.class);

        progressBar.setVisibility(View.VISIBLE);

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
                progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getTotalRevenue().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double revenue) {
                txtRevenue.setText(formatNumber(revenue)+ " Đ");
                progressBar.setVisibility(View.GONE);
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
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
            viewModel.changeDate(selectedDate);
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
