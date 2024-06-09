package com.example.foodorderingapp.ui.viewmodel.customer.checkout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.CreateOrder;
import com.example.foodorderingapp.data.model.SendNotification;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.model.entity.UserPoint;
import com.example.foodorderingapp.data.repository.accountmanagement.PointRepository;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.data.repository.authentication.AuthRepository;
import com.example.foodorderingapp.data.repository.coupon.CouponRepository;
import com.example.foodorderingapp.data.repository.notification.NotificationRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.checkout.BuySuccessActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.checkout.CheckoutActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.refund.SendRefundSucessActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.N;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CheckoutViewModel extends ViewModel {
    private String userId, token;
    private String img = "@drawable/cash";
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context;
    private Activity activity;
    
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
    private MutableLiveData<Integer> deliveryCostLiveData = new MutableLiveData<>();

    // REPOSITORY
    private OrderRepository orderRepository;
    private UserInfoRepository userInfoRepository;
    private PointRepository pointRepository;
    private CouponRepository couponRepository;
    private ProductRepository productRepository;
    private NotificationRepository notificationRepository;
    private AuthRepository authRepository;
    // momo
    private MutableLiveData<Boolean> paymentRequestResult = new MutableLiveData<>();
    private String amount = "1000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Thanh toán";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ ABC";

    // CONSTRUCTOR
    public CheckoutViewModel(String userId, Context context, Activity activity){
        this.userId = userId;
        this.context = context;
        this.activity = activity;

        orderRepository = new OrderRepository();
        userInfoRepository = new UserInfoRepository();
        pointRepository = new PointRepository();
        couponRepository = new CouponRepository();
        productRepository = new ProductRepository();
        notificationRepository = new NotificationRepository();
        authRepository = new AuthRepository();

        loadDefaultUserAddress(userId);
        deliveryCostLiveData.setValue(0);
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

    public MutableLiveData<Integer> getDeliveryCostLiveData() {
        return deliveryCostLiveData;
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
    public MutableLiveData<Coupon> getCouponLiveData() {
        return couponLiveData;
    }

    public MutableLiveData<Boolean> getPaymentRequestResult() {
        return paymentRequestResult;
    }
// end: GETTER


    // start: ORDER-----------
    // load order from firestore
    public void loadOrder(){
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
        //todo: về việc xử lý trường hợp hết hàng cho sản phẩm nhất định
        nếu có sản phẩm không đủ số lượng --> hiện alert xem user muốn trở về giỏ hàng để cập nhật lại sl
        nếu có sản phẩm hết hàng trong giỏ hàng --> todo: có thể làm chức năng bổ sung
        hiện tại giỏ hàng xử lý: nếu có hết hàng --> ko cho mua --> xóa sp thì đc mua
     */

    // method to check valid before checkout
    public void checkout() {
        showProgressDialog("Đang xử lý...");

        if (discountValueLiveData.getValue() < 0) {
            couponRepository.getCouponById(couponLiveData.getValue().getIdCoupon(), new CouponRepository.callBackCoupon() {
                @Override
                public void loadDataSuccess(Coupon coupon) {
                    if (coupon.getQuantity() < 1) {
                        Toast.makeText(context, "Mã giảm giá đã hết lượt sử dụng!", Toast.LENGTH_LONG).show();
                        dismissProgressDialog();
                        reloadSelectedCoupon();
                    } else {
                        // check product quantity
                        checkProductQuantity();
                    }
                }

                @Override
                public void loadDataFail(Exception e) {
                    Toast.makeText(context, "Mã giảm giá không tồn tại!", Toast.LENGTH_LONG).show();
                    dismissProgressDialog();
                    reloadSelectedCoupon();
                }
            });
        }
        else checkProductQuantity();
    }

    public void checkProductQuantity(){
        // Check quantity
        List<String> ids = new ArrayList<>();
        if (orderLiveData.getValue() != null) {
            Order order = orderLiveData.getValue();
            for (Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()) {
                ids.add(entry.getValue().getIdProduct());
            }

            productRepository.getProductListByIds(ids, new IProductRepository.ProductListCallback() {
                @Override
                public void onProductListLoaded(List<Product> productList) {
                    boolean allProductsAvailable = true;
                    List<String> unavailablePName = new ArrayList<>();

                    for (Map.Entry<String, OrderItem> entry : order.getOrderItem().entrySet()) {
                        String size = (entry.getValue().getSize() == null || entry.getValue().getSize().isEmpty()) ? "Mặc định" : entry.getValue().getSize();
                        String productId = entry.getValue().getIdProduct();

                        Product product = null;
                        for (Product p : productList) {
                            if (p.getId().equals(productId)) {
                                product = p;
                                break;
                            }
                        }

                        if (product == null || product.getProductSize().get(size).get("QUANTITY") < entry.getValue().getQuantity()) {
                            allProductsAvailable = false;
                            unavailablePName.add(product.getProductName());
                        }
                    }

                    if (allProductsAvailable) {
                        dismissProgressDialog();

                        if(paymentMethodLiveData.getValue().equals("Ví MoMo")){
                            requestPayment();
                        }
                        else if(paymentMethodLiveData.getValue().equals("ZaloPay")){
                            requestZalo();
                        }
                        else{
                            processOrder();
                        }

                    }
                    else{
                        dismissProgressDialog();
                        showAlertDialog(context, unavailablePName);
                    }
                }

                @Override
                public void onProductListLoadFailed(String errorMessage) {
                    Toast.makeText(context, "Lỗi vì không tìm thấy sản phẩm", Toast.LENGTH_LONG).show();
                    dismissProgressDialog();
                }
            });
        }
    }

    // method to process order: update order from cart to official order
    public void processOrder() {
        // update order before upload to firestore
        Date now = new Date();
        Order order = orderLiveData.getValue();
        order.setTotalPrice(totalPrice.getValue());
        order.setTotalProduct(totalProductLiveData.getValue());
        order.setOrderPrice(orderPriceLiveData.getValue());
        order.setAddress(userAddressLiveData.getValue().getAllAddress());
        order.setRecipientName(userAddressLiveData.getValue().getRecipientName());
        order.setRecipientPhone(userAddressLiveData.getValue().getRecipientPhone());
        order.setPoint(pointUsedLiveData.getValue());
        order.setDiscountValue(discountValueLiveData.getValue());
        order.setPayment(paymentMethodLiveData.getValue());
        order.setDeliveryCost(deliveryCostLiveData.getValue());
        order.setCreateOn(now);
        order.setStatus("Chờ xác nhận");

        // update product quantity
        for(Map.Entry<String, OrderItem> orderItemEntry : order.getOrderItem().entrySet()){
            String size;
            if(orderItemEntry.getValue().getSize() == null || orderItemEntry.getValue().getSize().isEmpty()){
                size = "Mặc định";
            }
            else
                size = orderItemEntry.getValue().getSize();
            productRepository.updateProductQuantity(
                    orderItemEntry.getValue().getIdProduct(),
                    size,
                    orderItemEntry.getValue().getQuantity());
        }

        // update coupon quantity
        if(discountValueLiveData.getValue() < 0 && discountValueLiveData.getValue() != null)
            couponRepository.updateQuantityOfCoupon( couponLiveData.getValue(), false);

//        // update user point (minus current point and add reward point)
//        if(pointUsedLiveData.getValue() != 0){
//            UserPoint minusPoint = new UserPoint();
//            String id = UUID.randomUUID().toString();
//            minusPoint.setId(id);
//            minusPoint.setPoint(pointUsedLiveData.getValue());
//            minusPoint.setPointDate(now);
//            minusPoint.setUserId(userId);
//            pointRepository.AddPoint(minusPoint);
//        }
//
//        UserPoint plusPoint = new UserPoint();
//        String id1 = UUID.randomUUID().toString();
//        plusPoint.setId(id1);
//        plusPoint.setPoint((int)order.getOrderPrice() / 1000);
//        plusPoint.setPointDate(now);
//        plusPoint.setUserId(userId);
//        pointRepository.AddPoint(plusPoint);

        // update cart to order status "chờ xác nhận" in firestore
        orderRepository.updateCartToOrder(order);
        // send notificaiton to admin
        sendNotification();
    }

    // method to show alert if product quantity is unavailable
    public void showAlertDialog(Context context, List<String> productName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        // ghep ten sp
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < productName.size(); i++) {
            stringBuilder.append(productName.get(i));
            if (i < productName.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        alert.setTitle("Thông báo");
        alert.setMessage(stringBuilder.toString()
                + " hiện không đủ số lượng. Quay về giỏ hàng để chọn lại sản phẩm.");
        alert.setPositiveButton("Về giỏ hàng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

        alert.show();
    }

    // method to init environment for online payment
    public void initializeEnvironment() {
        //momo
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);

        //zalo
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
    }


    //Request zalo pay method
    private void requestZalo(){
        CreateOrder orderApi = new CreateOrder();

        try {
            String amount = orderPriceLiveData.getValue().toString();
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("return_code");

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(activity, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        processOrder();
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Toast.makeText(context, "Giao dịch không thành công! Bạn đã hủy thanh toán.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Toast.makeText(context, "Giao dịch đang bị lỗi! Hãy thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Get token through MoMo app--------
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", "orderId123456789"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(activity, eventValue);

    }

    public void reloadSelectedCoupon(){
        Coupon coupon = new Coupon();
        coupon.setIdCoupon("");
        couponLiveData.setValue(coupon);
        discountValueLiveData.setValue(0);
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
        else if(method.equals("ZaloPay"))
            iconPaymentLiveData.setValue(R.drawable.zalopay);
        else iconPaymentLiveData.setValue(R.drawable.momo);
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
        orderPriceLiveData.setValue(calculateOrderPrice());
    }


    // calculate order price
    public int calculateOrderPrice() {
        int orderPrice = 0;

        Integer totalPriceValue = totalPrice.getValue();
        Integer deliveryCostValue = deliveryCostLiveData.getValue();
        Integer pointUsedValue = pointUsedLiveData.getValue();
        Integer discountValue = discountValueLiveData.getValue();

        // Use default values if any of the LiveData values are null
        orderPrice = (totalPriceValue != null ? totalPriceValue : 0)
                + (deliveryCostValue != null ? deliveryCostValue : 0)
                + (pointUsedValue != null ? pointUsedValue : 0)
                + (discountValue != null ? discountValue : 0);

        // todo: if have delivery cost --> add to orderPrice
        return orderPrice;
    }


    // Method to dismiss progress dialog
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void showProgressDialog(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    // method to send notification
    public void sendNotification(){
        authRepository.getAdminInfo(new AuthRepository.AdminInfoCallback() {
            @Override
            public void onSuccess(String userId, String token) {
                String orderId = orderLiveData.getValue().getId();
                String title = "Bạn có đơn hàng mới";
                String body = "Đơn hàng " + orderId + " đang chờ bạn xác nhận";

                // send notification
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendNotification notificationSender =
                                new SendNotification(token, title, body, context);
                        notificationSender.SendNotifications();

                    }
                } , 20);

                // create notification in firestore
                Notification notification = new Notification();
                notification.setId(UUID.randomUUID().toString());
                notification.setIdRecipient(userId);
                notification.setRecipientType(1);
                notification.setIdOrder(orderId);
                notification.setStatus("unread");
                notification.setDate(new Date());
                notification.setTitle(title);
                notification.setContent(body);
                notificationRepository.createNotification(notification);

                // Assuming the process is successful:
                dismissProgressDialog();
                Intent intent = new Intent(context, BuySuccessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                intent.putExtras(bundle);
                context.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
