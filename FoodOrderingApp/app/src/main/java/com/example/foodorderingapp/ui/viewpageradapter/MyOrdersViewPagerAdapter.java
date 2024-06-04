package com.example.foodorderingapp.ui.viewpageradapter;

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


    public MyOrdersViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyOrderInProgress();
            case 1:
                return new MyOrderDelivering();
            case 2:
                return new MyOrderCompleted();
            case 3:
                return new MyOrderCancelled();
            case 4:
                return new MyOrderRefunded();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
