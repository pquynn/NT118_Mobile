package com.example.foodorderingapp.ui.activityfragment.customer.category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements ICategoryRepository.CategoryListCallBack {

    private RecyclerView rcvCategory, rcvListCategory;
    private CategoryListAdapter categoryListAdapter;
    private CategoryAdapter categoryAdapter;
    private CategoryRepository categoryRepository;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(getContext());
        rcvCategory.setAdapter(categoryAdapter);

        categoryRepository = new CategoryRepository(); // Khởi tạo repository
        categoryRepository.getListCategory(this); // Gọi phương thức để lấy danh sách danh mục


        rcvListCategory = view.findViewById(R.id.rcv_categoryList);
        rcvListCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryListAdapter = new CategoryListAdapter(getActivity(), getListCategory());
        rcvListCategory.setAdapter(categoryListAdapter);

        categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductSearch product) {
                // Kiểm tra xem từ "bánh" có xuất hiện trong tên sản phẩm hay không
                if (isCake(product.getName())) {
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

    //interface của danh sách danh mục
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCategoryListLoaded(List<Category> categoryList) {
        // Khi danh sách danh mục đã được tải thành công từ repository, cập nhật adapter
        categoryAdapter.setData(categoryList);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryListLoadFailed(Exception e) {
        // Xử lý khi có lỗi xảy ra khi tải danh sách danh mục
        Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
        Log.e("CategoryFragment", "Failed to load categories", e);
    }

    private List<CategoryList> getListCategory() {
        List<CategoryList> list = new ArrayList<>();

        List<ProductSearch> listProduct = new ArrayList<>();
        listProduct.add(new ProductSearch(R.drawable.img4, "Trà chanh cam xả", "30.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img8, "Trà xanh matcha kem cheese", "30.000đ"));

        listProduct.add(new ProductSearch(R.drawable.img1, "Trà sữa chân châu đường đen", "35.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));

        listProduct.add(new ProductSearch(R.drawable.img3, "Bạc xỉu", "20.000đ"));

        listProduct.add(new ProductSearch(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img6, "Bánh mochi phúc bồn tử", "19.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img7, "Bánh Tirasumi Socola", "24.000đ"));

        list.add(new CategoryList("Trà", listProduct));
        list.add(new CategoryList("Trà sữa", listProduct));
        list.add(new CategoryList("Cafe", listProduct));
        list.add(new CategoryList("Bánh", listProduct));

        return list;
    }
}