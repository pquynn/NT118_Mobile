package com.example.foodorderingapp.data.repository.topping;

import com.example.foodorderingapp.data.model.entity.Topping;

public interface IToppingRepository {
    interface ToppingCallBack {
        void onToppingLoaded(Topping topping);
        void onToppingLoadFailed(String errorMessage);
    }
}
