package com.example.foodorderingapp.ui.activityfragment.customer.productdetail;

import static com.example.foodorderingapp.ui.bindingadapters.BindingAdapters.setPriceFormatted;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.data.repository.product.ProductRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.ui.viewmodel.customer.productdetail.ProductDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDetailCakeActivity extends AppCompatActivity {
    private ImageView imgComment;
    private TextView contentTextView, showMoreTextView, showLessTextView, quantityComment;
    private CharSequence originalText;
    private int originalMaxLines;
    private ProductDetailViewModel productDetailViewModel;
    private String productId;
    private OrdersFeedbackRepository ordersFeedbackRepository = new OrdersFeedbackRepository();
    private Context context = this;
    private CommentDialogFragment commentDialogFragment = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_cake);

        contentTextView = findViewById(R.id.contentTextView);
        showMoreTextView = findViewById(R.id.showMoreTextView);
        showLessTextView = findViewById(R.id.showLessTextView);
        quantityComment = findViewById(R.id.txt_comment);
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

        //Xử lý button quay lại
        FrameLayout btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button tăng giảm số lượng
        View layoutButtonCount = findViewById(R.id.layout_button_count);

        FrameLayout btnDecrease = layoutButtonCount.findViewById(R.id.img_decrease);
        FrameLayout btnIncrease = layoutButtonCount.findViewById(R.id.img_increase);
        final TextView txtQuantity = layoutButtonCount.findViewById(R.id.count);

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        Intent intent = getIntent();
        // Kiểm tra xem intent có dữ liệu không
        if (intent != null) {
            // Lấy dữ liệu từ intent
            Log.d("LoadData", "Sucessful");
            productId = intent.getStringExtra("productID");
            productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
            productDetailViewModel.getProductDetail(productId).observe(this, new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    // Hiển thị ảnh sản phẩm
                    ImageView imgCake = findViewById(R.id.img_cake);
                    Glide.with(ProductDetailCakeActivity.this).load(product.getProductImage()).into(imgCake);

                    // Hiển thị tên và giá sản phẩm
                    TextView txtNameCake = findViewById(R.id.txt_NameCake);
                    txtNameCake.setText(product.getProductName());

                    TextView txtPriceCake = findViewById(R.id.txt_priceCake);
                    txtPriceCake.setText(String.valueOf(product.getProductPrice()));

                    // Hiển thị mô tả sản phẩm
                    TextView contentTextView = findViewById(R.id.contentTextView);
                    contentTextView.setText(product.getProductInfo());

                    TextView txtPrice = findViewById(R.id.txt_price);
                    txtPrice.setText(String.valueOf(product.getProductPrice()));
                }
            });

        } else {
            // Xử lý trường hợp intent không có dữ liệu
            Log.d("LoadData", "Failed");
        }

        imgComment = findViewById(R.id.ic_comment);
        ordersFeedbackRepository.checkExistComments(productId, new OrdersFeedbackRepository.checkCommentsCallback() {
            @Override
            public void loadCommentsSuccess(int isExist) {
                quantityComment.setText(isExist+"");
                if(isExist>0){
                    imgComment.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            commentDialogFragment = null;
                            productDetailViewModel.getListCommentLiveData(productId).observe((LifecycleOwner) context , new Observer<List<Comment>>() {
                                @Override
                                public void onChanged(List<Comment> list) {
                                    // Remove the observer after receiving the data to prevent multiple dialogs
                                    productDetailViewModel.getListCommentLiveData(productId).removeObserver(this);

                                    Log.d("Comment dialog: ", "get in");
                                    commentDialogFragment = new CommentDialogFragment(list);
                                    commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
                                }
                            });
                        }
                    });

                }else{
                    imgComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Load comment in activity: ", "No comments");

                            commentDialogFragment = new CommentDialogFragment(null);
                            commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
                        }
                    });

                }
            }

            @Override
            public void loadCommentError(Exception e) {

            }
        });
    }
}
