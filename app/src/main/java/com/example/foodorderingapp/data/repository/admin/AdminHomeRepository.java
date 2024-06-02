package com.example.foodorderingapp.data.repository.admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;

public class AdminHomeRepository {

    private int totalOrder;
    private double totalRevenue, totalRefund;
    private int[] dataYear = new int[12];
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
        reference.whereGreaterThanOrEqualTo("CREATE_ON", date).whereLessThan("CREATE_ON", nextDate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String status;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        status = document.getString("STATUS");

                        if (status != null && (status.equals("Đang giao") || status.equals("Đã giao") || status.equals("Hoàn tiền"))) {
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

    public void getDataYear(Date date, adminHomeLineChartDataCallback callback) {
        // Sử dụng Calendar để lấy năm từ biến Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        // Tạo đối tượng Date cho ngày đầu tiên của năm
        Date startOfYear = new Date(year - 1900, 0, 1, 0, 0, 0); // Đặt giờ, phút, giây, ngày, tháng, năm đầu tiên của date

        // Tạo đối tượng Date cho ngày cuối của năm
        Date endOfYear = new Date(year - 1900, 11, 31, 23, 59, 59); // Đặt giờ, phút, giây, ngày, tháng, năm cuối cùng của date

        // VD: date = 10/5/2024
        // startOfYear = 1/1/2024 0:0:0
        // endOfYear = 31/12/2024 23:59:59

        // Truy vấn các dòng dữ liệu trong collection "ORDER" có "CREATE_ON"
        reference.whereGreaterThanOrEqualTo("CREATE_ON", startOfYear)
                .whereLessThan("CREATE_ON", endOfYear)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataYear = new int[12]; // Reset giá trị của dataYear
                            int month = 0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Kiểm tra nếu "STATUS" là "Đã giao"
                                if (document.getString("STATUS").equals("Đã giao")) {
                                    month = document.getDate("CREATE_ON").getMonth();
                                    dataYear[month]++;
                                }
                            }
                            callback.loadDataSuccess(dataYear);

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

    public interface adminHomeLineChartDataCallback {

        void loadDataSuccess(int[] data);

        void loadDataFail(Exception e);
    }
}
