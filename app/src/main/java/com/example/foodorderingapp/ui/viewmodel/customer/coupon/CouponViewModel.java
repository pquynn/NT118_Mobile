package com.example.foodorderingapp.ui.viewmodel.customer.coupon;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.repository.coupon.CouponRepository;

import java.util.Date;
import java.util.List;

public class CouponViewModel extends ViewModel {
    private String orderId;
    private int orderPrice;
    private ProgressDialog progressDialog; // Declare ProgressDialog
    private Context context; // Context variable

    private MutableLiveData<List<Coupon>> couponListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSelectedLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> discountValueLiveData = new MutableLiveData<>();


    private CouponRepository couponRepository;

    public CouponViewModel(String orderId, int orderPrice, Context context){
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.context = context;
        isSelectedLiveData.setValue(false);
        discountValueLiveData.setValue(0);
        couponRepository = new CouponRepository();
    }

    public MutableLiveData<List<Coupon>> getCouponListMutableLiveData() {
        loadCouponList(orderPrice);
        return couponListMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsSelectedLiveData() {
        return isSelectedLiveData;
    }

    public MutableLiveData<Integer> getDiscountValueLiveData() {
        return discountValueLiveData;
    }

    public void loadCouponList(int orderPrice){
        couponRepository.getListCoupon((double)orderPrice, new Date(), new CouponRepository.callBackGetList() {
            @Override
            public void loadDataSuccess(List<Coupon> listCoupon) {
                couponListMutableLiveData.setValue(listCoupon);
            }

            @Override
            public void loadDataFail(Exception e) {

            }
        });
    }

    // Method to dismiss progress dialog
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
