package com.example.foodorderingapp.ui.activityfragment.customer.category;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailActivity;
import com.example.foodorderingapp.ui.adapter.CategoryAdapter;
import com.example.foodorderingapp.ui.adapter.CategoryListAdapter;
import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.CategoryList;
import com.example.foodorderingapp.ui.adapter.CategoryProductListAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.category.CategoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CategoryFragment extends Fragment implements CategoryProductListAdapter.ProductClickListener{
    private RecyclerView rcvCategory, rcvListCategory;
    private CategoryListAdapter categoryListAdapter;
    private CategoryAdapter categoryAdapter;
    CategoryViewModel categoryViewModel;
    private static final int PRODUCTDETAIL_REQUEST_CODE = 1;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mainActivity = (MainActivity) getActivity();

        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvListCategory = view.findViewById(R.id.rcv_categoryList);

        //Danh sách danh mục
        rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(getContext());
        rcvCategory.setAdapter(categoryAdapter);
        //Load dữ liệu từ firestore
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(getViewLifecycleOwner(), categoryList -> {
            categoryAdapter.setData(categoryList);
        });

        //Xử lý điều hướng khi click danh mục
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                // Xác định vị trí của danh mục được click trong danh sách
                int position = getPositionOfCategory(category);

                // Nếu vị trí hợp lệ (khác -1), cuộn đến vị trí tương ứng trong RecyclerView danh sách sản phẩm
                if (position != -1) {
                    scrollToCategoryPosition(position);
                }
            }
        });
        //Danh sách sản phẩm ứng với từng danh mục
        rcvListCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryListAdapter = new CategoryListAdapter(getActivity(), new ArrayList<>(), this::onProductClick);
        rcvListCategory.setAdapter(categoryListAdapter);
        //Load dữ liệu từ firestore
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getProductListLiveData().observe(getViewLifecycleOwner(), categoryLists -> {
            categoryListAdapter.setData(categoryLists);
        });

        // Lắng nghe kết quả từ `BottomSheet`
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            boolean addToCart = bundle.getBoolean("addToCart");
            if (addToCart) {
                Toast.makeText(getContext(), "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                // Cập nhật giao diện nếu cần thiết
                categoryListAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
    //Xử lý sự kiện khi click vào sản phẩm
    @Override
    public void onProductClick(Product product) {
        // Xử lý sự kiện khi click vào sản phẩm
        Log.d("ProductClick", "idCategory: " + product.getIdCategory());
        Log.d("ProductClick", "Product ID: " + product.getId());
        Intent intent;
        //Tạo intent và truyền dữ liệu vào Activity chi tiết sản phẩm
        intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("productID", product.getId());
        startActivityForResult(intent, PRODUCTDETAIL_REQUEST_CODE);
    }

    //Phương thức tìm vị trí danh mục trong danh sách
    private int getPositionOfCategory(Category category) {
        for (int i = 0; i < categoryListAdapter.getItemCount(); i++) {
            CategoryList categoryList = categoryListAdapter.getItem(i);
            if (categoryList != null && categoryList.getCategory().getId().equals(category.getId())) {
                return i;
            }
        }
        return -1;
    }

    // Phương thức này để cuộn đến vị trí của danh sách sản phẩm
    private void scrollToCategoryPosition(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rcvListCategory.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.scrollToPositionWithOffset(position, 0);
        }
    }

    // method to get result from activity through intent (activity2 -> activity1)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GET DATA FROM COUPON ACTIVITY
        if (requestCode == PRODUCTDETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("addToCart")) {
                // if product is add to cart --> change badge
//                if(data.getBooleanExtra("addToCart", false)){
                    mainActivity.reloadBadge();
//                }
            }
        }
    }
}