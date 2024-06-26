package com.example.javajoyadmin.ui.viewmodel.admin.product;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javajoyadmin.data.model.entity.Product;
import com.example.javajoyadmin.data.repository.admin.ProductRepository;

import java.util.List;
import java.util.Objects;

public class AdminProductVM extends ViewModel {
    private ProductRepository  productRepository = new ProductRepository();
    private MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();

    public AdminProductVM() {
        GetProductList();
    }

    public void GetProductList(){
        productRepository.GetListProduct(new ProductRepository.GetListProductCallBack(){
            @Override
            public void loadDataSuccess(List<Product> productList) {
                productListLiveData.setValue(productList);
            }

            @Override
            public void loadDataFail(Exception e) {
                productListLiveData.setValue(null);
                Log.e("AdminProductVM", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public void UpdateQuantity(Context context){
        productRepository.UpdateQuantity(new ProductRepository.UpdateQuantityCallBack() {
            @Override
            public void updateSuccess() {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateFail(Exception e) {
                Log.e("UpdateQuantity", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(context, "Quá trình cập nhật đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<List<Product>> GetProductListLiveData(){
        GetProductList();
        return this.productListLiveData;
    }
}
