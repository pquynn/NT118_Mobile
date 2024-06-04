package com.example.foodorderingapp.ui.activityfragment.customer.productdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.ui.adapter.ToppingAdapter;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.ui.viewmodel.customer.productdetail.ProductDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductDetailDrinkActivity extends AppCompatActivity {
    private RecyclerView rcvTopping;
    private ToppingAdapter toppingAdapter;
    private ImageView imgComment;
    private TextView contentTextView, showMoreTextView, showLessTextView;
    private CharSequence originalText;
    private int originalMaxLines;
    private ProductDetailViewModel productDetailViewModel;
    private Product product;

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
            String productId = intent.getStringExtra("productID");
            productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
            productDetailViewModel.getProductDetail(productId).observe(this, new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    // Hiển thị ảnh sản phẩm
                    ImageView imgCake = findViewById(R.id.img_cake);
                    Glide.with(ProductDetailDrinkActivity.this).load(product.getProductImage()).into(imgCake);

                    // Hiển thị tên và giá sản phẩm
                    TextView txtNameCake = findViewById(R.id.txt_NameCake);
                    txtNameCake.setText(product.getProductName());

                    TextView txtPriceCake = findViewById(R.id.txt_priceCake);
                    txtPriceCake.setText(String.valueOf(product.getProductPrice()));

                    // Hiển thị mô tả sản phẩm
                    TextView contentTextView = findViewById(R.id.contentTextView);
                    contentTextView.setText(product.getProductInfo());

//                    TextView txtPrice = findViewById(R.id.txt_price);
//                    txtPrice.setText(String.valueOf(product.getProductPrice()));

                    TextView txtSmallPrice = findViewById(R.id.tv_small_price);
                    TextView txtMediumPrice = findViewById(R.id.tv_medium_price);
                    TextView txtLargePrice = findViewById(R.id.tv_big_price);
                    //Hiển thị size
                    Map<String, Map<String, Integer>> size = product.getProductSize();
                    for (Map.Entry<String, Map<String, Integer>> entry : size.entrySet()) {
                        String sizeName = entry.getKey();
                        Map<String, Integer> sizeInfo = entry.getValue();
                        // Tìm TextView tương ứng với tên kích thước và cập nhật giá
                        switch (sizeName) {
                            case "Nhỏ":
                                txtSmallPrice.setText(String.valueOf(sizeInfo.get("PRICE")));
                                break;
                            case "Vừa":
                                txtMediumPrice.setText(String.valueOf(sizeInfo.get("PRICE")));
                                break;
                            case "Lớn":
                                txtLargePrice.setText(String.valueOf(sizeInfo.get("PRICE")));
                                break;
                            default:
                                break;
                        }
                    }

                    //Hiển thị danh sách topping

                }
            });

        } else {
            // Xử lý trường hợp intent không có dữ liệu
            Log.d("LoadData", "Failed");
        }

//        rcvTopping = findViewById(R.id.rcv_topping);
//        rcvTopping.setLayoutManager(new LinearLayoutManager(this));
//
//        toppingAdapter = new ToppingAdapter(this, getListTopping());
//        rcvTopping.setAdapter(toppingAdapter);
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
