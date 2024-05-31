package com.example.foodorderingapp.ui.viewpageradapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderCancelled;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderCompleted;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderDelivering;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderInProgress;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.myordersfragment.MyOrderRefunded;

public class MyOrdersViewPagerAdapter extends FragmentStateAdapter {

    private String userId;
    public MyOrdersViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String userId) {
        super(fragmentActivity);
        this.userId = userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment =  new MyOrderInProgress();
                break;
            case 1:
                fragment = new MyOrderDelivering();
                break;
            case 2:
                fragment =  new MyOrderCompleted();
                break;
            case 3:
                fragment =  new MyOrderCancelled();
                break;
            case 4:
                fragment = new MyOrderRefunded();
                break;
            default:
                return null;

        }

        Bundle bundle = new Bundle();
        bundle.putString("user_id", userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
