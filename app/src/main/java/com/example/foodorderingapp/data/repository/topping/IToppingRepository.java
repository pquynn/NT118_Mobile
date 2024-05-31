package com.example.foodorderingapp.data.repository.topping;

import com.example.foodorderingapp.data.model.entity.Topping;

import java.util.List;

public interface IToppingRepository {
    interface ToppingCallBack {
        void onToppingLoaded(Topping topping);
        void onToppingLoadFailed(String errorMessage);
    }
    interface ToppingListCallBack {
        void onToppingListLoaded(List<Topping> toppingList);
        void onFailed(String errorMessage);
    }
}
