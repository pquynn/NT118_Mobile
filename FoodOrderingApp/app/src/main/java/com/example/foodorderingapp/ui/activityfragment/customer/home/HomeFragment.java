package com.example.foodorderingapp.ui.activityfragment.customer.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.ui.activityfragment.customer.category.CategoryFragment;
//import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailCakeActivity;
//import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailDrinkActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.search.SearchActivity;
import com.example.foodorderingapp.ui.adapter.CategoryHomeAdapter;
import com.example.foodorderingapp.data.model.HomeCategory;
import com.example.foodorderingapp.ui.adapter.ProductPopularHomeAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView rcv_homeCategory;
    RecyclerView rcv_ProductPopular;
    HomeViewModel viewModel;
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get user id from shared preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);

        //todo: nếu user đang nhập thì thay đổi txt_welcome, nếu chưa đăng nhập thì đổi thành "Chào bạn mới" hay j đó
        //todo: đổi địa chỉ cửa hàng thành địa chỉ UIT nha, Trung set trong định vị r
        if (userId == null) {
        }
        else{

        }

        // Xử lý khi click vào frameLayoutSearch
        FrameLayout frameSearch = view.findViewById(R.id.frameLayoutSearch);
        frameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở activity SearchActivity
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        // Xử lý khi click vào Xem thêm của Danh mục --> điều hướng sang Fragment Category
        TextView tvExtend1 = view.findViewById(R.id.textExtend1);
        tvExtend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_area);
                navController.navigate(R.id.categoryFragment);
            }
        });

        // Xử lý khi click vào Xem thêm của Cửa hàng
        TextView tvExtend2 = view.findViewById(R.id.textExtend2);
        tvExtend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StoreActivity.class);
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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcv_ProductPopular.setLayoutManager(layoutManager);
        Context context = getContext();
        if (context != null) {
            ProductPopularHomeAdapter productPopularHomeAdapter = new ProductPopularHomeAdapter(getContext(), new ArrayList<>(), product -> {
                Log.d("ProductClick", "idCategory: " + product.getIdCategory());
                Log.d("ProductClick", "Product ID: " + product.getId());
                Set<String> validCategories = new HashSet<>(Arrays.asList("2", "4"));
                Intent intent;
                if (validCategories.contains(product.getIdCategory())) {
                    intent = new Intent(getActivity(), ProductDetailActivity.class);
                } else {
                    intent = new Intent(getActivity(), ProductDetailActivity.class);
                }
                //Tạo intent và truyền dữ liệu vào Activity chi tiết sản phẩm
//                intent = new Intent(getActivity(), ProductDetailDrink.class);
                intent.putExtra("productID", product.getId());
                startActivity(intent);


            });
            rcv_homeCategory.setHasFixedSize(true);
            rcv_ProductPopular.setAdapter(productPopularHomeAdapter);

            //Hiển thị danh sách sản phẩm phổ biến
            viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

            viewModel.getBestSellingProducts().observe(getViewLifecycleOwner(), products -> {
                productPopularHomeAdapter.setProductList(products);
            });

            TextView averagePoint = view.findViewById(R.id.text_ic_star);

            viewModel.getCommentList().observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
                @Override
                public void onChanged(List<Comment> commentList) {
                    double averageScore = viewModel.calculateAverageScore(commentList);
                    averagePoint.setText(String.format(Locale.getDefault(), "%.1f", averageScore));
                }
            });
        }
    }

}
