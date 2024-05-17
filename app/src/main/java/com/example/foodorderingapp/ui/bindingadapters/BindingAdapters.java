package com.example.foodorderingapp.ui.bindingadapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.data.model.entity.Coupon;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingAdapters {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .into(imageView);
        }
    }

    @BindingAdapter("dateFormatted")
    public static void setDateFormatted(TextView textView, Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(date);
            textView.setText(formattedDate);
        }
    }

    @BindingAdapter("priceFormatted")
    public static void setPriceFormatted(TextView textView, int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
//        formattedPrice = formattedPrice.replace(",", ".");
        textView.setText(formattedPrice);
    }


    @BindingAdapter("pointFormatted")
    public static void setPointFormatted(TextView textView, int point) {
        textView.setText(String.valueOf(point) + " điểm");
    }

    @BindingAdapter("productNumbFormatted")
    public static void setProductNumbFormatted(TextView textView, int numb) {
        textView.setText("(" + String.valueOf(numb) + " món" + ")");
    }

    @BindingAdapter("selectedCoupon")
    public static void setSelectedCoupon(TextView view, int discountValue) {
        String value = "";
        if(discountValue != 0){
            value = new DecimalFormat("#,###").format(discountValue);
        }
        view.setText(value);
    }

    // using in coupon activity: after viewholder coupon is clicked --> show bar with discount value
    @BindingAdapter("visibleSelectedBar")
    public static void visibleSelectedBar(ConstraintLayout view, boolean isSelected) {
        if (!isSelected) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("textInSelectedBar")
    public static void setTextInSelectedBar(TextView textView, int price) {
        String formattedPrice = new DecimalFormat("#,### đ").format(price);
        textView.setText("1 mã giảm giá được áp dụng: " + formattedPrice);
    }

    @BindingAdapter("visibleIfNotEmpty")
    public static void visibleIfNotEmpty(TextView view, String text) {
        if (text == null || text.isEmpty()) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }
}
