package com.example.foodorderingapp.UI.Activity_Fragment.Customer.search;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import com.example.foodorderingapp.Data.Model.Entity.Product;
import com.example.foodorderingapp.Data.Repository.Product.IProductRepository;
import com.example.foodorderingapp.Data.Repository.Product.ProductRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Activity_Fragment.Customer.ProductDetail.ProductDetailCakeActivity;
import com.example.foodorderingapp.UI.Activity_Fragment.Customer.ProductDetail.ProductDetailDrinkActivity;
import com.example.foodorderingapp.UI.Adapter.SearchAdapter;
import com.example.foodorderingapp.Data.Model.ProductSearch;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements IProductRepository.ProductListCallback {

    RecyclerView rcv_productSearch;
    SearchAdapter searchAdapter;
    SearchView searchView;
    private ProductRepository productRepository;
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
        //Hiên thị danh sách sản phẩm từ FireStore
        productRepository = new ProductRepository();
        productRepository.getAllProducts(this);

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
                if (product.getIdCategory() != null) {
                    if (product.getIdCategory().equals("2") || product.getIdCategory().equals("4")) {
                        Intent intent = new Intent(SearchActivity.this, ProductDetailCakeActivity.class);
                        intent.putExtra("productId", product.getId());
                        startActivity(intent);
                    } else if (product.getIdCategory().equals("1") || product.getIdCategory().equals("3")) {
                        Intent intent = new Intent(SearchActivity.this, ProductDetailDrinkActivity.class);
                        intent.putExtra("productId", product.getId());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    //Load danh sách sản phẩm từ FireStore
    @Override
    public void onProductListLoaded(List<Product> productList) {
        searchAdapter.setFilterList(productList); // Cập nhật dữ liệu cho Adapter
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProductListLoadFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}