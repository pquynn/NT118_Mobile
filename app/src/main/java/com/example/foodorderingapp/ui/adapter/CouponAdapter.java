package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.databinding.ViewholderCouponBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.coupon.CouponViewModel;

import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    List<Coupon> couponlist;
    CouponViewModel couponViewModel;

    private int row_index = -1; // position of selected viewholder
    public CouponAdapter(List<Coupon> couponlist, CouponViewModel couponViewModel){
        this.couponlist = couponlist;
        this.couponViewModel = couponViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCouponBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_coupon, parent, false);
        return new CouponAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coupon coupon = couponlist.get(position);
        holder.binding.setCoupon(coupon);

        // Click event for view holder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        // Set foreground based on position of viewholder
        if (row_index == position) {
            //add foreground: solid_line
            holder.binding.viewholderCoupon.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_border));
        } else {
            holder.binding.viewholderCoupon.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return couponlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCouponBinding binding;
        public ViewHolder(@NonNull ViewholderCouponBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coupon coupon){
            binding.setCoupon(coupon);
            binding.executePendingBindings();
        }
    }
}
