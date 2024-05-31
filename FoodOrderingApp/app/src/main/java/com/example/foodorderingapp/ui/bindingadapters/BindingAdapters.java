package com.example.foodorderingapp.ui.bindingadapters;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
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

    @BindingAdapter({"videoUrl"})
    public static void setVideoUrl(VideoView view, String url) {
        if (url != null && !url.isEmpty()) {
            Uri uri = Uri.parse(url);
            view.setVideoURI(uri);
            view.start();
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

    @BindingAdapter("visibleLinearLayout")
    public static void visibleLinearLayout(LinearLayout view, int value) {
        if (value == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
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

    @BindingAdapter("refundInstruction")
    public static void setRefundInstruction(TextView view, String status) {

        if(status != null && status.equals("Chờ hoàn tiền"))
            view.setText("Yêu cầu hoàn tiền đã được xác nhận. Cửa hàng sẽ liên hệ với bạn qua điện thoại trong vòng 24 giờ để xử lý hoàn tiền.");
        else
            view.setText("Hoàn tiền thành công. Số tiền hoàn đã được chuyển đến bạn.");
    }

    @BindingAdapter("visiblerefundInstruction")
    public static void visibleLinearLayout(LinearLayout view, String status) {
        if (status != null && (status.equals("Chờ xác nhận") || status.equals("Từ chối hoàn tiền"))) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("visibleDateProgressByStatus")
    public static void visibleDateProgressByStatus(LinearLayout view, String status) {
        if (status != null && (status.equals("Chờ xác nhận"))) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("visibleAcceptButtonByStatus")
    public static void visibleAcceptButtonByStatus(Button button, String status) {
        if(status != null){
            switch (status){
                case "Chờ xác nhận":{
                    button.setVisibility(View.VISIBLE);
                    button.setText("Chấp nhận hoàn tiền");
                    break;
                }
                case "Chờ hoàn tiền":{
                    button.setVisibility(View.VISIBLE);
                    button.setText("Đã hoàn tiền");
                    break;
                }
                case "Đã hoàn tiền":{
                    button.setVisibility(View.GONE);
                }
                default:{
                    break;
                }
            }

        }
    }

    @BindingAdapter("visibleRefuseButtonByStatus")
    public static void visibleRefuseButtonByStatus(Button button, String status) {
        if(status != null){
            if(status.equals("Chờ xác nhận")){
                button.setVisibility(View.VISIBLE);
            }
            else {
                button.setVisibility(View.GONE);
            }
        }
    }
}
