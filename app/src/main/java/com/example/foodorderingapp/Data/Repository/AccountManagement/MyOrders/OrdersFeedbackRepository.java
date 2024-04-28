package com.example.foodorderingapp.Data.Repository.AccountManagement.MyOrders;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.Data.Model.OrderDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersFeedbackRepository {
    //Model = OrderDetail
    private class nameAndImage{
        String productName;
        String productImage;

        public nameAndImage(String productName, String productImage) {
            this.productName = productName;
            this.productImage = productImage;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
//    private orderItemCallback callback;
    private orderStatusCallback callbackStatus;
    private FirebaseFirestore db ;
//    private CollectionReference reference = firebaseFirestore.collection("ORDER");
//    private CollectionReference refProduct = firebaseFirestore.collection("PRODUCT");
    private ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
    private nameAndImage nameImageProduct = new nameAndImage("", "");
    private String productName;
    private String orderStatus = "";

    public OrdersFeedbackRepository() {
        db = FirebaseFirestore.getInstance();
    }

    //Get danh sách sản phẩm đánh giá
    public void listFeedback(String orderId, orderItemCallback callback) {
        db.collection("ORDER").document(orderId) // Sử dụng .document thay vì .whereEqualTo
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // Thay đổi thành DocumentSnapshot
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            DocumentSnapshot document = task.getResult(); // Lấy DocumentSnapshot thay vì QuerySnapshot
                            Map<String, Object> orderItemData = (Map<String, Object>) document.get("ORDER_ITEM");

                            if (orderItemData != null) {
                                for (Map.Entry<String, Object> entry : orderItemData.entrySet()) {
                                    String itemId = entry.getKey();
                                    Map<String, Object> itemData = (Map<String, Object>) entry.getValue();

                                    String productId = (String) itemData.get("ID_PRODUCT");

                                    // Gọi getProductName và chỉ thêm vào orderDetailList sau khi có kết quả
                                    getProductNameImage(productId, new productNameImageCallback() {
                                        @Override
                                        public void loadProductNameImageSuccess(nameAndImage nameImageProduct) {
                                            OrderDetail detail = parseOrderItem(itemData, nameImageProduct);
                                            orderDetailList.add(detail);

                                            // Gọi callback khi tất cả các item đã được xử lý
                                            if (orderDetailList.size() == orderItemData.size()) {
                                                callback.loadOrderItemsSuccess(orderDetailList);
                                            }
                                        }

                                        @Override
                                        public void loadProductNameImageError(Exception e) {
                                            callback.loadOrderItemsError(e);
                                        }
                                    });
                                }
                            } else {
                                callback.loadOrderItemsError(new Exception("No order item data found"));
                            }
                        } else {
                            callback.loadOrderItemsError(task.getException());
                        }
                    }
                });
    }


    //Get order_status
    public void getOrderStatus(String orderId, orderStatusCallback callback) {
        Log.d(TAG, "orderId: " + orderId);

        // Sử dụng document thay vì whereEqualTo để truy vấn một tài liệu bằng ID
        db.collection("ORDER").document(orderId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // Sử dụng DocumentSnapshot
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            DocumentSnapshot document = task.getResult(); // Lấy DocumentSnapshot từ task
                            String orderStatus = document.getString("STATUS"); // Lấy giá trị từ trường STATUS

                            if (orderStatus != null) {
                                callback.loadOrderStatusSuccess(orderStatus); // Gọi callback khi thành công
                            } else {
                                callback.loadOrderStatusError(new Exception("Status not found")); // Xử lý lỗi nếu không có status
                            }
                        } else {
                            callback.loadOrderStatusError(task.getException()); // Xử lý lỗi nếu task không thành công
                        }
                    }
                });
    }


    //Lấy dữ liệu và tạo đối tượng OrderDetail
    private OrderDetail parseOrderItem(Map<String, Object> itemData, nameAndImage nameImageProduct) {
        String productId = (String) itemData.get("ID_PRODUCT");
        String productNameGet = nameImageProduct.getProductName();
        String productImage = nameImageProduct.getProductImage();
        String note = (String) itemData.get("NOTE");
        String size = (String) itemData.get("SIZE");
        Long productPriceStr = (Long) itemData.get("PRICE");
        Long quantityStr = (Long) itemData.get("QUANTITY");
        int productPrice = Integer.valueOf(Long.toString(productPriceStr));
        int quantity = Integer.valueOf(Long.toString(quantityStr));

        return new OrderDetail(productNameGet, productPrice, size, note, quantity, productImage);
    }

    //Lấy product name, product image
    private void getProductNameImage(String productId, productNameImageCallback callback){

        db.collection("PRODUCT").whereEqualTo("ID", productId).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                            if(document.exists()){
                                String productNameGet = (String)document.getString("PRODUCT_NAME");
                                String productPitureGet = (String)document.getString("PRODUCT_IMAGE");
                                if(productNameGet!=null && productPitureGet!=null) {
                                    nameImageProduct.setProductName(productNameGet);
                                    nameImageProduct.setProductImage(productPitureGet);
                                }
                            }
                            callback.loadProductNameImageSuccess(nameImageProduct);
                        }else{
                            callback.loadProductNameImageError(task.getException());
                        }

                    }
                });
    }



    public ArrayList<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public String getStrOrderStatus() {
        return orderStatus;
    }

    public interface orderItemCallback{
        void loadOrderItemsSuccess(ArrayList<OrderDetail> orderItems);
        void loadOrderItemsError(Exception e);
    }

    private interface productNameImageCallback{
        void loadProductNameImageSuccess(nameAndImage nameImageProducts);
        void loadProductNameImageError(Exception e);
    }

    public interface orderStatusCallback{
        void loadOrderStatusSuccess(String status);
        void loadOrderStatusError(Exception e);
    }
}
