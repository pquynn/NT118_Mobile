package com.example.javajoyadmin.ui.viewmodel.admin.order;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javajoyadmin.data.model.SendNotification;
import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.data.model.entity.Order;
import com.example.javajoyadmin.data.repository.authentication.AuthRepository;
import com.example.javajoyadmin.data.repository.notification.INotificationRepository;
import com.example.javajoyadmin.data.repository.notification.NotificationRepository;
import com.example.javajoyadmin.data.repository.order.IOrderRepository;
import com.example.javajoyadmin.data.repository.order.OrderRepository;

import java.util.Date;
import java.util.UUID;

public class AdminOrderDetailVM extends ViewModel {
    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;
    private AuthRepository authRepository;
    private String orderID;
    private MutableLiveData<Order> orderMutableLiveData;

    public AdminOrderDetailVM(String orderId) {
        notificationRepository = new NotificationRepository();
        authRepository = new AuthRepository();
        this.orderID = orderId;
        getData(orderID);
    }

    public void updateOrderStatusByID(String orderID, String status, Context context) {
        orderRepository.updateOrderStatusById(orderID, status, new IOrderRepository.OrderChangedCallback() {
            @Override
            public void onOrderChanged() {
                sendNotification(status, context);
                Toast.makeText(context, "Update successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "Update failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(String orderId) {
        orderMutableLiveData = new MutableLiveData<>();
        orderRepository = new OrderRepository();

        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderMutableLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("AdminOrderDetailVM", "Load order fail!");
            }
        });
    }

    public MutableLiveData<Order> getOrder() {
        getData(orderID);
        return orderMutableLiveData;
    }

    // method to send notification
    private String title = "", body = "", oldTitle = "";
    public void sendNotification(@NonNull String status, Context context){
        authRepository.getUserTokenByUserId(orderMutableLiveData.getValue().getIdUser(), new AuthRepository.TokenCallback() {
            @Override
            public void onSuccess(String token) {
                if(status.equals("Đã xác nhận")){
                    title = "Đơn hàng đã được xác nhận";
                    body = "Đơn hàng " + orderID + " đã được xác nhận. Hãy kiểm tra lại nhé";
                }
                else if(status.equals("Đang giao")){
                    oldTitle = "Đơn hàng đã được xác nhận";
                    title = "Đơn hàng đang giao đến bạn";
                    body = "Đơn hàng " + orderID + " đang trên đường giao đến bạn. Hãy chú ý điện thoại";
                }
                else  if(status.equals("Đã giao")){
                    oldTitle = "Đơn hàng đang giao đến bạn";
                    title = "Đơn hàng đã giao thành công";
                    body = "Đơn hàng " + orderID + " đã được giao thành công. Hãy kiểm tra lại nhé";
                }

                // send notification
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendNotification notificationSender =
                                new SendNotification(token, title, body, orderID, context);
                        notificationSender.SendNotifications();
                    }
                } , 100);

                if(status.equals("Đã xác nhận")){
                    // create notification in firestore
                    Notification notification = new Notification();
                    notification.setId(UUID.randomUUID().toString());
                    notification.setIdRecipient(orderMutableLiveData.getValue().getIdUser());
                    notification.setRecipientType(0);
                    notification.setIdOrder(orderID);
                    notification.setStatus("unread");
                    notification.setDate(new Date());
                    notification.setTitle(title);
                    notification.setContent(body);
                    notificationRepository.createNotification(notification);
                }
                else {
                    notificationRepository.getNotificationByOrderIdAndTitle(
                            orderID,
                            oldTitle,
                            new INotificationRepository.NotificationCallback() {
                                @Override
                                public void onLoaded(Notification notification) {
                                    notification.setStatus("unread");
                                    notification.setDate(new Date());
                                    notification.setTitle(title);
                                    notification.setContent(body);
                                    notificationRepository.updateNotification(notification);
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    Log.d("fcm", errorMessage);
                                }
                            }
                    );
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }
}
