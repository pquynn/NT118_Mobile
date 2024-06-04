package com.example.javajoyadmin.data.repository.accountmanagement;

import androidx.annotation.NonNull;

import com.example.javajoyadmin.data.model.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserInfoRepository {
    private FirebaseFirestore db ;

    public UserInfoRepository() {
        db = FirebaseFirestore.getInstance();
    }

    private User userInfo;

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

    }

    public interface userInfoCallback{
        void loadUserInfoSuccess(User userInfo);
        void loadUserInfoError(Exception e);
    }

    public interface updateNameCallback{
        void updateNameSuccess();
        void updateNameError(Exception e);
    }
}
