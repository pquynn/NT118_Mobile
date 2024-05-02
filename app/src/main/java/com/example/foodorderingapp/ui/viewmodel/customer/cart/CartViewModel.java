package com.example.foodorderingapp.ui.viewmodel.customer.cart;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.BuyingProduct;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.ui.adapter.CartAdapter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartViewModel extends ViewModel {
    private String userId, orderIdVM;
    private MutableLiveData<Map<String, BuyingProduct>> buyingProducts = new MutableLiveData<>();
    private MutableLiveData<Double> totalPrice = new MutableLiveData<>();

    private OrderRepository orderRepository = new OrderRepository();

    public CartViewModel(String userId){
        this.userId = userId;
    }

    public MutableLiveData<Double> getTotalPrice() {
        loadTotalPrice();
        return totalPrice;
    }

    public MutableLiveData<Map<String, BuyingProduct>> getBuyingProducts() {
        loadCart(userId);
        return buyingProducts;
    }

    public void loadCart(String userId){
        orderRepository.getCartByUserId(userId, new IOrderRepository.CartCallback() {
            @Override
            public void onCartLoaded(String orderId, Map<String, BuyingProduct> buyingProductMap) {
                orderIdVM = orderId;
                buyingProducts.setValue(buyingProductMap);
                totalPrice.setValue(calculateTotalPrice(buyingProductMap));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public void loadTotalPrice(){
        totalPrice.setValue(calculateTotalPrice(buyingProducts.getValue()));
    }

    // calculate total price
    public double calculateTotalPrice(Map<String, BuyingProduct> buyingProductHashMap){
        double totalPrice = 0.0;
        for(Map.Entry<String, BuyingProduct> entry : buyingProductHashMap.entrySet()){
            totalPrice += entry.getValue().getProductPrice();
        }
        return totalPrice;
    }

    // Define click listeners for increase and decrease buttons
    public void onChangeQuantityButtonClick(String orderItemId, int quantity) {
//        BuyingProduct buyingProduct = buyingProducts.getValue().get(orderItemId);
//        buyingProduct.setQuantity(quantity);
        buyingProducts.getValue().get(orderItemId).setQuantity(quantity);
    }


}
