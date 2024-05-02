package com.example.foodorderingapp.UI.ViewModel.Customer.AccountManagement;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.Data.Model.Entity.User;
import com.example.foodorderingapp.Data.Model.Entity.UserAddress;
import com.example.foodorderingapp.Data.Repository.AccountManagement.UserInfoRepository;

import java.util.ArrayList;

public class UserInfoVM extends ViewModel {
    UserInfoRepository repository = new UserInfoRepository();
    MutableLiveData<User> userInfoLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<UserAddress>>  userAddressesLiveData= new MutableLiveData<>();

    public UserInfoVM(String userId){
        repository.getUserInfo(userId, new UserInfoRepository.userInfoCallback() {
            @Override
            public void loadUserInfoSuccess(User userInfo) {
                userInfoLiveData.setValue(userInfo);
            }

            @Override
            public void loadUserInfoError(Exception e) {
                Log.d("VMUserInfoError", "Error load userinfo in UserInfoVM");
            }
        });

        repository.getListAddress(userId, new UserInfoRepository.userAddressesCallback() {
            @Override
            public void loadUserAddressesSuccess(ArrayList<UserAddress> listAddress) {
                userAddressesLiveData.setValue(listAddress);
            }

            @Override
            public void loadUsserAddressesError(Exception e) {
                Log.d("VMUserInfoError","Error load list address in UserInfoVM");
            }
        });
    }

    public MutableLiveData<User> getUserInfoLiveData (){
        return userInfoLiveData;
    }

    public MutableLiveData<ArrayList<UserAddress>> getUserAddressesLiveData() {
        return userAddressesLiveData;
    }
}
