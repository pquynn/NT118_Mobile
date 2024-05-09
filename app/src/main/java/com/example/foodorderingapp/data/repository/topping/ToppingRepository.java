package com.example.foodorderingapp.data.repository.topping;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.Topping;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ToppingRepository implements IToppingRepository{
    private FirebaseFirestore db;
    public ToppingRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getToppingDetails(String toppingId, ToppingCallBack callback) {
        db.collection("TOPPING")
                .document(toppingId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Topping topping = new Topping();
                                topping.setId(toppingId);
                                topping.setNameTopping(document.getString("TOPPING_NAME"));
                                topping.setPriceTopping(String.valueOf(document.getLong("TOPPING_PRICE").intValue()));
                                callback.onToppingLoaded(topping);
                            } else {
                                callback.onToppingLoadFailed("No such document");
                            }
                        } else {
                            callback.onToppingLoadFailed("Failed to get document: " + task.getException());
                        }
                    }
                });
    }

}
