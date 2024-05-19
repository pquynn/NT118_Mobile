package com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.UserPoint;
import com.example.foodorderingapp.data.repository.accountmanagement.PointRepository;

import java.util.ArrayList;

public class PointVM extends ViewModel {
    PointRepository repository = new PointRepository();

    MutableLiveData<ArrayList<UserPoint>> pointListLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> pointTotalLiveData = new MutableLiveData<>();
    private Context context;

    public PointVM(String userId, Context context){
        this.context = context;
        repository.getListPoint(userId, new PointRepository.userPointCallback() {
            @Override
            public void loadUserPointSuccess(ArrayList<UserPoint> userPoints) {
                pointListLiveData.setValue(userPoints);
            }

            @Override
            public void loadUserPointError(Exception e) {
                Log.d("VMPointError: ", "Error load point list in point VM");
            }
        });
        repository.getTotalPoint(userId, new PointRepository.userTotalPointCallback() {
            @Override
            public void loadTotalPointSuccess(int totalPoint) {
                pointTotalLiveData.setValue(totalPoint);
            }

            @Override
            public void loadTotalPointError(Exception e) {
                Log.d("VMPointError: ", "Error load point total in point VM");
            }
        });

    }

    public MutableLiveData<ArrayList<UserPoint>> getPointList (){
        return pointListLiveData;
    }
    public MutableLiveData<Integer> getTotalPoint(){
        return pointTotalLiveData;
    }
}
