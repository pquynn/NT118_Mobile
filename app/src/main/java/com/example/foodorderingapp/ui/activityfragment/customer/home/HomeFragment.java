package com.example.foodorderingapp.ui.activityfragment.customer.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailCakeActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailDrinkActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.search.SearchActivity;
import com.example.foodorderingapp.ui.adapter.CategoryHomeAdapter;
import com.example.foodorderingapp.data.model.HomeCategory;
import com.example.foodorderingapp.ui.adapter.ProductPopularHomeAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView rcv_homeCategory;
    RecyclerView rcv_ProductPopular;
    HomeViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout frameSearch = view.findViewById(R.id.frameLayoutSearch);
        frameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi click vào frameLayoutSearch
                // Mở activity SearchActivity
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        rcv_homeCategory = view.findViewById(R.id.rcv_homeCategory);
        rcv_homeCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CategoryHomeAdapter adapter = new CategoryHomeAdapter(new ArrayList<>());
        rcv_homeCategory.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getCategoryListLiveData().observe(getViewLifecycleOwner(), categoryList -> {
            adapter.setCategoryList(categoryList);
        });

        rcv_ProductPopular = view.findViewById(R.id.rcv_ProductPopular);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_ProductPopular.setLayoutManager(layoutManager);
        Context context = getContext();
        if (context != null) {
            ProductPopularHomeAdapter productPopularHomeAdapter = new ProductPopularHomeAdapter(getActivity(), new ArrayList<>(), product -> {
                if ("1".equals(product.getIdCategory()) || "3".equals(product.getIdCategory())) {
                    Intent intent = new Intent(getContext(), ProductDetailDrinkActivity.class);
                    intent.putExtra("PRODUCT_ID", product.getProductName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ProductDetailCakeActivity.class);
                    intent.putExtra("PRODUCT_ID", product.getProductName());
                    startActivity(intent);
                }
            });
            rcv_homeCategory.setHasFixedSize(true);
            rcv_ProductPopular.setAdapter(productPopularHomeAdapter);

            viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

            viewModel.getBestSellingProducts().observe(getViewLifecycleOwner(), products -> {
                productPopularHomeAdapter.setProductList(products);
            });
        }
    }

}
