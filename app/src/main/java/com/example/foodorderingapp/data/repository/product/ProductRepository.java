package com.example.foodorderingapp.data.repository.product;

import android.util.Log;

import androidx.annotation.NonNull;


import com.example.foodorderingapp.data.model.entity.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
                        Log.d("firestore", "getProductById: " + product.toString());
                        callback.onProductLoaded(product);
//                        Log.d("firebase", "getProductById" + product.toString());
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
                    Log.d("firebase", "Product Detail");
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

    //Lấy thông tin chi tiết sản phẩm
    public void getProductDetails(String productId, ProductCallback callback) {
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
                                Product product = new Product();
                                product.setIdCategory(document.getString("ID_CATEGORY"));
                                product.setProductImage(document.getString("PRODUCT_IMAGE"));
                                product.setProductInfo(document.getString("PRODUCT_INFO"));
                                product.setProductName(document.getString("PRODUCT_NAME"));
                                product.setProductPrice(document.getLong("PRODUCT_PRICE").intValue());
                                product.setProductSize((Map<String, Map<String, Integer>>) document.get("SIZE"));
                                product.setTopping((List<String>) document.get("TOPPING"));

                                // Gọi callback để trả về thông tin chi tiết của sản phẩm
                                callback.onProductLoaded(product);
                                Log.d("firebase", "Product Detail" + product.toString());
                            } else {
                                // Document không tồn tại
                                callback.onProductLoadFailed("No such document");
                            }
                        } else {
                            // Đọc document thất bại
                            callback.onProductLoadFailed("Failed to get document: " + task.getException());
                        }
                    }
                });
    }
    // Method to update product quantity
    public void updateProductQuantity(String productId, String size, int quantityPurchased) {
        DocumentReference productRef = db.collection("PRODUCT").document(productId);
        productRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Get the current quantity of the specified size
                        Map<String, Object> sizeMap = (Map<String, Object>) document.get("SIZE");
                        if (sizeMap != null && sizeMap.containsKey(size)) {
                            Map<String, Object> sizeDetails = (Map<String, Object>) sizeMap.get(size);
                            if (sizeDetails != null && sizeDetails.containsKey("QUANTITY")) {
                                Long currentQuantity = (Long) sizeDetails.get("QUANTITY");
                                if (currentQuantity != null) {
                                    // Calculate new quantity
                                    long newQuantity = currentQuantity - quantityPurchased;

                                    // Update the quantity in Firestore
                                    sizeDetails.put("QUANTITY", newQuantity);
                                    sizeMap.put(size, sizeDetails);

                                    // Update the product document with new quantity
                                    productRef.update("SIZE", sizeMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Successfully updated
                                                    Log.d("Firestore", "Product quantity updated successfully.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Failed to update
                                                    Log.w("Firestore", "Error updating product quantity", e);
                                                }
                                            });
                                } else {
                                    Log.w("Firestore", "Current quantity is null.");
                                }
                            } else {
                                Log.w("Firestore", "Size details do not contain quantity.");
                            }
                        } else {
                            Log.w("Firestore", "Size map does not contain the specified size.");
                        }
                    } else {
                        Log.w("Firestore", "No such document.");
                    }
                } else {
                    Log.w("Firestore", "Task failed: ", task.getException());
                }
            }
        });
    }
}
