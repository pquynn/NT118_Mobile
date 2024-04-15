package com.example.foodorderingapp.activity.customer_module.productdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.CommentDialogFragment;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.CommentDomain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDetailCakeActivity extends AppCompatActivity {
    private ImageView imgComment;
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
}
