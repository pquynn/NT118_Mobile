package com.example.foodorderingapp.Data.Repository.Coupon;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.Data.Model.Entity.Coupon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponRepository {
    private List<Coupon> listCoupon = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("COUPON");

    public void updateQuantityOfCoupon(Coupon input, boolean isDelete) {
        Map<String, Object> counpon = new HashMap<>();
        if (isDelete) // Trường hợp xóa ưu đãi khỏi hóa đơn
            counpon.put("QUANTITY", input.getQuantity() + 1);
        else // Trường hợp thêm ưu đãi vào hóa đơn
            counpon.put("QUANTITY", input.getQuantity() - 1);

        reference.document(input.getIdCoupon())
                .update(counpon);
    }

    public void getListCoupon(Double orderPrice, Date date, callBackGetList callBackGetList) {
        reference.whereLessThanOrEqualTo("MIN_ORDER", orderPrice)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getDouble("QUANTITY") > 0
                                        && date.compareTo(document.getDate("END_DATE")) < 0 // date < END_DATE
                                        && document.getDate("START_DATE").compareTo(date) < 0) { // START_DATE < date
                                    listCoupon.add(new Coupon(document.getId(),
                                            document.getString("COUPON_NAME"),
                                            document.getDate("START_DATE"),
                                            document.getDate("END_DATE"),
                                            document.getDouble("DISCOUNT_VALUE"),
                                            document.getDouble("MIN_ORDER"),
                                            document.getDouble("QUANTITY"),
                                            document.getString("DESCRIPTION")
                                    ));
                                }
                            }

                            callBackGetList.loadDataSuccess(listCoupon);

                        } else {
                            callBackGetList.loadDataFail(new Exception("Không có ưu đãi phù hợp hoặc đã xảy ra lỗi!"));
                        }
                    }
                });
    }

    // Thêm ưu đãi vào hóa đơn
    public void addCouponToOrder(Coupon newCoupon, String idOrder) {
        CollectionReference referenceOrder = firebaseFirestore.collection("ORDER");

        Map<String, Object> coupon = new HashMap<>();
        coupon.put("ID_COUPON", newCoupon.getIdCoupon());

        // Thêm ưu đãi vào hóa đơn
        referenceOrder.document(idOrder)
                .update(coupon)
                .addOnSuccessListener(unused -> {
                    Log.d("AddCouponToOrder", "Thêm ưu đãi thành công!");

                    // Cập nhật số lượng của Coupon
                    updateQuantityOfCoupon(newCoupon, false);
                })
                .addOnFailureListener(e -> Log.e("AddCouponToOrder", "Lỗi khi thêm ưu đãi!", e));
    }

    // Xóa ưu đãi khỏi hóa đơn
    public void deleteCouponFromOrder(Coupon oldCoupon, String idOrder) {
        CollectionReference referenceOrder = firebaseFirestore.collection("ORDER");

        Map<String, Object> coupon = new HashMap<>();
        coupon.put("ID_COUPON", "");


        // Xóa ưu đãi khỏi hóa đơn
        referenceOrder.document(idOrder)
                .update(coupon)
                .addOnSuccessListener(unused -> {
                    Log.d("DeleteCouponFromOrder", "Xóa ưu đãi thành công!");

                    // Cập nhật số lượng của Coupon
                    updateQuantityOfCoupon(oldCoupon, true);

                })
                .addOnFailureListener(e -> Log.e("DeleteCouponFromOrder", "Lỗi khi xóa ưu đãi!", e));
    }

    // Cập nhật ưu đãi trong hóa đơn
    public void updateCouponToOrder(Coupon newCoupon, Coupon oldCoupon, String idOrder) {
        // Xóa đi ưu đãi cũ khỏi hóa đơn
        deleteCouponFromOrder(oldCoupon, idOrder);

        // Thêm ưu đãi mới vào hóa đơn
        addCouponToOrder(newCoupon, idOrder);
    }

    public interface callBackGetList {
        public void loadDataSuccess(List<Coupon> listCoupon);

        public void loadDataFail(Exception e);
    }
}
