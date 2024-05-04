package com.example.foodorderingapp.ui.Activity_Fragment.Customer.AccountManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.ViewPagerAdapter.MyOrdersViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class am_my_orders extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MyOrdersViewPagerAdapter ViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_my_orders);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đơn hàng của tôi");

        viewPager = findViewById(R.id.my_orders_viewpager);
        tabLayout = findViewById(R.id.my_orders_menu);

        ViewPagerAdapter = new MyOrdersViewPagerAdapter(this);
        viewPager.setAdapter(ViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, i) -> {
            switch(i) {
                case 0:
                    tab.setText("Đang xử lý");
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

//        for(int i = 0; i<tabLayout.getTabCount();i++){
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//
//            if(tab!=null){
//                Typeface typeface = ResourcesCompat.getFont(this,R.font.roboto_regular);
//                tab.view.
//            }
//        }
    }
}