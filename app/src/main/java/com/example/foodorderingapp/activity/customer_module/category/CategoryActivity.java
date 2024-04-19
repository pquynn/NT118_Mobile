package com.example.foodorderingapp.activity.customer_module.category;

import static androidx.core.app.NotificationCompat.getCategory;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CategoryAdapter;
import com.example.foodorderingapp.adapter.CategoryListAdapter;
import com.example.foodorderingapp.domain.CategoryDomain;
import com.example.foodorderingapp.domain.CategoryListDomain;
import com.example.foodorderingapp.domain.ProductSearchDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.PrimitiveIterator;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rcvCategory, rcvListCategory;
//    private TextView screenName;
    private CategoryListAdapter categoryListAdapter;
    private CategoryAdapter categoryAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

//        TextView screenName = findViewById(R.id.screen_name);
//        screenName.setText("Danh mục");

        rcvCategory = findViewById(R.id.rcv_category);
        rcvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, getCategory());
        rcvCategory.setAdapter(categoryAdapter);

        rcvListCategory = findViewById(R.id.rcv_categoryList);
        rcvListCategory.setLayoutManager(new LinearLayoutManager(this));
        categoryListAdapter = new CategoryListAdapter(this, getListCategory());
        rcvListCategory.setAdapter(categoryListAdapter);
    }

    private List<CategoryDomain> getCategory() {
        List<CategoryDomain> list = new ArrayList<>();
        list.add(new CategoryDomain(R.drawable.img_cafe, "Cà phê"));
        list.add(new CategoryDomain(R.drawable.img_milktea, "Trà sữa"));
        list.add(new CategoryDomain(R.drawable.img_tea, "Trà"));
        list.add(new CategoryDomain(R.drawable.img_cake, "Bánh"));
        return list;
    }

    private List<CategoryListDomain> getListCategory() {
        List<CategoryListDomain> list = new ArrayList<>();

        List<ProductSearchDomain> listProduct = new ArrayList<>();
        listProduct.add(new ProductSearchDomain(R.drawable.img4, "Trà chanh cam xả", "30.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img8, "Trà xanh matcha kem cheese", "30.000đ"));

        listProduct.add(new ProductSearchDomain(R.drawable.img1, "Trà sữa chân châu đường đen", "35.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img2, "Trà sữa truyền thống", "25.000đ"));

        listProduct.add(new ProductSearchDomain(R.drawable.img3, "Bạc xỉu", "20.000đ"));

        listProduct.add(new ProductSearchDomain(R.drawable.img5, "Bánh mochi socola ", "19.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img6, "Bánh mochi phúc bồn tử", "19.000đ"));
        listProduct.add(new ProductSearchDomain(R.drawable.img7, "Bánh Tirasumi Socola", "24.000đ"));

        list.add(new CategoryListDomain("Trà", listProduct));
        list.add(new CategoryListDomain("Trà sữa", listProduct));
        list.add(new CategoryListDomain("Cafe", listProduct));
        list.add(new CategoryListDomain("Bánh", listProduct));

        return list;
    }
}
