package com.example.foodorderingapp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.databinding.ViewholderCouponBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.coupon.CouponViewModel;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private List<Coupon> couponlist;
    private CouponViewModel couponViewModel;
    private OnItemClickListener listener;
    private Boolean isSelected;
    private Coupon selectedCoupon;
    private int row_index;

    public CouponAdapter(Coupon selectedCoupon, List<Coupon> couponlist, CouponViewModel couponViewModel, OnItemClickListener listener) {
        this.couponlist = couponlist;
        this.couponViewModel = couponViewModel;
        this.listener = listener;
        this.selectedCoupon = selectedCoupon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCouponBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_coupon, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coupon coupon = couponlist.get(position);
        holder.bind(coupon);

        holder.itemView.setOnClickListener(view -> {
            row_index = holder.getBindingAdapterPosition();
            isSelected = true;
            selectedCoupon = coupon;
            listener.onItemClick(isSelected, selectedCoupon);
            notifyDataSetChanged();
        });

        // check if there is any coupon is chosen in checkout, if yes set row_index
        if (selectedCoupon.getIdCoupon().equals(coupon.getIdCoupon())) {
            row_index = holder.getBindingAdapterPosition();
            isSelected = true;
        }
        else{
            row_index = -1; //todo; chưa có set đúng row_index trong trường hợp coupon đã được áp dụng
            isSelected = false;
        }

        // set foreground for selected coupon viewholder
        if (row_index == position) {
            holder.binding.viewholderCoupon.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_border));
        } else {
            holder.binding.viewholderCoupon.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return couponlist != null ? couponlist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderCouponBinding binding;

        public ViewHolder(@NonNull ViewholderCouponBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coupon coupon) {
            binding.setCoupon(coupon);
            binding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Boolean isSelected, Coupon selectedCoupon);
    }
}
