package com.example.foodorderingapp.ui.activityfragment.customer.search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.R;
//import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailCakeActivity;
//import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailDrinkActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.productdetail.ProductDetailActivity;
import com.example.foodorderingapp.ui.adapter.SearchAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.search.SearchViewModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rcv_productSearch;
    SearchAdapter searchAdapter;
    SearchView searchView;
    private SearchViewModel searchViewModel;
//    private static final int PRODUCTDETAIL_REQUEST_CODE = 1;
//    private MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Xử lý khi chọn hủy thì chuyển về trang Home
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để gửi dữ liệu về Fragment HomeFragment
                Intent intent = new Intent();
                intent.putExtra("cancel_pressed", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rcv_productSearch = findViewById(R.id.recyclerViewProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_productSearch.setLayoutManager(linearLayoutManager);

        searchView = findViewById(R.id.searchView_product);
        searchView.clearFocus();

        searchAdapter = new SearchAdapter(this);
        rcv_productSearch.setAdapter(searchAdapter);
        //set đường kẻ giữa các sản phẩm
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_productSearch.addItemDecoration(itemDecoration);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getProductListLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                searchAdapter.setFilterList(products);
            }
        });

        // Xử lý sự kiện khi người dùng nhập vào SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Thực hiện tìm kiếm khi người dùng thay đổi văn bản trong SearchView
                searchAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Xử lý sự kiện: Chuyển sang chi tiết sản phẩm
        searchAdapter.setOnProductClickListener(new SearchAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                // Xử lý sự kiện click vào sản phẩm ở đây
                // Ví dụ: Chuyển sang màn hình chi tiết sản phẩm
                Log.d("ProductClick", "idCategory: " + product.getIdCategory());
                Log.d("ProductClick", "Product ID: " + product.getId());
                Set<String> validCategories = new HashSet<>(Arrays.asList("2", "4"));
                Intent intent;
                if (validCategories.contains(product.getIdCategory())) {
                    intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                } else {
                    intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                }
                //Tạo intent và truyền dữ liệu vào Activity chi tiết sản phẩm
        //        intent = new Intent(getActivity(), ProductDetailDrink.class);
                intent.putExtra("productID", product.getId());
                startActivity(intent);
            }
        });

    }

//    // method to get result from activity through intent (activity2 -> activity1)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //GET DATA FROM COUPON ACTIVITY
//        if (requestCode == PRODUCTDETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null && data.hasExtra("addToCart")) {
//                // if product is add to cart --> change badge
//                if(data.getBooleanExtra("addToCart", false)){
//                    mainActivity.reloadBadge();
//                }
//            }
//        }
//
//    }
}