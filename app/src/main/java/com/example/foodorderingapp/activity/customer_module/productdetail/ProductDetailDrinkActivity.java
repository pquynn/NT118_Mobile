package com.example.foodorderingapp.activity.customer_module.productdetail;

import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.CommentDialogFragment;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapter.ToppingAdapter;
import com.example.foodorderingapp.domain.CommentDomain;
import com.example.foodorderingapp.domain.Topping_SizeDomain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDetailDrinkActivity extends AppCompatActivity {
    private RecyclerView rcvTopping;
    private ToppingAdapter toppingAdapter;
    private ImageView imgComment;
    private TextView contentTextView, showMoreTextView, showLessTextView;
    private CharSequence originalText;
    private int originalMaxLines;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_drink);

        imgComment = findViewById(R.id.ic_comment);

        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clickOpenBottemFragmment();
            }
        });

        contentTextView = findViewById(R.id.contentTextView);
        showMoreTextView = findViewById(R.id.showMoreTextView);
        showLessTextView = findViewById(R.id.showLessTextView);
        // Lưu trạng thái ban đầu của nội dung
        originalMaxLines = contentTextView.getMaxLines();
        originalText = contentTextView.getText();

        showMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị toàn bộ nội dung khi nhấn vào "Xem thêm"
                contentTextView.setMaxLines(Integer.MAX_VALUE);
                showMoreTextView.setVisibility(View.GONE);
                showLessTextView.setVisibility(View.VISIBLE);
            }
        });

        showLessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rút gọn nội dung lại khi nhấn vào "Rút gọn"
                contentTextView.setMaxLines(originalMaxLines);
                contentTextView.setText(originalText);
                showMoreTextView.setVisibility(View.VISIBLE);
                showLessTextView.setVisibility(View.GONE);
            }
        });


        rcvTopping = findViewById(R.id.rcv_topping);
        rcvTopping.setLayoutManager(new LinearLayoutManager(this));

        toppingAdapter = new ToppingAdapter(this, getListTopping());
        rcvTopping.setAdapter(toppingAdapter);
    }

    private void clickOpenBottemFragmment() {
        String dateString = "06-03-2025";
        // Define the date format of your input string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Parse the string to obtain a Date object
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<CommentDomain> listComment = new ArrayList<>();
        listComment.add(new CommentDomain("Bảo Ngọc", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new CommentDomain("Ngọc Bảo", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new CommentDomain("Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new CommentDomain("Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new CommentDomain("Ngọc Bảo", 3, "Chất lượng, ngon tuyệt vời", date));

        CommentDialogFragment commentDialogFragment = new CommentDialogFragment(listComment);
        commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
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
