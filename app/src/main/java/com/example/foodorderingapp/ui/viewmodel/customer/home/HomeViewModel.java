package com.example.foodorderingapp.ui.viewmodel.customer.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.category.CategoryRepository;
import com.example.foodorderingapp.data.repository.category.ICategoryRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeViewModel extends ViewModel {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<List<Product>> bestSellingProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();
    private CategoryRepository categoryRepository;

    public HomeViewModel() {
        categoryRepository = new CategoryRepository();
        loadCategories();
    }

    public LiveData<List<Category>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    private void loadCategories() {
        categoryRepository.getListCategory(new ICategoryRepository.CategoryListCallBack() {
            @Override
            public void onCategoryListLoaded(List<Category> categoryList) {
                categoryListLiveData.setValue(categoryList);
            }

            @Override
            public void onCategoryListLoadFailed(Exception e) {
                Log.e("CategoryViewModel", "Error loading categories", e);
            }
        });
    }
    //Khai báo danh sách sản phẩm bán chạy.
    public LiveData<List<Product>> getBestSellingProducts() {
            fetchBestSellingProducts();
            return bestSellingProductsLiveData;
    }
    //Hàm tính sản phẩm bán chạy
    public void fetchBestSellingProducts() {
        db.collection("ORDER")
                .get()
                .addOnSuccessListener(result -> {
                    Map<String, Integer> productCountMap = new HashMap<>();

                    for (QueryDocumentSnapshot document : result) {
                        Order order = document.toObject(Order.class);

                        for (OrderItem item : order.getOrderItem().values()) {
                            String productId = item.getIdProduct();
                            if (productId != null && !productId.isEmpty()) {
                                int count = productCountMap.getOrDefault(productId, 0) + item.getQuantity();
                                productCountMap.put(productId, count);
                                Log.d("firebaseVM", "Product ID: " + productId + ", Quantity: " + item.getQuantity() + ", Total Count: " + count);
                            }
                        }
                    }

                    List<String> sortedProductIds = productCountMap.entrySet().stream()
                            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                            .map(Map.Entry::getKey)
                            .filter(productId -> productId != null && !productId.isEmpty()) // Ensure no empty IDs
                            .limit(5)
                            .collect(Collectors.toList());
                    Log.d("firebaseVM", "Sorted Product IDs: " + sortedProductIds);

                    if (!sortedProductIds.isEmpty()) {
                        db.collection("PRODUCT")
                                .whereIn(FieldPath.documentId(), sortedProductIds)
                                .get()
                                .addOnSuccessListener(productsResult -> {
                                    List<Product> products = new ArrayList<>();
                                    for (DocumentSnapshot document : productsResult.getDocuments()) {
                                        String id = document.getId();
                                        String idCategory = document.getString("ID_CATEGORY");
                                        String productImage = document.getString("PRODUCT_IMAGE");
                                        String productName = document.getString("PRODUCT_NAME");
                                        int productPrice = document.getLong("PRODUCT_PRICE").intValue();

                                        Product product = new Product(id, idCategory, productImage, productName, productPrice);
                                        products.add(product);
                                        Log.d("firebase", "best selling products" + product.toString());
                                    }
                                    bestSellingProductsLiveData.setValue(products);
                                    Log.d("firebaseVM", "Best Selling Products: " + products);
                                })
                                .addOnFailureListener(exception -> {
                                    Log.e("firebaseVM", "Error getting products: ", exception);
                                });
                    } else {
                        Log.d("firebaseVM", "No products found in orders.");
                    }
                })
                .addOnFailureListener(exception -> {
                    Log.d("firebase", "fail");
                });
    }

}

