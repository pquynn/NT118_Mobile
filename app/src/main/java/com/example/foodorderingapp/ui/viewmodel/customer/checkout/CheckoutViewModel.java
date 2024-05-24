package com.example.foodorderingapp.ui.viewmodel.customer.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.model.entity.UserPoint;
import com.example.foodorderingapp.data.repository.accountmanagement.PointRepository;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.data.repository.coupon.CouponRepository;
import com.example.foodorderingapp.data.repository.notification.NotificationRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.checkout.BuySuccessActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.refund.SendRefundSucessActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class CheckoutViewModel extends ViewModel {
    private String userId, orderId;
    private String img = "@drawable/cash";
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context;
    private boolean isValid = true;
    
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
    private MutableLiveData<Coupon> couponLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();

    // REPOSITORY
    private OrderRepository orderRepository;
    private UserInfoRepository userInfoRepository;
    private PointRepository pointRepository;
    private CouponRepository couponRepository;
    private ProductRepository productRepository;
    private NotificationRepository notificationRepository;

    // CONSTRUCTOR
    public CheckoutViewModel(String userId, String orderId, Context context){
        this.userId = userId;
        this.orderId = orderId;
        this.context = context;
        orderRepository = new OrderRepository();
        userInfoRepository = new UserInfoRepository();
        pointRepository = new PointRepository();
        couponRepository = new CouponRepository();
        productRepository = new ProductRepository();
        notificationRepository = new NotificationRepository();

        loadDefaultUserAddress(userId);
        pointUsedLiveData.setValue(0);
        discountValueLiveData.setValue(0);
        paymentMethodLiveData.setValue("Thanh toán khi nhận hàng");
        iconPaymentLiveData.setValue(R.drawable.cash);
        loadOrder();
    }

    // start: GETTER
    public MutableLiveData<Order> getOrderLiveData() {
        loadOrder();
        return orderLiveData;
    }

    public MutableLiveData<Integer> getTotalPrice() {
        totalPrice.setValue(calculateTotalPrice(orderLiveData.getValue().getOrderItem()));
        return totalPrice;
    }

    public MutableLiveData<UserAddress> getUserAddressLiveData() {
        return userAddressLiveData;
    }

    public MutableLiveData<UserAddress> getUserAddressLiveData(String id) {
        loadUserAddress(id);
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

    public MutableLiveData<Coupon> getCouponLiveData(String couponId) {
        loadCoupon(couponId);
        return couponLiveData;
    }

    public MutableLiveData<List<Product>> getProductListLiveData() {
        loadProductList();
        return productListLiveData;
    }
// end: GETTER

    //method to load productlist
    public void loadProductList() {
        List<String> ids = new ArrayList<>();
        if(orderLiveData.getValue() != null){
            for (Map.Entry<String, OrderItem> entry : orderLiveData.getValue().getOrderItem().entrySet()) {
                ids.add(entry.getValue().getIdProduct());
            }
            productRepository.getProductListByIds(ids, new IProductRepository.ProductListCallback() {
                @Override
                public void onProductListLoaded(List<Product> productList) {
                    productListLiveData.setValue(productList);
                    Log.d("firestore", "onProductLoaded vm: " + productListLiveData.getValue().toString());
                }

                @Override
                public void onProductListLoadFailed(String errorMessage) {

                }
            });
        }

//        Log.d("firestore", "onProductLoaded vm: " + product.toString());
    }


    // start: ORDER-----------
    // load order from firestore
    public void loadOrder(){
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
                totalPrice.setValue(calculateTotalPrice(order.getOrderItem()));
                totalProductLiveData.setValue(calculateTotalProduct(order.getOrderItem()));
                orderPriceLiveData.setValue(calculateOrderPrice());
//                loadProductList(order.getOrderItem());

//                Log.d("firestore", "onload order: " + productListLiveData.getValue().toString());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    /*
    todo: flow checkout:
    (1) check if selected coupon, point, product is available
    if yes
        update cart to order (2)
    else
        reload checkout activity (3)

     (2) update cart to order
     - set order live data (address, create on, discount_value, order price,
            payment, point, status, total_price, total_product)
     - update quantity for selected coupon, product
     - minus selected point
     - add reward point when checkout success
     - move to checkout success activity
     - send notification to admin =)))))))))))))))))

     (3) reload checkout activity
     - if point is unavailable
        update point live data, order price live data
     - if coupon is unavailable
        update coupon live data, discount value, order price live data
     - if products are unavailable
        update orderItem in orderLiveData (change quantity or remove in firestore too),
            total price, total product, order_price and coupon, discount value because min_order change
     */

    // method to check valid before checkout
    public void checkout(){
        // (1) check if selected coupon, point, product is available

        //check point
        /*
        todo: không cần check điểm vì firestore cập nhật là giao diện cập nhật theo. vậy có cần báo cho user không?
         */
        // check coupon
        if(discountValueLiveData.getValue() < 0){
            couponRepository.getCouponById(couponLiveData.getValue().getIdCoupon(), new CouponRepository.callBackCoupon() {
                @Override
                public void loadDataSuccess(Coupon coupon) {
                    if(coupon.getQuantity() < 1){
                        Toast.makeText(context, "Mã giảm giá đã hết lượt sử dụng!", Toast.LENGTH_LONG).show();
                        isValid = false;
                        reloadCheckoutActivity();
                    }
                }

                @Override
                public void loadDataFail(Exception e) {
                    Toast.makeText(context, "Mã giảm giá không tồn tại!", Toast.LENGTH_LONG).show();
                    isValid = false;
                    reloadCheckoutActivity();
                }
            });
        }

        if(isValid){
            //check quantity
            List<String> ids = new ArrayList<>();
            if(orderLiveData.getValue() != null){
                Order order = orderLiveData.getValue();
                for (Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()) {
                    ids.add(entry.getValue().getIdProduct());
                }
                productRepository.getProductListByIds(ids, new IProductRepository.ProductListCallback() {
                    @Override
                    public void onProductListLoaded(List<Product> productList) {
//                        productListLiveData.setValue(productList);
                        for(Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()){
                            String size;
                            if(entry.getValue().getSize() == null || entry.getValue().getSize().isEmpty())
                            size = "Mặc định";

                            else size = entry.getValue().getSize();
                            int index = productList.indexOf(entry.getValue().getIdProduct());
                            if(productList.get(index).getProductSize().get(size).get("QUANTITY") < 1){
                                Toast.makeText(context, entry.getValue().getProductName() + " không còn đủ số lượng!", Toast.LENGTH_LONG).show();
                                isValid = false;
                                break;
                            }
                        }

                        if(isValid){
//                            showProgressDialog("Đang xử lý...");
//            // update order before upload to firestore
//            Date now = new Date();
                                Order order = orderLiveData.getValue();
//            order.setTotalPrice(totalPrice.getValue());
//            order.setTotalProduct(totalProductLiveData.getValue());
//            order.setOrderPrice(orderPriceLiveData.getValue());
//            order.setAddress(userAddressLiveData.getValue().getAllAddress());
//            order.setRecipientName(userAddressLiveData.getValue().getRecipientName());
//            order.setRecipientPhone(userAddressLiveData.getValue().getRecipientPhone());
//            order.setPoint(pointUsedLiveData.getValue());
//            order.setDiscountValue(discountValueLiveData.getValue());
//            order.setPayment(paymentMethodLiveData.getValue());
//            order.setDeliveryCost(0);
//            order.setCreateOn(now);
//            order.setStatus("Chờ xác nhận");
//
////            // update product quantity
//            for(Map.Entry<String, OrderItem> orderItemEntry : order.getOrderItem().entrySet()){
//                String size;
//                if(orderItemEntry.getValue().getSize() == null || orderItemEntry.getValue().getSize().isEmpty()){
//                    size = "Mặc định";
//                }
//                else
//                    size = orderItemEntry.getValue().getSize();
//                productRepository.updateProductQuantity(
//                        orderItemEntry.getValue().getIdProduct(),
//                        size,
//                        orderItemEntry.getValue().getQuantity());
//            }
//
//            // update coupon quantity
//            if(discountValueLiveData.getValue() < 0 && discountValueLiveData.getValue() != null)
//                couponRepository.updateQuantityOfCoupon( couponLiveData.getValue(), false);
//
//            // update user point (minus current point and add reward point)
//            if(pointUsedLiveData.getValue() != 0){
//                UserPoint minusPoint = new UserPoint();
//                String id = UUID.randomUUID().toString();
//                minusPoint.setId(id);
//                minusPoint.setPoint(pointUsedLiveData.getValue());
//                minusPoint.setPointDate(now);
//                minusPoint.setUserId(userId);
//                pointRepository.AddPoint(minusPoint);
//            }
//
//            UserPoint plusPoint = new UserPoint();
//            String id1 = UUID.randomUUID().toString();
//            plusPoint.setId(id1);
//            plusPoint.setPoint((int)order.getOrderPrice() / 1000);
//            plusPoint.setPointDate(now);
//            plusPoint.setUserId(userId);
//            pointRepository.AddPoint(plusPoint);
//
//            // update cart to order status "chờ xác nhận" in firestore
//            orderRepository.updateCartToOrder(order);


                                // todo: send notificaiton to admin
                                // todo: hủy đơn hàng thì nhớ cộng lại sl sp vào và có cộng coupon???

                                // start buy success activity if success
                                Intent intent = new Intent(context, BuySuccessActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("orderId", order.getId());
                                intent.putExtras(bundle);
                                context.startActivity(intent);

                                dismissProgressDialog();
                            }
                            else
                                reloadCheckoutActivity();
                        }
//                    }

                    @Override
                    public void onProductListLoadFailed(String errorMessage) {
                         Toast.makeText(context, "Lỗi vì không tìm thấy sản phẩm", Toast.LENGTH_LONG).show();
                        isValid = false;
                    }
                });
            }
//            for(Map.Entry<String, OrderItem> orderItemEntry : getOrderLiveData().getValue().getOrderItem().entrySet()){
//                productRepository.getProductById(orderItemEntry.getValue().getIdProduct(), new IProductRepository.ProductCallback() {
//                    @Override
//                    public void onProductLoaded(Product product) {
//                        String size;
//                        if(orderItemEntry.getValue().getSize() == null || orderItemEntry.getValue().getSize().isEmpty()){
//                            size = "Mặc định";
//                        }
//                        else size = orderItemEntry.getValue().getSize();
//                        if(product.getProductSize().get(size).get("QUANTITY") < 1){
//                            Toast.makeText(context, product.getProductName() + " không còn đủ số lượng!", Toast.LENGTH_LONG).show();
//                            isValid = false;
//                        }
//                    }
//
//                    @Override
//                    public void onProductLoadFailed(String errorMessage) {
//                        Toast.makeText(context, "Lỗi vì không tìm thấy sản phẩm", Toast.LENGTH_LONG).show();
//                        isValid = false;
//                    }
//                });
//
//                if(!isValid)
//                    break;
//            }
        }

//        return isValid;
    }

    // Checkout method using repository and Firestore transaction

//    public void checkout(){
//        showProgressDialog("Đang xử lý...");
//
//        if(isValidCheckout()){
////            // update order before upload to firestore
////            Date now = new Date();
//            Order order = orderLiveData.getValue();
////            order.setTotalPrice(totalPrice.getValue());
////            order.setTotalProduct(totalProductLiveData.getValue());
////            order.setOrderPrice(orderPriceLiveData.getValue());
////            order.setAddress(userAddressLiveData.getValue().getAllAddress());
////            order.setRecipientName(userAddressLiveData.getValue().getRecipientName());
////            order.setRecipientPhone(userAddressLiveData.getValue().getRecipientPhone());
////            order.setPoint(pointUsedLiveData.getValue());
////            order.setDiscountValue(discountValueLiveData.getValue());
////            order.setPayment(paymentMethodLiveData.getValue());
////            order.setDeliveryCost(0);
////            order.setCreateOn(now);
////            order.setStatus("Chờ xác nhận");
////
//////            // update product quantity
////            for(Map.Entry<String, OrderItem> orderItemEntry : order.getOrderItem().entrySet()){
////                String size;
////                if(orderItemEntry.getValue().getSize() == null || orderItemEntry.getValue().getSize().isEmpty()){
////                    size = "Mặc định";
////                }
////                else
////                    size = orderItemEntry.getValue().getSize();
////                productRepository.updateProductQuantity(
////                        orderItemEntry.getValue().getIdProduct(),
////                        size,
////                        orderItemEntry.getValue().getQuantity());
////            }
////
////            // update coupon quantity
////            if(discountValueLiveData.getValue() < 0 && discountValueLiveData.getValue() != null)
////                couponRepository.updateQuantityOfCoupon( couponLiveData.getValue(), false);
////
////            // update user point (minus current point and add reward point)
////            if(pointUsedLiveData.getValue() != 0){
////                UserPoint minusPoint = new UserPoint();
////                String id = UUID.randomUUID().toString();
////                minusPoint.setId(id);
////                minusPoint.setPoint(pointUsedLiveData.getValue());
////                minusPoint.setPointDate(now);
////                minusPoint.setUserId(userId);
////                pointRepository.AddPoint(minusPoint);
////            }
////
////            UserPoint plusPoint = new UserPoint();
////            String id1 = UUID.randomUUID().toString();
////            plusPoint.setId(id1);
////            plusPoint.setPoint((int)order.getOrderPrice() / 1000);
////            plusPoint.setPointDate(now);
////            plusPoint.setUserId(userId);
////            pointRepository.AddPoint(plusPoint);
////
////            // update cart to order status "chờ xác nhận" in firestore
////            orderRepository.updateCartToOrder(order);
//
//
//            // todo: send notificaiton to admin
//            // todo: hủy đơn hàng thì nhớ cộng lại sl sp vào và có cộng coupon???
//
//            // start buy success activity if success
//            Intent intent = new Intent(context, BuySuccessActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("orderId", order.getId());
//            intent.putExtras(bundle);
//            context.startActivity(intent);
//
//            dismissProgressDialog();
//        }
//        else
//            reloadCheckoutActivity();
//    }

    private void reloadCheckoutActivity() {
        // Implementation to reload checkout activity
    }

     //end: ORDER-----------


    // load default user address (when checkout activity first runing)
    public void loadDefaultUserAddress(String userId){
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

    // load selected user address by id
    public void loadUserAddress(String addressId){
        userInfoRepository.getAddressById(addressId, new UserInfoRepository.userAddressCallback() {
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

        // update order price
        orderPriceLiveData.setValue(calculateOrderPrice());
    }

    // load payment method
    public void loadPaymentMethod(String method){
        paymentMethodLiveData.setValue(method);
        if(method.equals("Thanh toán khi nhận hàng"))
            iconPaymentLiveData.setValue(R.drawable.cash);
        else iconPaymentLiveData.setValue(R.drawable.paypal);
    }

    // load coupon by coupon id
    public void loadCoupon(String couponId){
        couponRepository.getCouponById(couponId, new CouponRepository.callBackCoupon() {
            @Override
            public void loadDataSuccess(Coupon coupon) {
                couponLiveData.setValue(coupon);
            }

            @Override
            public void loadDataFail(Exception e) {
                //todo: nếu ko có coupon thì hiện thông báo coupon ko hợp lệ hay gì đó
            }
        });
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
    public void loadDiscountValue(double percentDiscount) {
        // Calculate the discount value as a floating-point operation
        double discountValue = -1 * (totalPrice.getValue() + pointUsedLiveData.getValue()) * (percentDiscount / 100.0);
        // Convert the discount value to an integer (if needed)
        int discountValueInteger = (int) discountValue;

        discountValueLiveData.setValue(discountValueInteger);
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
