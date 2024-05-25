package com.example.foodorderingapp.ui.viewmodel.customer.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.category.CategoryRepository;
import com.example.foodorderingapp.data.repository.category.ICategoryRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
        loadCategories();
    }

    //khai báo cho danh mục
    public LiveData<List<Category>> getCategoryList(){
        return categoryListLiveData;
    }
    //khai báo cho sản phẩm từng danh mục
    public LiveData<List<Product>> getProductListLiveData(String categoryId) {
        loadProductList(categoryId);
        return productListLiveData;
    }

    //load sữ liệu danh sách danh mục
    private void loadCategories() {
        categoryRepository = new CategoryRepository();
        categoryRepository.getListCategory(new ICategoryRepository.CategoryListCallBack() {
            @Override
            public void onCategoryListLoaded(List<Category> categoryList) {
                categoryListLiveData.setValue(categoryList);
            }

            @Override
            public void onCategoryListLoadFailed(Exception exception) {

            }
        });
    }

    //load danh sách sản phẩm theo từng danh mục
    private void loadProductList(String categoryId) {
        // Sử dụng repository để lấy danh sách sản phẩm theo categoryId
        productRepository = new ProductRepository();
        productRepository.getProductsByCategory(categoryId, new ProductRepository.ProductListCallback() {
            @Override
            public void onProductListLoaded(List<Product> productList) {
                productListLiveData.setValue(productList);
            }

            @Override
            public void onProductListLoadFailed(String errorMessage) {
            }
        });
    }
}
