package com.example.javajoyadmin.ui.viewmodel.admin.refund;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javajoyadmin.data.model.OrderItem;
import com.example.javajoyadmin.data.model.SendNotification;
import com.example.javajoyadmin.data.model.entity.Coupon;
import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.data.model.entity.Order;
import com.example.javajoyadmin.data.model.entity.Product;
import com.example.javajoyadmin.data.model.entity.Refund;
import com.example.javajoyadmin.data.repository.authentication.AuthRepository;
import com.example.javajoyadmin.data.repository.notification.INotificationRepository;
import com.example.javajoyadmin.data.repository.notification.NotificationRepository;
import com.example.javajoyadmin.data.repository.order.IOrderRepository;
import com.example.javajoyadmin.data.repository.order.OrderRepository;
import com.example.javajoyadmin.data.repository.refund.IRefundRepository;
import com.example.javajoyadmin.data.repository.refund.RefundRepository;

import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RefundViewModel extends ViewModel {
    private String orderId;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context; // Context variable

    //Live data
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<Refund> refundLiveData = new MutableLiveData<>();
    // Repository
    private OrderRepository orderRepository;
    private RefundRepository refundRepository;
    private NotificationRepository notificationRepository;
    private AuthRepository authRepository;

    // Constructor
    public RefundViewModel(String orderId, Context context) {
        this.orderId = orderId;
        this.context = context;
        orderRepository = new OrderRepository();
        refundRepository = new RefundRepository();
        notificationRepository = new NotificationRepository();
        authRepository = new AuthRepository();
    }

    // Getter
    public MutableLiveData<Order> getOrderLiveData() {
        loadOrder();
        return orderLiveData;
    }

    public MutableLiveData<Refund> getRefundLiveData() {
        loadRefund();
        return refundLiveData;
    }


// Loader

    // Load order live data by order id
    public void loadOrder(){
        orderRepository.getOrderById(orderId, new IOrderRepository.OrderCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                orderLiveData.setValue(order);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    // Start: ADMIN------------------------------------
    // Load refund live data by order id
    public void loadRefund(){
        refundRepository.getRefundByOrderId(orderId, new IRefundRepository.RefundCallback() {
            @Override
            public void onRefundLoaded(Refund refund) {
                refundLiveData.setValue(refund);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    // update status of refund by refund id
    private String status = "";
    public void updateRefundStatus(String action){
        showProgressDialog("Đang xử lý...");
        if(action != null){
            if(action.equals("Chấp nhận hoàn tiền")){
                status = "Chờ hoàn tiền";
            }
            else if(action.equals("Từ chối hoàn tiền")){
                status = action;
            }
            else  if(action.equals("Đã hoàn tiền")){
                status = "Đã hoàn tiền";
            }
        }

        refundRepository.updateRefundStatusById(
                refundLiveData.getValue().getId(),
                status,
                new IRefundRepository.RefundChangedCallback() {
                    @Override
                    public void onRefundChanged() {
                        refundLiveData.getValue().setDateProcessed(new Date());
                        refundLiveData.getValue().setStatus(status);
                        dismissProgressDialog();
                        Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                        sendNotification(status);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        dismissProgressDialog();
                    }
                });
    }
    // End: ADMIN------------------------------------

    // method to show progress dialog
    private void showProgressDialog(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    // method to send notification
    private String title = "", body = "";
    private boolean isExist = false;
    public void sendNotification(String status){
        authRepository.getUserTokenByUserId(orderLiveData.getValue().getIdUser(), new AuthRepository.TokenCallback() {
            @Override
            public void onSuccess(String token) {
                if(status.equals("Chờ hoàn tiền")){
                    title = "Yêu cầu hoàn tiền được chấp nhận";
                    body = "JavaJoy đã chấp nhận yêu cầu hoàn tiền của bạn cho đơn hàng " + orderId;
                }
                else if(status.equals("Từ chối hoàn tiền")){
                    title = "Yêu cầu hoàn tiền bị từ chối";
                    body = "JavaJoy đã từ chối yêu cầu hoàn tiền của bạn cho đơn hàng " + orderId;
                }
                else  if(status.equals("Đã hoàn tiền")){
                    isExist = true;
                    title = "Bạn đã được hoàn lại tiền";
                    body = "JavaJoy đã hoàn lại tiền cho đơn hàng " + orderId + ". Hãy kiểm tra lại";
                }

                // send notification
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendNotification notificationSender =
                                new SendNotification(token, title, body, orderId, context);
                        notificationSender.SendNotifications();
                    }
                } , 100);

                if(!isExist){
                    // create notification in firestore
                    Notification notification = new Notification();
                    notification.setId(UUID.randomUUID().toString());
                    notification.setIdRecipient(orderLiveData.getValue().getIdUser());
                    notification.setRecipientType(0);
                    notification.setIdOrder(orderId);
                    notification.setStatus("unread");
                    notification.setDate(new Date());
                    notification.setTitle(title);
                    notification.setContent(body);
                    notificationRepository.createNotification(notification);
                }
                else {
                    notificationRepository.getNotificationByOrderIdAndTitle(
                            orderId,
                            "Yêu cầu hoàn tiền được chấp nhận",
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