package com.example.foodorderingapp.Data.Repository.Category;

import com.example.foodorderingapp.Data.Model.Entity.Category;
import java.util.List;

public interface ICategoryRepository {
    public void getCategoryById(String categoryId, CategoryCallBack callBack);

    interface CategoryCallBack{
        void onCategoryLoaded(Category category);
        void onCategoryLoadFailed(String errorMessage);
    }

    interface CategoryListCallBack {
        void onCategoryListLoaded(List<Category> categoryList);
        void onCategoryListLoadFailed(Exception exception);
    }
}
