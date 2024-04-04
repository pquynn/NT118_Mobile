package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.CouponDomain;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    ArrayList<CouponDomain> couponlist;

    public CouponAdapter(ArrayList<CouponDomain> couponlist){
        this.couponlist = couponlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_coupon, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.couponName.setText(couponlist.get(position).getCouponName());
        holder.description.setText(couponlist.get(position).getDescription());
        holder.valid_date.setText(couponlist.get(position).getValidFrom().toString());
    }

    @Override
    public int getItemCount() {
        return couponlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView couponName;
        TextView description;
        TextView valid_date;
        ConstraintLayout mainlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.txt_coupon_name);
            description = itemView.findViewById(R.id.txt_description);
            valid_date = itemView.findViewById(R.id.txt_valid_date);
        }
    }
}
