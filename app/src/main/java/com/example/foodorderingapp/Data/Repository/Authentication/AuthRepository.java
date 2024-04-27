package com.example.foodorderingapp.Data.Repository.Authentication;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodorderingapp.Data.Model.Entity.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class AuthRepository {

    private Login loginInfo = new Login();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("LOGIN");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String verificationCode;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

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
                        callback.onLoginFailure(new Exception("No matching login info found or task failed"));
                    }
                }
            }
        });
    }

    public void checkPhoneNumber(String phone, AuthCallback callback) {
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

    public void sendOTP(String phone, boolean isResend, Activity activity) {
        // Xóa số 0 ở đầu
        phone = phone.replaceFirst("^0", "");
        // Thêm +84 vào đầu chuỗi
        phone = "+84" + phone;

        PhoneAuthOptions.Builder options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Xử lý khi xác minh hoàn thành
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Xử lý khi xác minh thất bại
                        Log.d("OTP STATUS", "Failed!");
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Xử lý khi mã OTP được gửi đi
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode = s; // Đã sửa tên biến thành "verificationCode"
                        resendingToken = forceResendingToken;

                        Log.d("OTP", verificationCode);
                    }
                });

        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(options.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(options.build());
        }
    }


    public String getLoginID() {
        return this.loginInfo.getId();
    }

    public String getLoginPhone() {
        return this.loginInfo.getPhone();
    }

    public String getLoginPassword() {
        return this.loginInfo.getPassword();
    }

    public interface AuthCallback {
        void onLoginSuccess(String data);

        void onLoginFailure(Exception e);
    }
}
