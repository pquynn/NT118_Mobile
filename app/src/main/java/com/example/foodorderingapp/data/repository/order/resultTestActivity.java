package com.example.foodorderingapp.data.repository.order;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.notification.INotificationRepository;
import com.example.foodorderingapp.data.repository.notification.NotificationRepository;
import com.example.foodorderingapp.data.repository.product.IProductRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class resultTestActivity extends AppCompatActivity {
    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;
    private ProductRepository productRepository;
    private Notification notification;
    private Order od;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);

        orderRepository = new OrderRepository();
        notificationRepository = new NotificationRepository();
//        productRepository = new ProductRepository();
//        productRepository.getProductById("1", new IProductRepository.ProductCallback() {
//            @Override
//            public void onProductLoaded(Product product) {
//
//            }
//
//            @Override
//            public void onProductLoadFailed(String errorMessage) {
//
//            }
//        });
//        notificationRepository.getNotificationByRecipientIdAndType("3", 0, new INotificationRepository.NotificationListCallback() {
//            @Override
//            public void onListLoaded(List<Notification> notificationList) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//        orderRepository.getOrderById("1", new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//                Order a = order;
//
//            }
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

//        orderRepository.getCartByUserId("3", new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//
//            }
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });


//
//        // Create an instance of OrderItem
//        OrderItem orderItem = new OrderItem();
//        String id = UUID.randomUUID().toString();
//        orderItem.setIdOrderItem(id);
//        orderItem.setIdProduct("4");
//        orderItem.setQuantity(2);
//        orderItem.setPrice(10);
//        orderItem.setNote("No notes");
//        orderItem.setSize("Vừa");
//        ArrayList<String> topping = new ArrayList<>();
//        topping.add("3");
//        topping.add("1");
//        orderItem.setTopping(topping);
//
//        // Create a Map<String, OrderItem> and add the OrderItem to it
//        Map<String, OrderItem> orderItemMap = new HashMap<>();
//        orderItemMap.put(orderItem.getIdOrderItem(), orderItem);
//        orderRepository.createOrder("1", orderItemMap, new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

//         Create an instance of OrderItem
        OrderItem orderItem = new OrderItem();
        String id = UUID.randomUUID().toString();
//        orderItem.setIdOrderItem("602447ac-2108-4d83-aca6-15349523d78d");
        orderItem.setIdProduct("12");
        orderItem.setProductName("Hi Tea Đào");
        orderItem.setProductImage("https://firebasestorage.googleapis.com/v0/b/javajoy-mobileapp.appspot.com/o/1%2FHi%20Tea%20%C4%90%C3%A0o.png?alt=media&token=35bf18ac-c242-4b3c-ac55-065c5352dad1");
        orderItem.setQuantity(1);
        orderItem.setPrice(10000);
        orderItem.setNote("Ít đường");
        orderItem.setSize("Vừa");
        ArrayList<String> topping = new ArrayList<>();
        topping.add("1");
        topping.add("5");
        orderItem.setTopping(topping);
        orderRepository.addOrUpdateProductCart("4", id, orderItem, new IOrderRepository.OrderChangedCallback() {
                @Override
                public void onOrderChanged() {

                }

                @Override
                public void onError(String errorMessage) {

                }
            });

//        orderRepository.deleteProductCart("UV4jcGJFA5p37R0OUVqH", "602447ac-2108-4d83-aca6-15349523d78d", new IOrderRepository.OrderItemRemovedCallback() {
//            @Override
//            public void onOrderItemRemoved(String id) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//
//                orderRepository.getOrderListByStatusAndUserId("3", "Đang giao", new IOrderRepository.OrderListCallback() {
//                    @Override
//                    public void onOrderListLoaded(List<Order> orderList) {
//
//                    }
//
//                    @Override
//                    public void onError(String errorMessage) {
//
//                    }
//                });

//        orderRepository.updateOrderStatusById("2", "Đã giao", new IOrderRepository.OrderChangedCallback() {
//            @Override
//            public void onOrderChanged() {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });

//        orderRepository.getOrderById("nqwbKhPyhuXNyvyaYnA4", new IOrderRepository.OrderCallback() {
//            @Override
//            public void onOrderLoaded(Order order) {
//                // Modify the order here
//                order.setAddress("aaaaaaaaaaaaaaaaaaaa");
//                order.setOrderPrice(100002);
//                order.setDeliveryCost(100002);
//                order.setTotalPrice(100002);
//
//                // Checkout the modified order
//                orderRepository.checkout(order, new IOrderRepository.OrderChangedCallback() {
//                    @Override
//                    public void onOrderChanged() {
//                        Log.d("FirestoreOrderRepository", "checkout: " + order.toString());
//                        // Update UI or perform any other actions after checkout
//                    }
//
//                    @Override
//                    public void onError(String errorMessage) {
//                        // Handle error
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                // Handle error
//            }
//        });
//


    }
}
