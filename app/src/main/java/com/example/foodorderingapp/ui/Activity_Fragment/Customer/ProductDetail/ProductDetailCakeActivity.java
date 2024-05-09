package com.example.foodorderingapp.UI.Activity_Fragment.Customer.ProductDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.Data.Repository.Product.ProductRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.Entity.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDetailCakeActivity extends AppCompatActivity {
    private ImageView imgComment;
    private TextView contentTextView, showMoreTextView, showLessTextView;
    private CharSequence originalText;
    private int originalMaxLines;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_cake);

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

        //Hiển thị thông tin chi tiết sản phẩm
        String productId = "10";
        ProductRepository productRepository = new ProductRepository();
        productRepository.getProductDetailsCake(productId, new ProductRepository.ProductDetailCakeCallback() {
            @Override
            public void onProductDetailLoaded(String productName, String productImage, int productPrice, String productInfo) {
                setTitle(productName); // Thiết lập tiêu đề của activity là tên sản phẩm

                // Hiển thị ảnh sản phẩm
                ImageView imgCake = findViewById(R.id.img_cake);
                Glide.with(ProductDetailCakeActivity.this).load(productImage).into(imgCake);

                // Hiển thị tên và giá sản phẩm
                TextView txtNameCake = findViewById(R.id.txt_NameCake);
                txtNameCake.setText(productName);

                TextView txtPriceCake = findViewById(R.id.txt_priceCake);
                txtPriceCake.setText(String.valueOf(productPrice) + "đ");

                TextView txtPrice = findViewById(R.id.txt_price);
                txtPrice.setText(String.valueOf(productPrice));

                // Hiển thị mô tả sản phẩm
                TextView contentTextView = findViewById(R.id.contentTextView);
                contentTextView.setText(productInfo);
            }

            @Override
            public void onProductDetailLoadFailed(String errorMessage) {
                Toast.makeText(ProductDetailCakeActivity.this, "Failed to load product details: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
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

        List<Comment> listComment = new ArrayList<>();
        listComment.add(new Comment("Bảo Ngọc", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("Ngọc Bảo", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("Ngọc Bảo", 3, "Chất lượng, ngon tuyệt vời", date));

        CommentDialogFragment commentDialogFragment = new CommentDialogFragment(listComment);
        commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
    }
}
