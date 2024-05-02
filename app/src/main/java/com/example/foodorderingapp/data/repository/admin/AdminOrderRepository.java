package com.example.foodorderingapp.data.repository.admin;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.Coupon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderRepository {

    private List<Coupon> listCoupon = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("ORDER");

    // Hàm lấy danh sách hóa đơn theo trạng thái hóa đơn
    public void getListOrder(String status) {
        reference.whereEqualTo("STATUS", status)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });

    }

    // Hàm lấy chi tiết hóa đơn theo ID của hóa đơn
    public void getOrderDetail(String idOrder) {
        reference.document(idOrder)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                        } else {

                        }
                    }
                });

    }

    // Hàm cập nhật trạng thái của hóa đơn
    public void updateOrderStatus(String idOrder, String status) {

    }

    public interface gerOrderDetailCallback{
        public void getDataSuccess();

        public void getDataFail();
    }
}
