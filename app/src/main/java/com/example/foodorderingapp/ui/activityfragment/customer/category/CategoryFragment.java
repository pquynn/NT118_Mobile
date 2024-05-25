package com.example.foodorderingapp.ui.activityfragment.customer.category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.category.CategoryRepository;
import com.example.foodorderingapp.data.repository.category.ICategoryRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailCakeActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailDrinkActivity;
import com.example.foodorderingapp.ui.adapter.CategoryAdapter;
import com.example.foodorderingapp.ui.adapter.CategoryListAdapter;
import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.CategoryList;
import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.ui.viewmodel.customer.category.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment{
    private RecyclerView rcvCategory, rcvListCategory;
    private CategoryListAdapter categoryListAdapter;
    private CategoryAdapter categoryAdapter;
    CategoryViewModel categoryViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvListCategory = view.findViewById(R.id.rcv_categoryList);

        rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(getContext());
        rcvCategory.setAdapter(categoryAdapter);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(getViewLifecycleOwner(), categoryList -> {
            categoryAdapter.setData(categoryList);
        });


        rcvListCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
//        categoryListAdapter = new CategoryListAdapter(getActivity(), getListCategory());
        rcvListCategory.setAdapter(categoryListAdapter);

        categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Kiểm tra xem từ "bánh" có xuất hiện trong tên sản phẩm hay không
                if (isCake(product.getProductName())) {
                    Intent intent = new Intent(getActivity(), ProductDetailCakeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ProductDetailDrinkActivity.class);
                    startActivity(intent);
                }
            }

            private boolean isCake(String productName) {
                // Kiểm tra xem từ "bánh" có xuất hiện trong tên sản phẩm hay không
                return productName.toLowerCase().contains("bánh");
            }
        });

        return view;
    }

//    private List<CategoryList> getListCategory() {
//        List<CategoryList> list = new ArrayList<>();
//
//        List<Product> listProduct = new ArrayList<>();
//        listProduct.add(new Product(R.drawable.img4, "Trà chanh cam xả", "30.000đ"));
//        listProduct.add(new Product(R.drawable.img8, "Trà xanh matcha kem cheese", "30.000đ"));
//
//        listProduct.add(new Product(R.drawable.img1, "Trà sữa chân châu đường đen", "35.000đ"));
//        listProduct.add(new Product(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));
//
//        listProduct.add(new Product(R.drawable.img3, "Bạc xỉu", "20.000đ"));
//
//        listProduct.add(new Product(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));
//        listProduct.add(new Product(R.drawable.img6, "Bánh mochi phúc bồn tử", "19.000đ"));
//        listProduct.add(new Product(R.drawable.img7, "Bánh Tirasumi Socola", "24.000đ"));
//
//        list.add(new CategoryList("Trà", listProduct));
//        list.add(new CategoryList("Trà sữa", listProduct));
//        list.add(new CategoryList("Cafe", listProduct));
//        list.add(new CategoryList("Bánh", listProduct));
//
//        return list;
//    }
}