package com.example.foodorderingapp.ui.viewmodel.customer.productdetail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.data.repository.topping.IToppingRepository;
import com.example.foodorderingapp.data.repository.topping.ToppingRepository;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailViewModel extends ViewModel {
    private ProductRepository productRepository;
    private ToppingRepository toppingRepository;
    private OrdersFeedbackRepository ordersFeedbackRepository;
    private MutableLiveData<Product> productDetail;
    private MutableLiveData<List<Comment>> listCommentLiveData;
    private List<Topping> toppings = new ArrayList<>();
    public ProductDetailViewModel() {
        productRepository = new ProductRepository();
        toppingRepository = new ToppingRepository();
        ordersFeedbackRepository = new OrdersFeedbackRepository();
        productDetail = new MutableLiveData<>();
        listCommentLiveData = new MutableLiveData<>();

    }

    public LiveData<Product> getProductDetail(String productID) {
        loadProductDetails(productID);
        return productDetail;
    }
    public List<Topping> getToppings() {
        return toppings;
    }

    public MutableLiveData<List<Comment>> getListCommentLiveData(String productID){
        loadListComment(productID);
        return listCommentLiveData;
    }

    private  void loadListComment(String productID){
        ordersFeedbackRepository.getCommentsByProductID(productID, new OrdersFeedbackRepository.commentListCallback() {
            @Override
            public void loadListCommentSuccess(List<Comment> listComment) {
                listCommentLiveData.setValue(listComment);
            }

            @Override
            public void loadlistCommentError(Exception e) {
                Log.e("Error Product detail VM comment: ", "Product ID: "+ productID);
                Log.e("Error Product detail VM comment:", "Error load comment list in Product detail VM");
            }
        });
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
