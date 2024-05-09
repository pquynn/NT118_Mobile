package com.example.foodorderingapp.data.repository.category;

import android.util.Log;

import com.example.foodorderingapp.data.model.entity.Category;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository {
    private FirebaseFirestore db;
    public CategoryRepository() {
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public void getCategoryById(String categoryId, CategoryCallBack callBack) {
        DocumentReference categoryRef = db.collection("CATEGORY").document(categoryId);
        categoryRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Category category = documentSnapshot.toObject(Category.class);
                        callBack.onCategoryLoaded(category);
                    } else {
                        callBack.onCategoryLoadFailed("Category not found");
                    }
                })
                .addOnFailureListener(e -> callBack.onCategoryLoadFailed(e.getMessage()));
    }

    //lấy ảnh và tên của category
    public void getListCategory(CategoryListCallBack callBack) {
        db.collection("CATEGORY")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Category> categoryList = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String categoryImage = document.getString("CATEGORY_IMAGE");
                            String categoryName = document.getString("CATEGORY_NAME");

                            Category category = new Category(categoryImage, categoryName);
                            categoryList.add(category);
                        }
                        callBack.onCategoryListLoaded(categoryList);
                    } else {
                        Log.e("TAG", "Error getting documents: ", task.getException());
                        callBack.onCategoryListLoadFailed(task.getException());
                    }
                });
    }
}

