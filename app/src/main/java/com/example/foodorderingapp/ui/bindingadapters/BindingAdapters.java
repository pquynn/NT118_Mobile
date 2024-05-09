package com.example.foodorderingapp.ui.bindingadapters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

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

}
