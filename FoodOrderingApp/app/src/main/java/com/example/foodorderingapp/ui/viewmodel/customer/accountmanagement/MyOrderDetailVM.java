package com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.SendNotification;
import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.data.repository.accountmanagement.UserInfoRepository;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.data.repository.authentication.AuthRepository;
import com.example.foodorderingapp.data.repository.notification.NotificationRepository;
import com.example.foodorderingapp.data.repository.order.IOrderRepository;
import com.example.foodorderingapp.data.repository.order.OrderRepository;

import java.util.Date;
import java.util.UUID;

public class MyOrderDetailVM extends ViewModel {
    private OrderRepository orderRepository;
    private OrdersFeedbackRepository ordersFeedbackRepository;

    private NotificationRepository notificationRepository = new NotificationRepository();
    private AuthRepository authRepository = new AuthRepository();
    private Context context;
    private String orderId, userId;
    private Activity activity;
    private MutableLiveData<String> orderStatusLiveDate;
    private MutableLiveData<Order> orderMutableLiveData;

    public MyOrderDetailVM(String orderId, Context context, Activity activity){
        orderStatusLiveDate = new MutableLiveData<>();
        orderMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.orderId = orderId;
        this.activity = activity;

        ordersFeedbackRepository = new OrdersFeedbackRepository();
        orderRepository = new OrderRepository();
        //loadOrderStatus(orderId);
        loadOrderDetail(orderId);
    }

    public MutableLiveData<String> getOrderStatusLiveDate(){
        loadOrderStatus(orderId);
        return orderStatusLiveDate;
    }
    //get order info
    public MutableLiveData<Order> getOrderMutableLiveData(){
        loadOrderDetail(orderId);
        return orderMutableLiveData;
    }
    private void loadOrderStatus(String orderId) {
        ordersFeedbackRepository.getOrderStatus(orderId, new OrdersFeedbackRepository.orderStatusCallback() {
            @Override
            public void loadOrderStatusSuccess(String status) {
                orderStatusLiveDate.setValue(status);
            }

            @Override
            public void loadOrderStatusError(Exception e) {
                Log.e("Error load order status", "Error load order status in MyOrderDetailVM");
            }
        });
    }
    private void loadOrderDetail(String orderId){
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                userId = order.getIdUser();
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }


    // method to send notification
    public void sendNotification(){
        authRepository.getAdminInfo(new AuthRepository.AdminInfoCallback() {
            @Override
            public void onSuccess(String userId, String token) {
                String orderId = orderMutableLiveData.getValue().getId();
                String title = "Bạn có đơn hàng đã bị hủy";
                String body = "Đơn hàng " + orderId + " đã bị hủy. Hãy kiểm tra lại.";

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

                activity.finish();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
