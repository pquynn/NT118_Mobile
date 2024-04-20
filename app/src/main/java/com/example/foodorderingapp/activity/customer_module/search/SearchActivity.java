package com.example.foodorderingapp.activity.customer_module.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.SearchAdapter;
import com.example.foodorderingapp.domain.ProductSearchDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rcv_productSearch;

    List<ProductSearchDomain> nlist;
    SearchAdapter searchAdapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rcv_productSearch = findViewById(R.id.recyclerViewProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_productSearch.setLayoutManager(linearLayoutManager);

        searchView = findViewById(R.id.searchView_product);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        nlist = getListProduct();
        searchAdapter = new SearchAdapter(nlist);
        rcv_productSearch.setAdapter(searchAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_productSearch.addItemDecoration(itemDecoration);

    }

    private void showSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView.findFocus(), InputMethodManager.SHOW_IMPLICIT);
    }

    private void filterList(String newText) {
        List<ProductSearchDomain> filterList = new ArrayList<>();
        for (ProductSearchDomain itempro: nlist){
            if(itempro.getName().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(itempro);
            }
        }

        if(filterList.isEmpty()){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            searchAdapter.setFilterList(filterList);
        }
    }

    private List<ProductSearchDomain> getListProduct() {
        List<ProductSearchDomain> list = new ArrayList<>();

        list.add(new ProductSearchDomain(R.drawable.img1, "Trà sữa chân châu đường đen", "35.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img3, "Bạc xỉu", "20.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img4, "Trà chanh cam xả", "30.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img6, "Bánh mochi phúc bồn tử", "19.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img7, "Bánh Tirasumi Socola", "24.000đ"));
        list.add(new ProductSearchDomain(R.drawable.img8, "Trà xanh matcha kem cheese", "30.000đ"));

        return list;
    }
}
