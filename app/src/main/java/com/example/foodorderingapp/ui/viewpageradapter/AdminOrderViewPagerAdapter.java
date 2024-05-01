package com.example.foodorderingapp.ui.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodorderingapp.ui.activityfragment.Admin.AdminFragment.AdminCancelOrder;
import com.example.foodorderingapp.ui.activityfragment.Admin.AdminFragment.AdminDeliveredOrder;
import com.example.foodorderingapp.ui.activityfragment.Admin.AdminFragment.AdminDeliveringOrder;
import com.example.foodorderingapp.ui.activityfragment.Admin.AdminFragment.AdminNewOrder;
import com.example.foodorderingapp.ui.activityfragment.Admin.AdminFragment.AdminRefundOrder;

public class AdminOrderViewPagerAdapter extends FragmentStateAdapter {
    public AdminOrderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AdminNewOrder();
            case 1:
                return new AdminDeliveringOrder();
            case 2:
                return new AdminDeliveredOrder();
            case 3:
                return new AdminCancelOrder();
            case 4:
                return new AdminRefundOrder();
            default:
                return new AdminNewOrder();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
