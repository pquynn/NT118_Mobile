package com.example.foodorderingapp.ui.viewmodel.customer.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.PointRepository;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.data.repository.coupon.CouponRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutViewModel extends ViewModel {
    private String userId;
    private String img = "@drawable/cash";
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context;
    
    //LIVE DATA
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> totalPrice = new MutableLiveData<>();
    private MutableLiveData<UserAddress> userAddressLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> pointTotalLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> totalProductLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> pointUsedLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> orderPriceLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> discountValueLiveData = new MutableLiveData<>();
    private MutableLiveData<String> paymentMethodLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> iconPaymentLiveData = new MutableLiveData<>();

    // REPOSITORY
    private OrderRepository orderRepository;
    private UserInfoRepository userInfoRepository;
    private PointRepository pointRepository;
    private CouponRepository couponRepository;


    // CONSTRUCTOR
    public CheckoutViewModel(String userId, Context context){
        this.userId = userId;
        this.context = context;
        orderRepository = new OrderRepository();
        userInfoRepository = new UserInfoRepository();
        pointRepository = new PointRepository();
        couponRepository = new CouponRepository();

        loadUserAddress(userId);
        pointUsedLiveData.setValue(0);
        discountValueLiveData.setValue(0);
        paymentMethodLiveData.setValue("Thanh toán khi nhận hàng");
        iconPaymentLiveData.setValue(R.drawable.cash);
        loadOrder(userId);
    }

    // start: GETTER
    public MutableLiveData<Order> getOrderLiveData() {
        loadOrder(userId);
        return orderLiveData;
    }

    public MutableLiveData<Integer> getTotalPrice() {
        totalPrice.setValue(calculateTotalPrice(orderLiveData.getValue().getOrderItem()));
        return totalPrice;
    }

    public MutableLiveData<UserAddress> getUserAddressLiveData() {
        loadUserAddress(userId);
        return userAddressLiveData;
    }

    public MutableLiveData<Integer> getPointTotalLiveData() {
        loadTotalPoint(userId);
        return pointTotalLiveData;
    }

    public MutableLiveData<Integer> getTotalProductLiveData() {
        return totalProductLiveData;
    }

    public MutableLiveData<Integer> getPointUsedLiveData() {
        return pointUsedLiveData;
    }

    public MutableLiveData<Integer> getOrderPriceLiveData() {
        orderPriceLiveData.setValue(calculateOrderPrice());
        return orderPriceLiveData;
    }

    public MutableLiveData<Integer> getDiscountValueLiveData() {
        return discountValueLiveData;
    }

    public MutableLiveData<String> getPaymentMethodLiveData() {
        return paymentMethodLiveData;
    }

    public MutableLiveData<Integer> getIconPaymentLiveData() {
        return iconPaymentLiveData;
    }

    // end: GETTER

    // start: ORDER-----------
    // load order from firestore
    public void loadOrder(String userId){

        orderRepository.getCartByUserId(userId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
                totalPrice.setValue(calculateTotalPrice(order.getOrderItem()));
                totalProductLiveData.setValue(calculateTotalProduct(order.getOrderItem()));
                orderPriceLiveData.setValue(calculateOrderPrice());
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    //todo: xử lý cộng điểm khi mua hàng (hay khi giao thành công?), trừ điểm
    //todo: xử lý cập nhật số lượng mã giảm giá
    //todo: xử lý cập nhật số lượng sản phẩm

    // end: ORDER-----------


    // load user address
    public void loadUserAddress(String userId){
        userInfoRepository.getFirstAddress(userId, new UserInfoRepository.userAddressCallback() {
            @Override
            public void loadUserAddressSuccess(UserAddress userAddress) {
                userAddressLiveData.setValue(userAddress);
            }

            @Override
            public void loadUsserAddressError(Exception e) {

            }
        });
    }

    // load total point
    public void loadTotalPoint(String userId){
        pointRepository.getTotalPoint(userId, new PointRepository.userTotalPointCallback() {
            @Override
            public void loadTotalPointSuccess(int totalPoint) {
                pointTotalLiveData.setValue(totalPoint);
            }

            @Override
            public void loadTotalPointError(Exception e) {
                Log.d("VMPointError: ", "Error load point total in point VM");
            }
        });
    }

    // load point used
    public void loadPointUsed(int totalPoint, boolean isSelected){
        if(isSelected)
            pointUsedLiveData.setValue(-1 * totalPoint);
        else
            pointUsedLiveData.setValue(0);
    }

    // load payment method
    public void loadPaymentMethod(String method){
        paymentMethodLiveData.setValue(method);
        if(method == "Thanh toán khi nhận hàng")
            iconPaymentLiveData.setValue(R.drawable.cash);
        else iconPaymentLiveData.setValue(R.drawable.paypal);
    }

    // calculate total price
    public int calculateTotalPrice(@NonNull Map<String, OrderItem> buyingProductHashMap){
        int totalPrice = 0;
        for(Map.Entry<String, OrderItem> entry : buyingProductHashMap.entrySet()){
            totalPrice += entry.getValue().getPrice() * entry.getValue().getQuantity();
        }
        return totalPrice;
    }

    // calculate total product
    public int calculateTotalProduct(@NonNull Map<String, OrderItem> buyingProductHashMap){
        int totalProduct = 0;
        for(Map.Entry<String, OrderItem> entry : buyingProductHashMap.entrySet()){
            totalProduct += entry.getValue().getQuantity();
        }
        return totalProduct;
    }


    // calculate discount value
    // todo: tính tiền giảm từ tiền đã cộng điểm thưởng hay sao?
    public int calculateDiscountValue(){
        int discountValue = 0;

        return discountValue;
    }

    // calculate order price
    public int calculateOrderPrice(){
        int orderPrice = 0;

        orderPrice = totalPrice.getValue()
                + pointUsedLiveData.getValue()
                + discountValueLiveData.getValue();
        //todo: if have delivery cost --> add to orderPrice
        return orderPrice;
    }


    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}
