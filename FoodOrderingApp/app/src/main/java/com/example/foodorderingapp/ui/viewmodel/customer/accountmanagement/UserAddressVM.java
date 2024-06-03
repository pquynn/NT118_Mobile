package com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;

public class UserAddressVM extends ViewModel {
    private Context context;
    private String addressId;

    UserInfoRepository addressRepository;

    MutableLiveData<UserAddress> userAddressMutableLiveData;

    public UserAddressVM(String id, Context context){
        this.context = context;
        this.addressId = id;
        addressRepository = new UserInfoRepository();
        userAddressMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<UserAddress> getUserAddressMutableLiveData(){
        loadAddressById(addressId);
        return userAddressMutableLiveData;
    }
    private void loadAddressById(String id){
        addressRepository.getAddressById(id, new UserInfoRepository.userAddressCallback() {
            @Override
            public void loadUserAddressSuccess(UserAddress userAddress) {
                userAddressMutableLiveData.setValue(userAddress);
            }

            @Override
            public void loadUsserAddressError(Exception e) {

            }
        });
    }
}
