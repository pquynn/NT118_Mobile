package com.example.foodorderingapp.Data.Repository.AccountManagement.MyOrders;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrdersListFeedbackRepository {
    //Model = OrderDetail
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("ORDER");
    private List<OrderDetail> orderDetailList = new ArrayList<>();
    public void getlistFeedback(String orderid, orderItemCallback callback){
        reference.whereEqualTo("ID", orderid).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                    if(document.exists()){
                        Map<String,Object> orderItemData = (Map<String, Object>) document.getData().get("ORDER_ITEM");

                        if (orderItemData != null){
                            for(Map.Entry<String, Object> entry : orderItemData.entrySet()){

                                String itemId = entry.getKey();
                                Map<String, Object> itemData = (Map<String, Object>) entry.getValue();
                                Log.d("Test", parseOrderItem(itemData).toString());
                                orderDetailList.add(parseOrderItem(itemData));
                            }
                        }
                    }
                    callback.loadOrderItemsSuccess(orderDetailList);
                }else{
                    callback.loadOrderItemsError(task.getException());
                }
            }
        });
    }

    private OrderDetail parseOrderItem(Map<String, Object> itemData) {
        String productId = (String) itemData.get("ID_PRODUCT");
        String note = (String) itemData.get("NOTE");
        String size = (String) itemData.get("SIZE");
        Long productPriceStr = (Long) itemData.get("PRICE");
        Long quantityStr = (Long) itemData.get("QUANTITY");
        int productPrice = Integer.valueOf(Long.toString(productPriceStr));
        int quantity = Integer.valueOf(Long.toString(quantityStr));

        return new OrderDetail(productId, productPrice, size, note, quantity);
    }

    public interface orderItemCallback{
        void loadOrderItemsSuccess(List<OrderDetail> orderItems);
        void loadOrderItemsError(Exception e);
    }
}
