package com.example.foodorderingapp.ui.activityfragment.Admin.AdminFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.viewpageradapter.AdminOrderViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminOrder extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private AdminOrderViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_order, container, false);

        TextView screenName = view.findViewById(R.id.screen_name);
        screenName.setText("QUẢN LÝ ĐƠN HÀNG");

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        viewPagerAdapter = new AdminOrderViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, i) -> {
            switch (i) {
                case 0:
                    tab.setText("Mới");
                    break;
                case 1:
                    tab.setText("Đang giao");
                    break;
                case 2:
                    tab.setText("Đã giao");
                    break;
                case 3:
                    tab.setText("Đã hủy");
                    break;
                case 4:
                    tab.setText("Hoàn tiền");
                    break;
            }
        }).attach();

        return view;
    }


}