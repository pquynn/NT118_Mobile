package com.example.foodorderingapp.UI.Activity_Fragment.Customer.ProductDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.Data.Model.Entity.Product;
import com.example.foodorderingapp.Data.Repository.Product.IProductRepository;
import com.example.foodorderingapp.Data.Repository.Product.ProductRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.Entity.Comment;
import com.example.foodorderingapp.Data.Model.Entity.Topping;
import com.example.foodorderingapp.UI.Adapter.ToppingAdapter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductDetailDrinkActivity extends AppCompatActivity{
    private TextView txt_NameDrink, txt_priceDrink, txt_descriptionProduct, txt_size, txt_topping;
    private ImageView img_drink;
    private RadioGroup radioGroupSizes;
    private RecyclerView rcv_topping;
    private ToppingAdapter toppingAdapter;
    private ImageView imgComment;
    private TextView contentTextView, showMoreTextView, showLessTextView;
    private CharSequence originalText;
    private int originalMaxLines;
    private ProductRepository productRepository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail_drink);

        txt_NameDrink = findViewById(R.id.txt_NameDrink);
        txt_priceDrink = findViewById(R.id.txt_priceDrink);
        txt_descriptionProduct = findViewById(R.id.contentTextView);
        txt_size = findViewById(R.id.txt_depSize);
        txt_topping = findViewById(R.id.txt_depTopping);
        img_drink = findViewById(R.id.img_drink);
        rcv_topping = findViewById(R.id.rcv_topping);
        rcv_topping.setLayoutManager(new LinearLayoutManager(this));

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

//        toppingAdapter = new ToppingAdapter(this, getListTopping());
//        rcvTopping.setAdapter(toppingAdapter);

        productRepository = new ProductRepository();
        // Lấy productId từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String productId = intent.getStringExtra("PRODUCT_ID");
            // Gọi hàm để lấy thông tin sản phẩm từ Firestore
            getProductDetails(productId);
        }
    }

    private void getProductDetails(String productId) {
        productRepository.getProductDetails(productId, new IProductRepository.ProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                displayProductDetails(product);
            }

            @Override
            public void onProductLoadFailed(String errorMessage) {
                Toast.makeText(ProductDetailDrinkActivity.this,"Failed to load product details: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(Product product) {
        Glide.with(this).load(product.getProductImage()).into(img_drink);
        txt_NameDrink.setText(product.getProductName());
        txt_priceDrink.setText(String.format(Locale.getDefault(), "%dđ", product.getProductPrice()));
        txt_descriptionProduct.setText(product.getProductInfo());

//        // Nếu có kích thước thì hiển thị thông tin kích thước
//        if (product.getProductSize() != null && !product.getProductSize().isEmpty()) {
//            txt_size.setVisibility(View.VISIBLE);
//            // Hiển thị thông tin kích thước
//            // (Việc này cần bạn tự triển khai tùy vào cách hiển thị của bạn, có thể là TextView hoặc RecyclerView)
//            radioGroupSizes.removeAllViews();
//
//            // Lặp qua danh sách kích thước và thêm các RadioButton vào RadioGroup
//            for (String size : product.getProductSize()) {
//                RadioButton radioButton = new RadioButton(this);
//                radioButton.setText(size);
//                radioButton.setId(View.generateViewId()); // Đặt id cho RadioButton
//                radioGroupSizes.addView(radioButton);
//            }
//            // Lắng nghe sự kiện khi RadioButton được chọn
//            radioGroupSizes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                    // Xử lý khi có RadioButton được chọn
//                    RadioButton radioButton = findViewById(checkedId);
//                    if (radioButton != null) {
//                        String selectedSize = radioButton.getText().toString();
//                        // Thực hiện các hành động khi kích thước được chọn
//                    }
//                }
//            });
//        } else {
//            txt_size.setVisibility(View.GONE);
//            // Ẩn RadioGroup khi không có kích thước
//            radioGroupSizes.setVisibility(View.GONE);
//        }

        // Nếu có danh sách topping thì hiển thị thông tin topping
        if (product.getTopping() != null && !product.getTopping().isEmpty()) {
            txt_topping.setVisibility(View.VISIBLE);
            // Hiển thị danh sách topping
            // (Tương tự như việc hiển thị kích thước, bạn cần tự triển khai tùy vào cách hiển thị của bạn)
        }
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

//    public void onRadioButtonClicked(@NonNull View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        RadioButton radioButtonLarge = findViewById(R.id.radioButtonLarge);
//        RadioButton radioButtonMedium = findViewById(R.id.radioButtonMedium);
//        RadioButton radioButtonSmall = findViewById(R.id.radioButtonSmall);
//
//        // Huỷ chọn tất cả các RadioButton ngoại trừ RadioButton được chọn
//        if (view.getId() == R.id.radioButtonLarge) {
//            if (checked) {
//                radioButtonMedium.setChecked(false);
//                radioButtonSmall.setChecked(false);
//            }
//        } else if (view.getId() == R.id.radioButtonMedium) {
//            if (checked) {
//                radioButtonLarge.setChecked(false);
//                radioButtonSmall.setChecked(false);
//            }
//        } else if (view.getId() == R.id.radioButtonSmall) {
//            if (checked) {
//                radioButtonLarge.setChecked(false);
//                radioButtonMedium.setChecked(false);
//            }
//        }
//    }

//    @NonNull
//    private List<Topping> getListTopping() {
//        List<Topping> listTopping = new ArrayList<>();
//
//        listTopping.add(new Topping("Trân châu trắng", "5.000đ"));
//        listTopping.add(new Topping("Trân châu đen", "5.000đ"));
//        listTopping.add(new Topping("Bánh Flan", "7.000đ"));
//        listTopping.add(new Topping("Thạch dừa phô mai", "10.000đ"));
//        return listTopping;
//    }
}
