package com.example.foodorderingapp.data.repository.accountmanagement.myorders;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.OrderDetail;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.UserPoint;
import com.example.foodorderingapp.data.repository.accountmanagement.PointRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersFeedbackRepository {
    private FirebaseFirestore db ;
    private ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
    private String orderStatus = "";

    public OrdersFeedbackRepository() {
        db = FirebaseFirestore.getInstance();
    }

    //Get danh sách sản phẩm đánh giá
    public void listFeedback(String orderId, orderItemCallback callback) {
        db.collection("ORDER")
                .document(orderId)
                .get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Chuyển đổi documentSnapshot thành đối tượng Order
                        Order order = documentSnapshot.toObject(Order.class);
                        if (order != null) {
                            // Lấy HashMap từ đối tượng Order
                            HashMap<String, OrderItem> itemHashMap = (HashMap<String, OrderItem>) order.getOrderItem();

                            // Chuyển HashMap thành List
                            List<OrderItem> orderItemList = new ArrayList<>(itemHashMap.values());

                            // Gọi callback để trả về danh sách OrderItem
                            callback.loadOrderItemsSuccess(orderItemList);
                        } else {
                            callback.loadOrderItemsError(new Exception("Order data is null"));
                        }
                    } else {
                        callback.loadOrderItemsError(new Exception("Document does not exist"));
                    }
                })
                .addOnFailureListener(exception -> {
                    // Gọi callback khi có lỗi
                    callback.loadOrderItemsError(exception);
                });
    }


    //Get order_status
    public void getOrderStatus(String orderId, orderStatusCallback callback) {
        Log.d(TAG, "orderId: " + orderId);

        // Sử dụng document thay vì whereEqualTo để truy vấn một tài liệu bằng ID
        db.collection("ORDER").document(orderId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // Sử dụng DocumentSnapshot
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            DocumentSnapshot document = task.getResult(); // Lấy DocumentSnapshot từ task
                            String orderStatus = document.getString("STATUS"); // Lấy giá trị từ trường STATUS

                            if (orderStatus != null) {
                                callback.loadOrderStatusSuccess(orderStatus); // Gọi callback khi thành công
                            } else {
                                callback.loadOrderStatusError(new Exception("Status not found")); // Xử lý lỗi nếu không có status
                            }
                        } else {
                            callback.loadOrderStatusError(task.getException()); // Xử lý lỗi nếu task không thành công
                        }
                    }
                });
    }

    public void getUserIdByOrderId(String orderID, userIDCallback callback){
        db.collection("ORDER")
                .document(orderID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().exists()){
                            DocumentSnapshot document = task.getResult();
                            String userID = document.getString("ID_USER");
                            if(userID != null){
                                callback.loadUserIDSuccess(userID);
                            }else{
                                callback.loadUserIDError(new Exception("User id not found"));
                            }
                        }else {
                            callback.loadUserIDError(task.getException());
                        }
                    }
                });

    }

    public void checkExistComment(String userID, String productID, checkCommentCallback callback){
        db.collection("COMMENT")
                .whereEqualTo("ID_PRODUCT", productID)
                .whereEqualTo("ID_USER", userID)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            callback.checkCommentSuccess(1);
                        }else{
                            callback.checkCommentError(new Exception("Error check comment"));
                        }
                    }
                });
    }
    public void addComment(Comment commentGet, commentCallback callback) {
        CollectionReference reference = db.collection("COMMENT");
        Date date = new Date();

//        Map<String, Object> commentData = new HashMap<>();
//        commentData.put("CONTENT", comment.getContent());
//        commentData.put("POINT", comment.getRatingBar());
//        commentData.put("CM_DATE", comment.getDate());
//        commentData.put("ID_PRODUCT", comment.getIdProduct());
//        commentData.put("ID_USER", comment.getIdUser());
//        commentData.put("USER_NAME", comment.getNameUser());

        Comment comment = new Comment(
            commentGet.getIdProduct(),
            commentGet.getIdUser(),
            commentGet.getNameUser(),
            commentGet.getRatingBar(),
            commentGet.getContent(),
            commentGet.getDate()
        );

        // String userId, int point, Date pointDate) {

//        db.collection("COMMENT")
//                .add(commentData)
//                .addOnSuccessListener(documentReference -> {
//                    // Thêm bình luận thành công
//                    Log.d("AddComment", "Bình luận đã được thêm vào bộ sưu tập COMMENTS với ID: " + documentReference.getId());
//                })
//                .addOnFailureListener(e -> {
//                    // Lỗi khi thêm bình luận vào bộ sưu tập COMMENTS
//                    Log.e("AddComment", "Lỗi khi thêm bình luận vào bộ sưu tập COMMENTS", e);
//                });
        db.collection("COMMENT").add(comment)
                .addOnSuccessListener(documentReference -> {
                    callback.loadCommentSuccess(comment);
                    PointRepository pointRepository = new PointRepository();
                    pointRepository.AddPoint(new UserPoint(commentGet.getIdUser(), 200, new Date()));
                })
                .addOnFailureListener(e -> callback.loadCommentError(new Exception("Add comment Error")));
    }

    public void updateComment(String commentId, Comment comment, commentCallback callback) {
        Map<String, Object> newCommentData = new HashMap<>();
        newCommentData.put("CONTENT", comment.getContent()); // Cập nhật CONTENT
        newCommentData.put("POINT", comment.getRatingBar()); // Cập nhật POINT
        newCommentData.put("CM_DATE", comment.getDate()); // Cập nhật CM_DATE

        db.collection("COMMENT")
                .document(commentId)
                .update(newCommentData)
                .addOnSuccessListener(aVoid -> {
                    // Sửa comment thành công
                    callback.loadCommentSuccess(comment);
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi sửa comment
                    callback.loadCommentError(new Exception("Error: update comment"));
                });
    }

    public void deleteComment(String commentID) {
        db.collection("COMMENT")
                .document(commentID)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Xóa comment thành công
                    Log.d("DeleteComment", "Bình luận đã được xóa thành công");
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi xóa comment
                    Log.e("DeleteComment", "Lỗi khi xóa bình luận", e);
                });
    }

    public void getCommentByProductIDUserID(String productID, String userID, commentCallback callback){
        db.collection("COMMENT")
                .whereEqualTo("ID_PRODUCT", productID)
                .whereEqualTo("ID_USER", userID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        Comment comment = documentSnapshot.toObject(Comment.class);
                        callback.loadCommentSuccess(comment);
                    }
                }).addOnFailureListener(e -> {
                    Log.e("Error load comment in firestore:", "Comment not found");
                    callback.loadCommentError(new Exception("Comment not found"));
                });
    }

    public void getCommentsByProductID(String productID, commentListCallback callback) {
        db.collection("COMMENT")
                .whereEqualTo("ID_PRODUCT", productID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult(); // Sử dụng QuerySnapshot thay vì QueryDocumentSnapshot

                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                List<Comment> listComment = new ArrayList<>();
                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                    Comment comment = documentSnapshot.toObject(Comment.class);
                                    listComment.add(comment);
//                                    listComment.add(new Comment(documentSnapshot.getString("ID_PRODUCT"),
//                                            documentSnapshot.getString("USER_ID"),
//                                            documentSnapshot.getString("USER_NAME"),
//                                            Float.parseFloat(String.valueOf(documentSnapshot.getDouble("POINT"))),
//                                            documentSnapshot.getString("CONTENT"),
//                                            documentSnapshot.getDate("CM_DATE")));
                                }
                                // Xử lý danh sách comment ở đây
                                for (Comment comment : listComment) {
//                                    Log.d("CommentInfo", "ID_PRODUCT: " + comment.getIdProduct());
//                                    Log.d("CommentInfo", "CONTENT: " + comment.getContent());
//                                    Log.d("CommentInfo", "POINT: " + comment.getRatingBar());
//                                    Log.d("CommentInfo", "CM_DATE: " + comment.getDate());
//                                    Log.d("CommentInfo", "USER_NAME: " + comment.getNameUser());
//                                    Log.d("CommentInfo", "USER_ID: " + comment.getIdUser());
                                }
                                callback.loadListCommentSuccess(listComment);
                            } else {
                                Log.d("COMMENT", "Danh sách comment trống"); // Thông báo nếu danh sách comment rỗng
                                callback.loadlistCommentError(new Exception("Error load list comment"));
                            }
                        } else {
                            Log.d("COMMENT", "Lỗi khi lấy danh sách"); // Thông báo khi có lỗi xảy ra trong quá trình lấy danh sách comment
                            callback.loadlistCommentError(new Exception("Error load list comment"));
                        }
                    }
                });
    }

    public void calculateProductAveragePoint(String productId, averagePointCallback callback){
        db.collection("COMMENT")
                .whereEqualTo("ID_PRODUCT", productId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult(); // Sử dụng QuerySnapshot thay vì QueryDocumentSnapshot

                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                int totalPoint = 0, size = 0;
                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                    Comment comment = documentSnapshot.toObject(Comment.class);
                                    totalPoint += comment.getRatingBar();
                                    size++;
                                }
                                if(size > 0){
                                    callback.onLoad(totalPoint * 1.0 / size);
                                }
                                else
                                    callback.onLoad(0.0);
                            } else {
                                Log.d("COMMENT", "Danh sách comment trống"); // Thông báo nếu danh sách comment rỗng
                                callback.onError(new Exception("Error load list comment"));
                            }
                        } else {
                            Log.d("COMMENT", "Lỗi khi lấy danh sách"); // Thông báo khi có lỗi xảy ra trong quá trình lấy danh sách comment
                            callback.onError(new Exception("Error load list comment"));
                        }
                    }
                });
    }

    public void checkExistComments(String productID, checkCommentsCallback callback) {
        db.collection("COMMENT")
                .whereEqualTo("ID_PRODUCT", productID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            int commentSize = queryDocumentSnapshots.size();
                            callback.loadCommentsSuccess(commentSize);
                        } else {
                            callback.loadCommentsSuccess(0); // No comments found
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.loadCommentError(e);
                    }
                });
    }




    public ArrayList<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public String getStrOrderStatus() {
        return orderStatus;
    }

    public interface orderItemCallback{
        void loadOrderItemsSuccess(List<OrderItem> orderItems);
        void loadOrderItemsError(Exception e);
    }

    public interface checkCommentCallback{
        void checkCommentSuccess(int isExist);
        void checkCommentError(Exception e);
    }
    public interface commentCallback{
        void loadCommentSuccess(Comment comment);
        void loadCommentError(Exception e);
    }

    public interface commentListCallback{
        void loadListCommentSuccess(List<Comment> listComment);
        void loadlistCommentError(Exception e);
    }

    public interface orderStatusCallback{
        void loadOrderStatusSuccess(String status);
        void loadOrderStatusError(Exception e);
    }

    public interface userIDCallback{
        void loadUserIDSuccess(String userID);
        void loadUserIDError(Exception e);
    }
    public interface checkCommentsCallback{
        void loadCommentsSuccess(int isExist);
        void loadCommentError(Exception e);
    }

    public interface averagePointCallback{
        void onLoad(double point);
        void onError(Exception e);
    }
}
