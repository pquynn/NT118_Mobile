package com.example.foodorderingapp.ui.viewmodel.customer.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.ArrayList;
import java.util.Map;

public class CheckoutAddressViewModel extends ViewModel {
    private String userId;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context;

    //LIVE DATA
    private MutableLiveData<ArrayList<UserAddress>> userAddressesLiveData= new MutableLiveData<>();

    // REPOSITORY
    private UserInfoRepository userInfoRepository = new UserInfoRepository();

    // CONSTRUCTOR
    public CheckoutAddressViewModel(String userId, Context context){
        this.userId = userId;
        this.context = context;
        userInfoRepository = new UserInfoRepository();
    }

    // start: GETTER
    public MutableLiveData<ArrayList<UserAddress>> getUserAddressesLiveData() {
        loadUserAddressList(userId);
        return userAddressesLiveData;
    }

    // end: GETTER

    // start: ADDRESS LIST-----------
    // load user address list from firestore
    public void loadUserAddressList(String userId){
        userInfoRepository.getListAddress(userId, new UserInfoRepository.userAddressesCallback() {
            @Override
            public void loadUserAddressesSuccess(ArrayList<UserAddress> listAddress) {
                userAddressesLiveData.setValue(listAddress);
                Log.d("firestore", "loadUserAddressesSuccess: " + listAddress.get(1).toString());
            }

            @Override
            public void loadUsserAddressesError(Exception e) {

            }
        });
    }
    // end: ADDRESS LIST-----------


    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
