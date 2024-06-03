package com.example.foodorderingapp.ui.viewmodel.customer.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> productLiveData = new MutableLiveData<>();
    public SearchViewModel(){
        productRepository = new ProductRepository();
        loadListProduct();
    }
    public LiveData<List<Product>> getProductListLiveData() {
        return productLiveData;
    }
    private void loadListProduct() {
        productRepository.getAllProducts(new IProductRepository.ProductListCallback() {
            @Override
            public void onProductListLoaded(List<Product> productList) {
                productLiveData.setValue(productList);
            }

            @Override
            public void onProductListLoadFailed(String errorMessage) {
            }
        });
    }
}
