package com.example.javajoyadmin.data.repository.admin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.javajoyadmin.data.model.entity.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionProduct = firebaseFirestore.collection("PRODUCT");

    public void GetListProduct(GetListProductCallBack callBack) {
        collectionProduct.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> productList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Product product = new Product();

                        product.setId(document.getId());
                        product.setProductName(document.getString("PRODUCT_NAME"));
                        product.setProductImage(document.getString("PRODUCT_IMAGE"));
                        product.setProductPrice(document.getLong("PRODUCT_PRICE").intValue());
                        product.setProductSize((Map<String, Map<String, Integer>>) document.get("SIZE"));

                        productList.add(product);
                    }
                    callBack.loadDataSuccess(productList);
                    Log.d("firebase", "Product Detail");
                })
                .addOnFailureListener(e -> callBack.loadDataFail(new Exception("Quá trình tải dữ liệu đã xảy ra lỗi!")));
    }

    public void UpdateQuantity(UpdateQuantityCallBack callBack) {
        // Start a batch
        WriteBatch batch = firebaseFirestore.batch();

        // Get all products
        collectionProduct.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Get the current product data
                        Map<String, Object> product = document.getData();
                        Map<String, Map<String, Object>> sizes = (Map<String, Map<String, Object>>) product.get("SIZE");

                        // Update the quantity for each size
                        for (Map.Entry<String, Map<String, Object>> entry : sizes.entrySet()) {
                            entry.getValue().put("QUANTITY", 50);
                        }

                        // Add the updated product to the batch
                        batch.update(document.getReference(), "SIZE", sizes);
                    }

                    // Commit the batch
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                callBack.updateSuccess();
                            } else {
                                callBack.updateFail(new Exception("Đã xảy ra lỗi trong quá trình cập nhật số lượng!"));
                            }
                        }
                    });
                } else {
                    callBack.updateFail(new Exception("Đã xảy ra lỗi trong quá trình cập nhật số lượng!"));
                }
            }
        });
    }

    public interface GetListProductCallBack {

        void loadDataSuccess(List<Product> productList);

        void loadDataFail(Exception e);
    }

    public interface UpdateQuantityCallBack {

        void updateSuccess();

        void updateFail(Exception e);
    }
}
