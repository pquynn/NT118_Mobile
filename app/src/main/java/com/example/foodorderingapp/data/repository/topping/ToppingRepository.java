package com.example.foodorderingapp.data.repository.topping;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.Topping;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ToppingRepository implements IToppingRepository{
    private FirebaseFirestore db;
    public ToppingRepository() {
        db = FirebaseFirestore.getInstance();
    }

    //Lấy dữ liệu topping theo ID
    public void getToppingDetailsId(String documentId, ToppingCallBack callback) {
        db.collection("TOPPING")
                .document(documentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Topping topping = new Topping();
                                topping.setNameTopping(document.getString("TOPPING_NAME"));
                                topping.setPriceTopping(String.valueOf(document.getLong("TOPPING_PRICE").intValue()));
                                callback.onToppingLoaded(topping);
                                Log.d("firebase", "topping" + topping.toString());
                            } else {
                                callback.onToppingLoadFailed("No such document");
                            }
                        } else {
                            callback.onToppingLoadFailed("Failed to get document: " + task.getException());
                        }
                    }
                });
    }

    // lấy dữ liệu Topping theo tên
    public void getToppingDetails(String toppingName, ToppingCallBack callback) {
        db.collection("TOPPING")
                .whereEqualTo("TOPPING_NAME", toppingName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Topping topping = new Topping();
                                topping.setNameTopping(document.getString("TOPPING_NAME"));
                                topping.setPriceTopping(String.valueOf(document.getLong("TOPPING_PRICE").intValue()));
                                callback.onToppingLoaded(topping);
                                // Nếu bạn muốn lấy một topping duy nhất, bạn có thể break ở đây
                                Log.d("firebase", "topping" + topping.toString());
                            }
                        } else {
                            callback.onToppingLoadFailed("Failed to get toppings: " + task.getException());
                        }
                    }
                });
    }

}
