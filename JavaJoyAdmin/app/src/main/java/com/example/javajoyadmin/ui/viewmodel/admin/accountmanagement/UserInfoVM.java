package com.example.javajoyadmin.ui.viewmodel.admin.accountmanagement;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javajoyadmin.data.model.entity.User;
import com.example.javajoyadmin.data.repository.accountmanagement.UserInfoRepository;
import com.example.javajoyadmin.data.repository.authentication.AuthRepository;

public class UserInfoVM extends ViewModel {
    UserInfoRepository repository = new UserInfoRepository();
    MutableLiveData<User> userInfoLiveData = new MutableLiveData<>();
    private Context context;
    private String userId;

    public UserInfoVM(String userId, Context context){
        this.context = context;
        this.userId = userId;
    }

    public MutableLiveData<User> getUserInfoLiveData (){
        loadUserInfo(userId);
        return userInfoLiveData;
    }
    private void loadUserInfo(String userId){
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
    }
}
