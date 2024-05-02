package com.example.foodorderingapp.ui.activityfragment.customer.productdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.ui.adapter.ToppingAdapter;
import com.example.foodorderingapp.data.model.entity.Comment;
import com.example.foodorderingapp.data.model.entity.Topping;

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

        List<Comment> listComment = new ArrayList<>();
        listComment.add(new Comment("1", "1", "Bảo Ngọc", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 5, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 4, "Chất lượng, ngon tuyệt vời", date));
        listComment.add(new Comment("1", "1", "Ngọc Bảo", 3, "Chất lượng, ngon tuyệt vời", date));

        CommentDialogFragment commentDialogFragment = new CommentDialogFragment(listComment);
        commentDialogFragment.show(getSupportFragmentManager(), commentDialogFragment.getTag());
    }

    public void onRadioButtonClicked(@NonNull View view) {
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton radioButtonLarge = findViewById(R.id.radioButtonLarge);
        RadioButton radioButtonMedium = findViewById(R.id.radioButtonMedium);
        RadioButton radioButtonSmall = findViewById(R.id.radioButtonSmall);

        // Huỷ chọn tất cả các RadioButton ngoại trừ RadioButton được chọn
        if (view.getId() == R.id.radioButtonLarge) {
            if (checked) {
                radioButtonMedium.setChecked(false);
                radioButtonSmall.setChecked(false);
            }
        } else if (view.getId() == R.id.radioButtonMedium) {
            if (checked) {
                radioButtonLarge.setChecked(false);
                radioButtonSmall.setChecked(false);
            }
        } else if (view.getId() == R.id.radioButtonSmall) {
            if (checked) {
                radioButtonLarge.setChecked(false);
                radioButtonMedium.setChecked(false);
            }
        }
    }

    @NonNull
    private List<Topping> getListTopping() {
        List<Topping> listTopping = new ArrayList<>();

        listTopping.add(new Topping("Trân châu trắng", "5.000đ"));
        listTopping.add(new Topping("Trân châu đen", "5.000đ"));
        listTopping.add(new Topping("Bánh Flan", "7.000đ"));
        listTopping.add(new Topping("Thạch dừa phô mai", "10.000đ"));
        return listTopping;
    }
}
