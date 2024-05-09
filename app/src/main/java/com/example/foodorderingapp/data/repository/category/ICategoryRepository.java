package com.example.foodorderingapp.data.repository.category;

import com.example.foodorderingapp.data.model.entity.Category;
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
