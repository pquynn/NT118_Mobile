package com.example.foodorderingapp.data.repository.authentication;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.data.model.entity.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthRepository {

    private Login loginInfo = new Login();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("LOGIN");
    private CollectionReference reference_user = firebaseFirestore.collection("USER");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String verificationCode;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    public void getUserID(String phone, AuthCallbackGetUserID getUserID) {
        reference_user.whereEqualTo("PHONE", phone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                            if (getUserID != null){
                                getUserID.onSuccess(document.getId());
                            }
                        } else {
                            // Không tìm số điện thoại
                            if (getUserID != null) {
                                getUserID.onFailure(new Exception("No matching user info found or task failed!"));
                            }
                        }
                    }
                });
    }

    public void signIn(String phone, String password, AuthCallback callback) {
        reference.whereEqualTo("PHONE", phone).whereEqualTo("PASSWORD", password).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                    loginInfo = new Login(document.getId(), document.getString("PHONE"), document.getString("PASSWORD"));

                    // Dữ liệu trùng khớp, gọi callback với ID của đăng nhập
                    if (callback != null) {
                        callback.onLoginSuccess(document.getId());
                    }
                } else {
                    // Không tìm thấy hoặc có lỗi xảy ra
                    if (callback != null) {
                        callback.onLoginFailure(new Exception("No matching login info found or task failed!"));
                    }
                }
            }
        });
    }

    public void checkPhoneNumber(String phone, AuthCallback callback) {
        Log.d("PHONE", phone);
        reference.whereEqualTo("PHONE", phone).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    if (callback != null) {
                        callback.onLoginSuccess("1");
                    }
                } else {
                    // Không tìm thấy hoặc có lỗi xảy ra
                    if (callback != null) {
                        callback.onLoginFailure(new Exception("No matching login info found or task failed"));
                    }
                }
            }
        });
    }

    public void sendOTP(String phone, boolean isResend, Activity activity, AuthCallbackOTP callback) {
        // Xóa số 0 ở đầu
        phone = phone.replaceFirst("^0", "");
        // Thêm +84 vào đầu chuỗi
        phone = "+84" + phone;

        PhoneAuthOptions.Builder options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Xử lý khi xác minh hoàn thành
                        Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Xử lý khi xác minh thất bại
                        Log.d("OTP STATUS", "Failed!");
                        callback.onFailure(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Xử lý khi mã OTP được gửi đi
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode = s; // Đã sửa tên biến thành "verificationCode"
                        resendingToken = forceResendingToken;

                        Log.d("OTP", verificationCode);
                        callback.onSuccess();
                    }
                });

        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(options.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(options.build());
        }
    }

    public void changePassword(String phone, String password, AuthCallbackUpdatePassword callBack) {
        // Tìm các tài khoản trong LOGIN có trường PHONE trùng khớp với số điện thoại được cung cấp
        reference.whereEqualTo("PHONE", phone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                            reference.document(document.getId()).update("PASSWORD", password)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Password đã được cập nhật thành công
                                            if (callBack != null) {
                                                callBack.onUpdateSuccess();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xảy ra lỗi khi cố gắng cập nhật password
                                            if (callBack != null) {
                                                callBack.onUpdateFailure(new Exception("No matching login info found or task failed"));
                                            }
                                        }
                                    });
                        } else {
                            // Không tìm số điện thoại
                        }
                    }
                });
    }

    public void changePhone(String oldPhone, String newPhone, AuthCallbackUpdatePassword callBack) {
        // Tìm các tài khoản trong LOGIN có trường PHONE trùng khớp với số điện thoại được cung cấp
        reference.whereEqualTo("PHONE", oldPhone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                            reference.document(document.getId()).update("PHONE", newPhone)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Số điện thoại đã được cập nhật thành công
                                            if (callBack != null) {
                                                callBack.onUpdateSuccess();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xảy ra lỗi khi cố gắng cập nhật số điện thoại
                                            if (callBack != null) {
                                                callBack.onUpdateFailure(new Exception("No matching login info found or task failed"));
                                            }
                                        }
                                    });
                        } else {
                            // Không tìm số điện thoại
                        }
                    }
                });

        // Tìm các tài khoản trong USER có trường PHONE trùng khớp với số điện thoại được cung cấp
        reference_user.whereEqualTo("PHONE", oldPhone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                            reference.document(document.getId()).update("PHONE", newPhone)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Số điện thoại đã được cập nhật thành công
                                            if (callBack != null) {
                                                callBack.onUpdateSuccess();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xảy ra lỗi khi cố gắng cập nhật số điện thoại
                                            if (callBack != null) {
                                                callBack.onUpdateFailure(new Exception("No matching login info found or task failed"));
                                            }
                                        }
                                    });
                        } else {
                            // Không tìm số điện thoại
                        }
                    }
                });
    }

    public void createUser(String name, String phone, String password, Context context) {
        Map<String, Object> userLoginData = new HashMap<>();
        userLoginData.put("PHONE", phone); // Điền thông tin số điện thoại
        userLoginData.put("PASSWORD", password); // Điền thông tin mật khẩu

        Map<String, Object> userData = new HashMap<>();
        userData.put("ID_LOGIN", ""); // Cập nhật sau khi thêm người dùng vào collection LOGIN
        userData.put("PHONE", phone);
        userData.put("NAME", name);
        userData.put("ROLE", 0);
        userData.put("GENDER", ""); // Cập nhật sau

        reference.add(userLoginData)
                .addOnSuccessListener(documentReference -> {
                    String loginDocumentId = documentReference.getId();
                    // 3. Cập nhật ID_LOGIN trong thông tin người dùng
                    userData.put("ID_LOGIN", loginDocumentId);

                    // 4. Thêm thông tin người dùng vào bộ sưu tập USER
                    reference_user.add(userData)
                            .addOnSuccessListener(userDocumentReference -> {
                                // Thêm người dùng thành công
                                // Xử lý đăng ký
                                Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Lỗi khi thêm người dùng vào bộ sưu tập USER
                                Log.e("AddUser", "Lỗi khi thêm người dùng vào bộ sưu tập USER", e);
                            });
                })
                .addOnFailureListener(e -> {
                    // Lỗi khi thêm thông tin đăng nhập vào bộ sưu tập LOGIN
                    Log.e("AddUser", "Lỗi khi thêm thông tin đăng nhập vào bộ sưu tập LOGIN", e);
                });
    }


    public String getVerificationCode() {
        return verificationCode;
    }

//    public String getLoginID() {
//        return this.loginInfo.getId();
//    }
//
//    public String getLoginPhone() {
//        return this.loginInfo.getPhone();
//    }
//
//    public String getLoginPassword() {
//        return this.loginInfo.getPassword();
//    }

    public interface AuthCallback {
        void onLoginSuccess(String data);

        void onLoginFailure(Exception e);
    }

    public interface AuthCallbackOTP {
        void onSuccess();

        void onFailure(Exception e);
    }

    public interface AuthCallbackUpdatePassword {
        void onUpdateSuccess();

        void onUpdateFailure(Exception e);
    }

    public interface AuthCallbackGetUserID {
        void onSuccess(String userID);

        void onFailure(Exception e);
    }
}
