package com.example.foodorderingapp;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.foodorderingapp.activity.customer_module.productdetail.ProductDetailDrinkActivity;
import com.example.foodorderingapp.activity.customer_module.search.SearchActivity;
import com.example.foodorderingapp.adapter.CategoryHomeAdapter;
import com.example.foodorderingapp.adapter.NewProductHomeAdapter;
import com.example.foodorderingapp.adapter.ProductPopularHomeAdapter;
import com.example.foodorderingapp.domain.HomeCategory;
import com.example.foodorderingapp.domain.ProductSearchDomain;

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
    RecyclerView rcv_NewPopular;
    List<ProductSearchDomain> productList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        CategoryHomeAdapter adapter = new CategoryHomeAdapter(getActivity(), getHomeCategory());
        rcv_homeCategory.setAdapter(adapter);

        rcv_ProductPopular = view.findViewById(R.id.rcv_ProductPopular);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcv_ProductPopular.setLayoutManager(layoutManager);
        ProductPopularHomeAdapter productPopularHomeAdapter = new ProductPopularHomeAdapter(getActivity(), getProductList());
        rcv_ProductPopular.setAdapter(productPopularHomeAdapter);

        rcv_NewPopular = view.findViewById(R.id.rcv_NewPopular);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
        rcv_NewPopular.setLayoutManager(layoutManager2);
        NewProductHomeAdapter newProductHomeAdapter = new NewProductHomeAdapter(getActivity(), getNewProductList());
        rcv_NewPopular.setAdapter(newProductHomeAdapter);

        return view;
    }

    private List<HomeCategory> getHomeCategory() {
        List<HomeCategory> list = new ArrayList<>();

        list.add(new HomeCategory("All"));
        list.add(new HomeCategory("Cà Phê"));
        list.add(new HomeCategory("Trà Sữa"));
        list.add(new HomeCategory("Trà"));
        list.add(new HomeCategory("Bánh"));

        return list;
    }
    private List<ProductSearchDomain> getProductList() {
        productList = new ArrayList<>();

        productList.add(new ProductSearchDomain(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));
        productList.add(new ProductSearchDomain(R.drawable.img3, "Bạc xỉu", "20.000đ"));
        productList.add(new ProductSearchDomain(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));

        return productList;
    }

    private List<ProductSearchDomain> getNewProductList() {
        List<ProductSearchDomain> newProductList = new ArrayList<>();

        newProductList.add(new ProductSearchDomain(R.drawable.img4, "Trà sữa ...", "25.000đ"));

        return newProductList;
    }
}