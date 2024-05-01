package com.example.foodorderingapp.UI;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.nfc.Tag;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.Data.Repository.AdminRepository.AdminHomeRepository;

import java.util.Date;
import java.util.Objects;

public class AdminHomeVM extends ViewModel {

    private MutableLiveData<Integer> totalOrder = new MutableLiveData<>();
    private MutableLiveData<Double> totalRevenue = new MutableLiveData<>();
    private MutableLiveData<Double> totalRefund = new MutableLiveData<>();
    private AdminHomeRepository adminHomeRepository = new AdminHomeRepository();

    public AdminHomeVM(Date date){
        adminHomeRepository.getData(date, new AdminHomeRepository.adminHomeCallback() {
            @Override
            public void loadDataSuccess(int order, double revenue, double refund) {
                totalOrder.setValue(order);
                totalRevenue.setValue(revenue);
                totalRefund.setValue(refund);
            }

            @Override
            public void loadDataFail(Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public void changeDate(Date date){
        adminHomeRepository.getData(date, new AdminHomeRepository.adminHomeCallback() {
            @Override
            public void loadDataSuccess(int order, double revenue, double refund) {
                totalOrder.setValue(order);
                totalRevenue.setValue(revenue);
                totalRefund.setValue(refund);
            }

            @Override
            public void loadDataFail(Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public MutableLiveData<Integer> getTotalOrder() {
        return totalOrder;
    }

    public MutableLiveData<Double> getTotalRevenue() {
        return totalRevenue;
    }

    public MutableLiveData<Double> getTotalRefund() {
        return totalRefund;
    }
}
