package com.example.foodorderingapp.ui.viewmodel.customer.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

public class CartViewModel extends ViewModel {
    private String userId;
    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Double> totalPrice = new MutableLiveData<>();

    private OrderRepository orderRepository = new OrderRepository();

    public CartViewModel(String userId){
        this.userId = userId;
    }

    public MutableLiveData<Double> getTotalPrice() {
        loadTotalPrice();
        return totalPrice;
    }

    public MutableLiveData<Order> getOrderMutableLiveData() {
        loadCart(userId);
        return orderMutableLiveData;
    }



    public void loadCart(String userId){
        orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    public void loadTotalPrice(){
        totalPrice.setValue(0.0);
//        totalPrice.setValue(calculateTotalPrice(buyingProducts.getValue()));
    }

    // calculate total price
//    public double calculateTotalPrice(Map<String, BuyingProduct> buyingProductHashMap){
//        double totalPrice = 0.0;
//        for(Map.Entry<String, BuyingProduct> entry : buyingProductHashMap.entrySet()){
//            totalPrice += entry.getValue().getProductPrice();
//        }
//        return totalPrice;
//    }

    // Define click listeners for increase and decrease buttons
    public void onChangeQuantityButtonClick(String orderItemId, int quantity) {
//        BuyingProduct buyingProduct = buyingProducts.getValue().get(orderItemId);
//        buyingProduct.setQuantity(quantity);
//        buyingProducts.getValue().get(orderItemId).setQuantity(quantity);
    }


}
