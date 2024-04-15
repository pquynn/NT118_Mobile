package com.example.foodorderingapp.activity.customer_module.productdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.CommentDialogFragment;
import com.example.foodorderingapp.adapter.SearchAdapter;
import com.example.foodorderingapp.adapter.ToppingAdapter;
import com.example.foodorderingapp.domain.Topping_SizeDomain;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailDrinkActivity extends AppCompatActivity {
    private RecyclerView rcvTopping;
    private ToppingAdapter toppingAdapter;

    private ImageView imgComment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_drink);

        imgComment = findViewById(R.id.ic_comment);

        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                CommentDialogFragment dialogFragment = new CommentDialogFragment();
                dialogFragment.show(fragmentManager, "CommentDialogFragment");
            }
        });

        rcvTopping = findViewById(R.id.rcv_topping);
        rcvTopping.setLayoutManager(new LinearLayoutManager(this));

        toppingAdapter = new ToppingAdapter(this, getListTopping());
        rcvTopping.setAdapter(toppingAdapter);
    }

    @NonNull
    private List<Topping_SizeDomain> getListTopping() {
        List<Topping_SizeDomain> listTopping = new ArrayList<>();

        listTopping.add(new Topping_SizeDomain("Trân châu trắng", "5.000đ"));
        listTopping.add(new Topping_SizeDomain("Trân châu đen", "5.000đ"));
        listTopping.add(new Topping_SizeDomain("Bánh Flan", "7.000đ"));
        listTopping.add(new Topping_SizeDomain("Thạch dừa phô mai", "10.000đ"));
        return listTopping;
    }
}
