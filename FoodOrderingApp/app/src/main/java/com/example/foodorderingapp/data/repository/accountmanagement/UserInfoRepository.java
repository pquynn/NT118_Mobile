package com.example.foodorderingapp.data.repository.accountmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInfoRepository {
    //Model = User
    private FirebaseFirestore db ;

    public UserInfoRepository() {
        db = FirebaseFirestore.getInstance();
    }

    private User userInfo;
    private ArrayList<UserAddress> listUserAddress = new ArrayList<>();

    //Get user -> in view: AccountNavigationFragment, am_user_info
    public void getUserInfo(String userId, userInfoCallback callback){
        db.collection("USER").document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isComplete() && task.getResult() != null && task.getResult().exists()){
                            DocumentSnapshot document = task.getResult();
                            userInfo = document.toObject(User.class);

                            if(callback != null){
                                callback.loadUserInfoSuccess(userInfo);
                            }else{
                                callback.loadUserInfoError(new Exception("User not found"));
                            }
                        }else{
                            callback.loadUserInfoError(task.getException());
                        }
                    }
                });
    }

    public void getAddressById (String addressId, userAddressCallback callback){
        db.collection("USER_ADDRESS")
                .document(addressId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            UserAddress userAddress = documentSnapshot.toObject(UserAddress.class);
                            callback.loadUserAddressSuccess(userAddress);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.loadUsserAddressError(e);
                    }
                });

    }

    public void getListAddress (String userId, userAddressesCallback callback){
        db.collection("USER_ADDRESS")
                .whereEqualTo("ID_USER", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<UserAddress> listUserAddress = new ArrayList<>();
                        for(DocumentSnapshot document : queryDocumentSnapshots){
                            UserAddress userAddress = document.toObject(UserAddress.class);
                            if(userAddress != null){
                                listUserAddress.add(userAddress);
                                Log.d(TAG, "address: " +userAddress.getAllAddress());
                            }
                        }
                        callback.loadUserAddressesSuccess(listUserAddress);
                    }
                }).addOnFailureListener(e->{
                    callback.loadUsserAddressesError(e);
                });
    }
    // get the first address by user id
    public void getFirstAddress(String userId, userAddressCallback callback) {
        db.collection("USER_ADDRESS")
                .whereEqualTo("ID_USER", userId)
                .limit(1) // Limit the result to the first document
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Get the first document
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            UserAddress userAddress = documentSnapshot.toObject(UserAddress.class);
                            if (userAddress != null) {
                                callback.loadUserAddressSuccess(userAddress);
                            }
                        }
                        else {
                            //if user has no address exist
                            UserAddress userAddress = new UserAddress();
                            callback.loadUserAddressSuccess(userAddress);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.loadUsserAddressError(e);
                    }
                });
    }


    //Update user: name and phone
//    public void updateNamePhone(String userId, User userInfo){
//        Map<String, Object> newUserData = new HashMap<>();
//        newUserData.put("NAME", userInfo.getUserName());
//        newUserData.put("PHONE", userInfo.getPhone());
//
//        db.collection("USER")
//                .document(userId)
//                .update(newUserData)
//                .addOnSuccessListener(aVoid -> {
//                    // Sửa comment thành công
//                    Log.d("UpdateUserSuccess", "Update user success!");
//                })
//                .addOnFailureListener(e -> {
//                    // Lỗi khi sửa comment
//                    Log.e("UpdateUserError", "Error update user: ", e);
//                });
//
//    }
    //Update user: name
    public void updateUserName(String userId, String userName, updateNameCallback callback){
        db.collection("USER").document(userId)
                .update("USER_NAME", userName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.updateNameSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.updateNameError(e);
                    }
                });

        db.collection("COMMENT").whereEqualTo("ID_USER", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            document.getReference().update("USER_NAME", userName)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callback.updateNameError(e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.updateNameError(e);
                    }
                });

    }
    //Update user: password

    //add address
    public void addAddress(UserAddress userAddress, userAddressCallback callback){
        UserAddress address = new UserAddress(
                userAddress.getRecipientName(),
                userAddress.getAddressDetail(),
                userAddress.getCity(),
                userAddress.getDistrict(),
                userAddress.getWard(),
                userAddress.getRecipientPhone(),
                userAddress.getIdUser()
        );

        db.collection("USER_ADDRESS")
                .add(address)
                .addOnSuccessListener(aVoid -> {
                    callback.loadUserAddressSuccess(address);
                })
                .addOnFailureListener(e -> {
                    callback.loadUsserAddressError(e);
                });

    }
    //update address
    public void updateAddress(String addressId, UserAddress userAddress, userAddressCallback callback){
        Map<String, Object> newAddress = new HashMap<>();
        newAddress.put("ADDRESS_DETAIL", userAddress.getAddressDetail());
        newAddress.put("CITY", userAddress.getCity());
        newAddress.put("DISTRICT", userAddress.getDistrict());
        newAddress.put("WARD",userAddress.getWard());
        newAddress.put("RECIPIENT_NAME", userAddress.getRecipientName());
        newAddress.put("RECIPIENT_PHONE", userAddress.getRecipientPhone());

        db.collection("USER_ADDRESS")
                .document(addressId)
                .update(newAddress)
                .addOnSuccessListener(aVoid -> {
                    callback.loadUserAddressSuccess(userAddress);
                })
                .addOnFailureListener(e -> {
                    callback.loadUsserAddressError(e);
                });
    }
    //delete address
    public void deleteAdress(String addressId, deleteAddressCallback callback){
        db.collection("USER_ADDRESS")
                .document(addressId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Xóa comment thành công
                    callback.deleteAddressSuccess();
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi xóa comment
                    callback.deleteAddressError(e);
                });
    }
    public interface userInfoCallback{
        void loadUserInfoSuccess(User userInfo);
        void loadUserInfoError(Exception e);
    }

    public interface userAddressesCallback{
        void loadUserAddressesSuccess(ArrayList<UserAddress> listAddress);
        void loadUsserAddressesError(Exception e);
    }

    public interface userAddressCallback{
        void loadUserAddressSuccess(UserAddress userAddress);
        void loadUsserAddressError(Exception e);
    }

    public interface updateNameCallback{
        void updateNameSuccess();
        void updateNameError(Exception e);
    }

    public interface deleteAddressCallback{
        void deleteAddressSuccess();
        void deleteAddressError(Exception e);
    }

}
