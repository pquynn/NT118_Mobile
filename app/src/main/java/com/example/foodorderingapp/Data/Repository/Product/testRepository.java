package com.example.foodorderingapp.Data.Repository.Product;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.Data.Model.Entity.Product;
import com.example.foodorderingapp.Data.Repository.Category.CategoryRepository;
import com.example.foodorderingapp.R;

public class testRepository extends AppCompatActivity {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_cake);

        productRepository = new ProductRepository();

//        productRepository.getProductDetails("1", new IProductRepository.ProductCallback() {
//            @Override
//            public void onProductLoaded(Product product) {
//                Product p = new Product();
//            }
//
//            @Override
//            public void onProductLoadFailed(String errorMessage) {
//
//            }
//        });

        productRepository.getProductById("2", new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                Product p = product;
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {

            }
        });

    }
}
