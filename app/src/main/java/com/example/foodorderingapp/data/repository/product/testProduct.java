package com.example.foodorderingapp.data.repository.product;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.CategoryList;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.category.CategoryRepository;
import com.example.foodorderingapp.data.repository.topping.ToppingRepository;

import java.util.List;

public class testProduct extends AppCompatActivity {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ToppingRepository toppingRepository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_drink);

        productRepository = new ProductRepository();
        productRepository.getProductDetails("1", new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                Product t = product;
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {

            }
        });

//        productRepository.getProductDetailsCake("19", new IProductRepository.ProductDetailCakeCallback() {
//            @Override
//            public void onProductDetailLoaded(String id, String productName, String productImage, int productPrice, String productInfo) {
//                Product p = new Product(id, productName, productImage, productPrice, productInfo);
//            }
//
//            @Override
//            public void onProductDetailLoadFailed(String errorMessage) {
//
//            }
//        });

//        productRepository.getProductById("2", new IProductRepository.ProductCallback() {
//            @Override
//            public void onProductLoaded(Product product) {
//                Product p = product;
//            }
//
//            @Override
//            public void onProductLoadFailed(String errorMessage) {
//
//            }
//        });

//        toppingRepository = new ToppingRepository();
//
//        toppingRepository.getToppingDetails("Trân châu trắng", new IToppingRepository.ToppingCallBack() {
//            @Override
//            public void onToppingLoaded(Topping topping) {
//                Topping t = topping;
//            }
//
//            @Override
//            public void onToppingLoadFailed(String errorMessage) {
//
//            }
//        });
    }

}