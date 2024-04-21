package com.example.foodorderingapp.UI.Activity_Fragment.Customer.Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Adapter.CategoryAdapter;
import com.example.foodorderingapp.UI.Adapter.CategoryListAdapter;
import com.example.foodorderingapp.Data.Model.Entity.Category;
import com.example.foodorderingapp.Data.Model.CategoryList;
import com.example.foodorderingapp.Data.Model.ProductSearch;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView rcvCategory, rcvListCategory;
    private CategoryListAdapter categoryListAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Category> listCategory;
    private List<CategoryList> getListCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(listCategory);
        rcvCategory.setAdapter(categoryAdapter);

        rcvListCategory = view.findViewById(R.id.rcv_categoryList);
        rcvListCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryListAdapter = new CategoryListAdapter(getActivity(), getListCategory);
        rcvListCategory.setAdapter(categoryListAdapter);

        listCategory = new ArrayList<Category>();
        listCategory.add(new Category(R.drawable.img_cafe, "Cà phê"));
        listCategory.add(new Category(R.drawable.img_milktea, "Trà sữa"));
        listCategory.add(new Category(R.drawable.img_tea, "Trà"));
        listCategory.add(new Category(R.drawable.img_cake, "Bánh"));

        List<ProductSearch> listProduct = new ArrayList<>();
        listProduct.add(new ProductSearch(R.drawable.img4, "Trà chanh cam xả", "30.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img8, "Trà xanh matcha kem cheese", "30.000đ"));

        listProduct.add(new ProductSearch(R.drawable.img1, "Trà sữa chân châu đường đen", "35.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));

        listProduct.add(new ProductSearch(R.drawable.img3, "Bạc xỉu", "20.000đ"));

        listProduct.add(new ProductSearch(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img6, "Bánh mochi phúc bồn tử", "19.000đ"));
        listProduct.add(new ProductSearch(R.drawable.img7, "Bánh Tirasumi Socola", "24.000đ"));

        getListCategory = new ArrayList<CategoryList>();
        getListCategory.add(new CategoryList("Trà", listProduct));
        getListCategory.add(new CategoryList("Trà sữa", listProduct));
        getListCategory.add(new CategoryList("Cafe", listProduct));
        getListCategory.add(new CategoryList("Bánh", listProduct));
        return view;
    }
}