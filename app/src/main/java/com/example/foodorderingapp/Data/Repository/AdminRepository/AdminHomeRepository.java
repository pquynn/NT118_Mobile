package com.example.foodorderingapp.Data.Repository.AdminRepository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminHomeRepository {

    private int totalOrder;
    private double totalRevenue, totalRefund;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("ORDER");

    public void getData(Date date, adminHomeCallback callback) {
        this.totalOrder = 0;
        this.totalRevenue = 0;
        this.totalRefund = 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        date = cal.getTime(); // Ngày được chọn

        // Tăng ngày lên một
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date nextDate = cal.getTime(); // Ngày hôm sau

        // Truy vấn các dòng dữ liệu trong collection "ORDER" có "CREATE_ON"
        reference.whereGreaterThanOrEqualTo("CREATE_ON", date)
                .whereLessThan("CREATE_ON", nextDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String status;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                status = document.getString("STATUS");

                                if (status != null && (status.equals("Đang giao") || status.equals("Đã giao") || status.equals("Hoàn tiền"))) {
                                    // Tổng số đơn trong ngày bao gồm tất cả các trạng thái của đơn hàng có trong statusList
                                    totalOrder++;

                                    // Kiểm tra nếu "STATUS" là "Đã giao"
                                    if (status.equals("Đã giao")) {
                                        // Tổng doanh thu
                                        totalRevenue += document.getDouble("TOTAL_PRICE");
                                    }
                                    // Kiểm tra nếu "STATUS" là "Hoàn tiền"
                                    else if (status.equals("Hoàn tiền")) {
                                        // Tổng tiền hoàn lại
                                        totalRefund += document.getDouble("TOTAL_PRICE");
                                    }
                                }
                            }

                            callback.loadDataSuccess(totalOrder, totalRevenue, totalRefund);

                        } else {
                            callback.loadDataFail(new Exception("Không có dữ liệu trong ngày được tìm kiếm hoặc đã xảy ra lỗi!"));
                        }
                    }
                });
    }

    public interface adminHomeCallback {

        void loadDataSuccess(int order, double revenue, double refund);

        void loadDataFail(Exception e);
    }
}
