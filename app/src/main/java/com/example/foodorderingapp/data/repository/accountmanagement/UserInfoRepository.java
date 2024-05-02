package com.example.foodorderingapp.data.repository.accountmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.google.android.gms.tasks.OnCompleteListener;
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
                            userInfo = new User(document.getString("USER_NAME"), document.getString("PHONE"),userId, document.getString("ID_LOGIN"));

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

    public void getListAddress (String userId, userAddressesCallback callback){
        db.collection("USER_ADDRESS")
                .whereEqualTo("ID_USER", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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

    //Update user: name and phone
    public void updateNamePhone(String userId, User userInfo){
        Map<String, Object> newUserData = new HashMap<>();
        newUserData.put("NAME", userInfo.getUserName());
        newUserData.put("PHONE", userInfo.getPhone());

        db.collection("USER")
                .document(userId)
                .update(newUserData)
                .addOnSuccessListener(aVoid -> {
                    // Sửa comment thành công
                    Log.d("UpdateUserSuccess", "Update user success!");
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi sửa comment
                    Log.e("UpdateUserError", "Error update user: ", e);
                });

    }
    //Update user: password

    //add address
    public void addAddress(UserAddress userAddress){
        HashMap<String, Object> address = new HashMap<>();
        address.put("ADDRESS_DETAIL", userAddress.getAddressDetail());
        address.put("CITY", userAddress.getCity());
        address.put("DISTRICT", userAddress.getDistrict());
        address.put("WARD",userAddress.getWard());
        address.put("ID_USER", userAddress.getIdUser());
        address.put("RECIPIENT_NAME", userAddress.getRecipientName());
        address.put("RECIPIENT_PHONE", userAddress.getRecipientPhone());

        db.collection("USER")
                .add(address)
                .addOnSuccessListener(aVoid -> {
                    // Sửa comment thành công
                    Log.d("AddUserSuccess", "Add user success!");
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi sửa comment
                    Log.e("AddUserError", "Error add user: ", e);
                });

    }
    //update address
    public void updateAdress(String addressId, UserAddress userAddress){
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
                    // Sửa comment thành công
                    Log.d("UpdateUserAddressSuccess", "Update user address success!");
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi sửa comment
                    Log.e("UpdateUserAddressError", "Error update user address: ", e);
                });
    }
    //delete address
    public void deleteAdress(String addressId){
        db.collection("USER_ADDRESS")
                .document(addressId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Xóa comment thành công
                    Log.d("DeleteUserAddressSuccess", "Delete user address success!");
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi xóa comment
                    Log.e("DeleteUserAddressError", "Error delete user address:", e);
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
}
