package com.example.foodorderingapp.Data.Repository.Product;

import android.util.Log;

import androidx.annotation.NonNull;


import com.example.foodorderingapp.Data.Model.Entity.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductRepository implements IProductRepository {
    private FirebaseFirestore db;

    public ProductRepository() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void getProductById(String productId, ProductCallback callback) {
        DocumentReference productRef = db.collection("PRODUCT").document(productId);
        ((DocumentReference) productRef).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Product product = documentSnapshot.toObject(Product.class);
                        Log.d("firebase", "getProductById" + product.toString());
                        callback.onProductLoaded(product);
                    } else {
                        callback.onProductLoadFailed("Product not found");
                    }
                })
                .addOnFailureListener(e -> callback.onProductLoadFailed(e.getMessage()));
    }

    //Lấy danh sách sản phẩm theo categoryID
    public void getProductsByCategory(String categoryId, ProductListCallback callBack) {
        db.collection("PRODUCT")
                .whereEqualTo("ID_CATEGORY", categoryId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> productList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId();
                        // Lấy dữ liệu từ document
                        String idCategory = document.getString("ID_CATEGORY");
                        String productName = document.getString("PRODUCT_NAME");
                        String productImage = document.getString("PRODUCT_IMAGE");
                        int productPrice = document.getLong("PRODUCT_PRICE").intValue();

                        // Tạo đối tượng Product
                        Product product = new Product(id, idCategory, productImage, productName, productPrice);

                        productList.add(product);
                    }
                    callBack.onProductListLoaded(productList);
                })
                .addOnFailureListener(e -> {
                    callBack.onProductListLoadFailed(e.getMessage());
                });
    }

    // Lấy danh sách sản phẩm cho tìm kiếm sản phẩm gồm id, ảnh, tên, giá
    public void getAllProducts(ProductListCallback callback) {
        db.collection("PRODUCT")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> productList1 = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        //Lấy id
                        String id = document.getId();
                        // Lấy dữ liệu từ document
                        String idCategory = document.getString("ID_CATEGORY");
                        String productName = document.getString("PRODUCT_NAME");
                        String productImage = document.getString("PRODUCT_IMAGE");
                        int productPrice = document.getLong("PRODUCT_PRICE").intValue();

                        // Tạo đối tượng Product
                        Product product1 = new Product(id, idCategory, productImage, productName, productPrice);
                        productList1.add(product1);
                    }
                    callback.onProductListLoaded(productList1);
                })
                .addOnFailureListener(e -> callback.onProductListLoadFailed(e.getMessage()));
    }

    // Lấy thông tin chi tiết sản phẩm là bánh
    public void getProductDetailsCake(String productId, ProductDetailCakeCallback callback) {
        db.collection("PRODUCT")
                .document(productId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Lấy thông tin chi tiết của sản phẩm từ document
                                String productName = document.getString("PRODUCT_NAME");
                                String productImage = document.getString("PRODUCT_IMAGE");
                                int productPrice = document.getLong("PRODUCT_PRICE").intValue();
                                String productInfo = document.getString("PRODUCT_INFO");

                                Log.d("Cake Product Details", "Name: " + productName + ", Image: " + productImage + ", Price: " + productPrice + ", Info: " + productInfo);

                                // Gọi callback để trả về thông tin chi tiết của sản phẩm
                                callback.onProductDetailLoaded(productName, productImage, productPrice, productInfo);
                            } else {
                                // Document không tồn tại
                                callback.onProductDetailLoadFailed("No such document");
                            }
                        } else {
                            // Đọc document thất bại
                            callback.onProductDetailLoadFailed("Failed to get document: " + task.getException());
                        }
                    }
                });
    }

    //   // lâý thông tin chi tiết sản phẩm là nước uống
    public void getProductDetailsDrink(String productId, ProductDetailCallback callback) {
        db.collection("PRODUCT")
                .document(productId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Lấy thông tin chi tiết của sản phẩm từ document
                                String productImage = document.getString("PRODUCT_IMAGE");
                                String productName = document.getString("PRODUCT_NAME");
                                String productInfo = document.getString("PRODUCT_INFO");
                                int productPrice = document.getLong("PRODUCT_PRICE").intValue();
                                // Lấy size
                                Map<String, Map<String, Object>> sizeMap = (Map<String, Map<String, Object>>) document.get("SIZE");
                                // Lấy topping
                                List<String> toppingList = (List<String>) document.get("TOPPING");

                                Log.d("Drink Product Details", "Name: " + productName + ", Image: " + productImage + ", Price: " + productPrice + ", Info: " + productInfo + ", Sizes: " + sizeMap + ", Toppings: " + toppingList);

                                // Gọi callback để trả về thông tin chi tiết của sản phẩm
                                callback.onProductDetailLoaded(productName, productImage, productInfo, productPrice, sizeMap, toppingList);
                            } else {
                                // Document không tồn tại
                                Log.d("Product Details", "No such document");
                                callback.onProductDetailLoadFailed("No such document");
                            }
                        } else {
                            // Đọc document thất bại
                            callback.onProductDetailLoadFailed("Failed to get document: " + task.getException());
                        }
                    }
                });
    }

}
