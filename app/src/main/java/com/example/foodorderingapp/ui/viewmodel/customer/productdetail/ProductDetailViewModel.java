package com.example.foodorderingapp.ui.viewmodel.customer.productdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.data.repository.topping.IToppingRepository;
import com.example.foodorderingapp.data.repository.topping.ToppingRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailViewModel extends ViewModel {
    private ProductRepository productRepository;
    private ToppingRepository toppingRepository;
    private MutableLiveData<Product> productDetail;
    private List<Topping> toppings = new ArrayList<>();
    public ProductDetailViewModel() {
        productRepository = new ProductRepository();
        toppingRepository = new ToppingRepository();
        productDetail = new MutableLiveData<>();
    }

    public LiveData<Product> getProductDetail(String productID) {
        loadProductDetails(productID);
        return productDetail;
    }
    public List<Topping> getToppings() {
        return toppings;
    }

    private void loadProductDetails(String productID) {
        productRepository.getProductDetails(productID, new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                productDetail.setValue(product);
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {
                // Xử lý khi tải dữ liệu thất bại
            }
        });
    }
    //load danh sách topping
    public void loadToppingList(){
        toppingRepository.getAllToppings(new IToppingRepository.ToppingListCallBack() {
            @Override
            public void onToppingListLoaded(List<Topping> toppingList) {
                toppings.addAll(toppingList);
            }

            @Override
            public void onFailed(String errorMessage) {

            }
        });
    }

}
