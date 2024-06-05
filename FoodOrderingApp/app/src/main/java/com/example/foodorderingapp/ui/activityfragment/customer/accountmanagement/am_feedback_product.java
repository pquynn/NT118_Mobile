package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.User;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrderCommentVM;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.OrderFeedbackListVM;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.UserInfoVM;

import java.util.Date;

public class am_feedback_product extends AppCompatActivity {

    OrderCommentVM viewModel;
    OrdersFeedbackRepository repository = new OrdersFeedbackRepository();
    String userID = "", productID = "", commentID = "";
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    Comment comment_ = new Comment();
    Context context = this;
    TextView tv_cmtProductName;
    ImageView tv_cmtProductImage;
    EditText txt_input_fb;
    RatingBar ratingBar;
    Button btn_submit_fb;
    FrameLayout btnBack;
    AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userID = sharedPreferences.getString(KEY_USER_ID, null);
        if (userID== null) {
            // User ID not found, handle this case
            finish();
            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_product);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        tv_cmtProductImage =findViewById(R.id.tv_cmtProductImage);

        // Tạo AlertDialog với ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(true);
        progressDialog = builder.create();
        progressDialog.getWindow().setLayout(50,50);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressDialog.show();
        viewModel.getProductNameImageMutableLiveData().observe((LifecycleOwner) context, new Observer<OrderCommentVM.ProductNameImage>() {
            @Override
            public void onChanged(OrderCommentVM.ProductNameImage productNameImage) {
                tv_cmtProductName.setText(productNameImage.getName());
                Glide.with(context)
                        .load(productNameImage.getImage())
                        .into(tv_cmtProductImage);
            }
        });


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
                        progressDialog.dismiss();
                    }
                });

                btn_submit_fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int ratingScore = (int) ratingBar.getRating();
                        String content = String.valueOf(txt_input_fb.getText());
                        if (ratingScore != 0 && !content.equals("")) {
                            progressDialog.show();
                            comment_.setContent(String.valueOf(txt_input_fb.getText()));
                            comment_.setDate(new Date());

                            comment_.setRatingBar(ratingScore);
                            repository.updateComment(comment_.getId(), comment_, new OrdersFeedbackRepository.commentCallback() {
                                @Override
                                public void loadCommentSuccess(Comment comment) {
                                    Log.d("Update comment", "Comment updated!");
                                    Toast.makeText(getApplicationContext(), "Đã cập nhật bình luận", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish(); // Quay về trang trước
                                }

                                @Override
                                public void loadCommentError(Exception e) {
                                    Log.e("Update comment", "Error updating comment", e);
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Cập nhật bình luận thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(), "Hãy nhập đánh giá!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void checkCommentError(Exception e) {
                progressDialog.dismiss();
                Log.d("comment exitst: ", "none");
                UserInfoVM viewModelUser = new UserInfoVM(userID, context);

                viewModelUser.getUserInfoLiveData().observe((LifecycleOwner) context, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        btn_submit_fb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int ratingScore = (int) ratingBar.getRating();
                                String content = String.valueOf(txt_input_fb.getText());
                                if (ratingScore != 0 && !content.equals("")) {
                                    comment_ = new Comment(productID, userID, user.getUserName(), ratingScore, content, new Date());

                                    repository.addComment(comment_, new OrdersFeedbackRepository.commentCallback() {
                                        @Override
                                        public void loadCommentSuccess(Comment comment) {
                                            Log.d("Add comment", "Comment added!");
                                            Toast.makeText(getApplicationContext(), "Đã thêm bình luận", Toast.LENGTH_SHORT).show();
                                            finish(); // Quay về trang trước
                                        }

                                        @Override
                                        public void loadCommentError(Exception e) {
                                            Log.e("Add comment", "Error updating comment", e);
                                            Toast.makeText(getApplicationContext(), "Thêm bình luận thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Hãy nhập đánh giá!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        });
    }
}