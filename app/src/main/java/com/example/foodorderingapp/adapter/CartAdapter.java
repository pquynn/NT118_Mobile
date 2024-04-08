package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.OrderDetail;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<OrderDetail> productList;

    public CartAdapter(ArrayList<OrderDetail> productList){
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getProductName());
        holder.productPrice.setText(productList.get(position).getProductPrice());
        holder.productSize.setText(productList.get(position).getProductSize());
//        holder.quantity.setText(productList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productSize;
        TextView quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txt_product_name);
            productPrice = itemView.findViewById(R.id.txt_product_price);
            productSize = itemView.findViewById(R.id.txt_product_size);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
