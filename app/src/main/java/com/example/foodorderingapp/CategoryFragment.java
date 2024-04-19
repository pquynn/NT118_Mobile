package com.example.foodorderingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderingapp.adapter.CategoryAdapter;
import com.example.foodorderingapp.adapter.CategoryListAdapter;
import com.example.foodorderingapp.domain.CategoryDomain;
import com.example.foodorderingapp.domain.CategoryListDomain;
import com.example.foodorderingapp.domain.ProductSearchDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView rcvCategory, rcvListCategory;
    private CategoryListAdapter categoryListAdapter;
    private CategoryAdapter categoryAdapter;
    private List<CategoryDomain> listCategory;
    private List<CategoryListDomain> getListCategory;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        listCategory.add(new CategoryDomain(R.drawable.img_cafe, "Cà phê"));
        listCategory.add(new CategoryDomain(R.drawable.img_milktea, "Trà sữa"));
        listCategory.add(new CategoryDomain(R.drawable.img_tea, "Trà"));
        listCategory.add(new CategoryDomain(R.drawable.img_cake, "Bánh"));

        List<ProductSearchDomain> listProduct = new ArrayList<>();
        listProduct.add(new ProductSearchDomain(R.drawable.img4, "Trà chanh cam xả", "30.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img8, "Trà xanh matcha kem cheese", "30.000đ"));

        listProduct.add(new ProductSearchDomain(R.drawable.img1, "Trà sữa chân châu đường đen", "35.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));

        listProduct.add(new ProductSearchDomain(R.drawable.img3, "Bạc xỉu", "20.000đ"));

        listProduct.add(new ProductSearchDomain(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img6, "Bánh mochi phúc bồn tử", "19.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img7, "Bánh Tirasumi Socola", "24.000đ"));

        getListCategory.add(new CategoryListDomain("Trà", listProduct));
        getListCategory.add(new CategoryListDomain("Trà sữa", listProduct));
        getListCategory.add(new CategoryListDomain("Cafe", listProduct));
        getListCategory.add(new CategoryListDomain("Bánh", listProduct));
        return view;
    }
}