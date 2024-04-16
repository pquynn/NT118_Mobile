package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ViewPagerAdmin.AdminOrderViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DecimalFormat;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ViewHolder> {
    private long totalRevenue, totalOrder, totalRefund;

    public AdminOrderAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_admin_home, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRevenue, txtTotalOrder, txtRefund;
        private TabLayout tabLayout;
        private ViewPager2 viewPager;
        private AdminOrderViewPagerAdapter viewPagerAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView screenName = itemView.findViewById(R.id.screen_name);
            screenName.setText("QUẢN LÝ ĐƠN HÀNG");

            tabLayout = itemView.findViewById(R.id.tabLayout);
            viewPager = itemView.findViewById(R.id.viewPager);

            viewPagerAdapter = new AdminOrderViewPagerAdapter();
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
                }
            }).attach();
        }
    }
}