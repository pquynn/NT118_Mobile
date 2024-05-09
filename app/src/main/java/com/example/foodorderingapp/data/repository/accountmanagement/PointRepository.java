package com.example.foodorderingapp.data.repository.accountmanagement;

import android.util.Log;

import com.example.foodorderingapp.data.model.entity.UserPoint;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class PointRepository {
    private FirebaseFirestore db ;
    private ArrayList<UserPoint> listUserPoint = new ArrayList<>();
    private int totalPoint = 0;

    public PointRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getListPoint(String userId, userPointCallback callback){
        db.collection("USER_POINT")
                .whereEqualTo("ID_USER", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(DocumentSnapshot document : queryDocumentSnapshots){
                                UserPoint point = document.toObject(UserPoint.class);
                                listUserPoint.add(point);
                                int i = 1;
                                Log.d("USER POINT", "User Point " + i +": "+ point.getPointDate()  + ", " + point.getPoint());
                                i++;
                            }
                            callback.loadUserPointSuccess(listUserPoint);
                        }callback.loadUserPointError(new Exception("Error: user point not found"));
                    }
                });
    }

    public void getTotalPoint(String userId, userTotalPointCallback callback){
        db.collection("USER_POINT")
                .whereEqualTo("ID_USER", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : queryDocumentSnapshots) {
                                UserPoint point = document.toObject(UserPoint.class);
                                    totalPoint += point.getPoint();
                            }
                            Log.d("TotalPoint: ", totalPoint + "");
                            callback.loadTotalPointSuccess(totalPoint);
                        }
                        callback.loadTotalPointError(new Exception("Error: user point not found"));
                    }
                });
    }
    public void AddPoint(UserPoint userPoint){
        CollectionReference collectionReference = db.collection("USER_POINT");
        collectionReference.add(userPoint)
                .addOnSuccessListener(aVoid -> {
                    // Sửa comment thành công
                    Log.d("AddPointSuccess", "Add point success!");
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi sửa comment
                    Log.e("AddPointError", "Error add point: ", e);
                });
    }
    public interface userPointCallback {
        void loadUserPointSuccess(ArrayList<UserPoint> userPoints);
        void loadUserPointError(Exception e);
    }

    public interface userTotalPointCallback {
        void loadTotalPointSuccess(int totalPoint);
        void loadTotalPointError(Exception e);
    }
}
