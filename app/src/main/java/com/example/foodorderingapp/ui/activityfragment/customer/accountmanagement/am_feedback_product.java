package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrderCommentVM;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.OrderFeedbackListVM;

import java.util.Date;

public class am_feedback_product extends AppCompatActivity {

    OrderCommentVM viewModel;
    OrdersFeedbackRepository repository = new OrdersFeedbackRepository();
    String userID = "", productID = "", commentID = "";
    Comment comment_ = new Comment();
    Context context = this;
    TextView tv_cmtProductName;
    EditText txt_input_fb;
    RatingBar ratingBar;
    Button btn_submit_fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_product);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá");

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("user_id") && intent.hasExtra("product_id")){
            userID = intent.getStringExtra("user_id");
            productID = intent.getStringExtra("product_id");
        }

        viewModel = new OrderCommentVM(userID, productID, context);

        tv_cmtProductName = findViewById(R.id.tv_cmtProductName);
        ratingBar = findViewById(R.id.ratingBar);
        txt_input_fb =findViewById(R.id.txt_input_fb);
        btn_submit_fb = findViewById(R.id.btn_submit_fb);

        repository.checkExistComment(userID, productID, new OrdersFeedbackRepository.checkCommentCallback() {
            @Override
            public void checkCommentSuccess(int isExist) {
                Log.d("comment exist: ", "exist");
                viewModel.getCommentMutableLiveData().observe((LifecycleOwner) context, new Observer<Comment>() {
                    @Override
                    public void onChanged(Comment comment) {
                        ratingBar.setRating(comment.getRatingBar());
                        txt_input_fb.setText(comment.getContent());
                        comment_ = comment;
                    }
                });

                viewModel.getProductNameImageMutableLiveData().observe((LifecycleOwner) context, new Observer<OrderCommentVM.ProductNameImage>() {
                    @Override
                    public void onChanged(OrderCommentVM.ProductNameImage productNameImage) {
                        tv_cmtProductName.setText(productNameImage.getName());
                    }
                });

                btn_submit_fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comment_.setContent(String.valueOf(txt_input_fb.getText()));
                        comment_.setDate(new Date());
                        float ratingScore = ratingBar.getRating();

                        comment_.setRatingBar((int) ratingScore);
                        repository.updateComment(comment_.getId(), comment_);
                    }
                });
            }

            @Override
            public void checkCommentError(Exception e) {

            }
        });
    }
}