package com.example.foodorderingapp.Data.Repository.Topping;

import com.example.foodorderingapp.Data.Model.Entity.Topping;

public interface IToppingRepository {
    interface ToppingCallBack {
        void onToppingLoaded(Topping topping);
        void onToppingLoadFailed(String errorMessage);
    }
}
