package com.example.foodorderingapp.UI.Activity_Fragment.Customer.ProductDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.Data.Model.Entity.Comment;
import com.example.foodorderingapp.R;

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
        listComment.add(new Comment("1", "1", "Bảo Ngọc", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 3, "Chất lượng, ngon tuyệt vời", date));

        CommentDialogFragment commentDialogFragment = new CommentDialogFragment(listComment);
        commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
    }
}
