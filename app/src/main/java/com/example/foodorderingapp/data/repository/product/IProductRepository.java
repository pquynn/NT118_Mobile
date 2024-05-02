
package com.example.foodorderingapp.data.repository.product;

import com.example.foodorderingapp.data.model.entity.Product;

import java.util.List;
import java.util.Map;

public interface IProductRepository {

    public void getProductById(String productId, ProductCallback callback);

    interface ProductCallback {
        void onProductLoaded(Product product);
        void onProductLoadFailed(String errorMessage);
    }

    interface  ProductListCallback{
        void onProductListLoaded(List<Product> productList);
        void onProductListLoadFailed(String errorMessage);
    }

    interface ProductListListener{
        void onProductList(List<Product> products);
        void onError(String errorMessage);
    }
    interface ProductDetailCallback {
        void onProductDetailLoaded(String productName, String productImage, String productInfo, int productPrice, Map<String, Map<String, Object>> sizeMap, List<String> toppingList);
        void onProductDetailLoadFailed(String errorMessage);
    }

    interface ProductDetailCakeCallback {
        void onProductDetailLoaded(String productName, String productImage, int productPrice, String productInfo);
        void onProductDetailLoadFailed(String errorMessage);
    }
}
