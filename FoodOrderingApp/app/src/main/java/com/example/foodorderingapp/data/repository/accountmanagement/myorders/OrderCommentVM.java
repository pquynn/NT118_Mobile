package com.example.foodorderingapp.data.repository.accountmanagement.myorders;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;

public class OrderCommentVM {
    private Context context;
    public class ProductNameImage {
        String name;
        String image;
        public ProductNameImage(String name, String image){
            this.name = name;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
    private String userID, productID;
    private OrdersFeedbackRepository ordersFeedbackRepository = new OrdersFeedbackRepository();
    private ProductRepository productRepository = new ProductRepository();

    private MutableLiveData<Comment> commentMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductNameImage> productNameImageMutableLiveData = new MutableLiveData<>();

    public OrderCommentVM(String userID, String productID, Context context){
        this.context = context;
        this.userID = userID;
        this.productID = productID;
    }

    public MutableLiveData<Comment> getCommentMutableLiveData(){
        loadComment(userID, productID);
        return commentMutableLiveData;
    }

    public MutableLiveData<ProductNameImage> getProductNameImageMutableLiveData(){
        loadProductNameImage(productID);
        return productNameImageMutableLiveData;
    }

    private void loadProductNameImage(String productID){
        productRepository.getProductById(productID, new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                ProductNameImage p = new ProductNameImage(product.getProductName(), product.getProductImage());
                productNameImageMutableLiveData.setValue(p);
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {

            }
        });
    }
    private void loadComment(String userID, String productID){
        ordersFeedbackRepository.getCommentByProductIDUserID(productID, userID, new OrdersFeedbackRepository.commentCallback() {
            @Override
            public void loadCommentSuccess(Comment comment) {
                commentMutableLiveData.setValue(comment);
            }

            @Override
            public void loadCommentError(Exception e) {

            }
        });
    }
}
