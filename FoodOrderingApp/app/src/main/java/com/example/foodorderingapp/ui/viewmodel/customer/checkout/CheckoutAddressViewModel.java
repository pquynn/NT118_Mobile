package com.example.foodorderingapp.ui.viewmodel.customer.checkout;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;

import java.util.ArrayList;

public class CheckoutAddressViewModel extends ViewModel {
    private String userId;
    private Context context;

    // LIVE DATA
    private MutableLiveData<ArrayList<UserAddress>> userAddressesLiveData = new MutableLiveData<>();
    private MutableLiveData<UserAddress> selectedAddressLiveData = new MutableLiveData<>();

    // REPOSITORY
    private UserInfoRepository userInfoRepository;

    // CONSTRUCTOR
    public CheckoutAddressViewModel(String userId, Context context) {
        this.userId = userId;
        this.context = context;
        userInfoRepository = new UserInfoRepository();
        loadUserAddressList(userId);
    }

    // GETTER
    public MutableLiveData<ArrayList<UserAddress>> getUserAddressesLiveData() {
        loadUserAddressList(userId);
        return userAddressesLiveData;
    }

    public MutableLiveData<UserAddress> getSelectedAddressLiveData(String id) {
        loadSelectedAddress(id);
        return selectedAddressLiveData;
    }

    public MutableLiveData<UserAddress> getSelectedAddressLiveData() {
        return selectedAddressLiveData;
    }

    // load selected address by id
    public void loadSelectedAddress(String id) {
        if(id != null && !id.isEmpty()){
            userInfoRepository.getAddressById(id, new UserInfoRepository.userAddressCallback() {
                @Override
                public void loadUserAddressSuccess(UserAddress userAddress) {
                    selectedAddressLiveData.setValue(userAddress);
                }

                @Override
                public void loadUsserAddressError(Exception e) {
                    Log.e("firestore", "Error loading address: ", e);
                }
            });
        }
    }

    // load user address list from firestore
    public void loadUserAddressList(String userId){
        userInfoRepository.getListAddress(userId, new UserInfoRepository.userAddressesCallback() {
            @Override
            public void loadUserAddressesSuccess(ArrayList<UserAddress> listAddress) {
                userAddressesLiveData.setValue(listAddress);
            }

            @Override
            public void loadUsserAddressesError(Exception e) {

            }
        });
    }
}
