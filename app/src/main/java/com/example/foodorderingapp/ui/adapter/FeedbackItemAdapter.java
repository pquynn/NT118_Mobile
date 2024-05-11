package com.example.foodorderingapp.ui.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.OrderDetail;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_my_orders;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_order_detail;

import java.util.ArrayList;

public class FeedbackItemAdapter extends RecyclerView.Adapter<FeedbackItemAdapter.ViewHolder>{
    ArrayList<OrderItem> productList;

    public FeedbackItemAdapter(ArrayList<OrderItem> productList){
        this.productList = productList;
    }

    @Override
    public FeedbackItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_feedback_item, parent, false);
        return new FeedbackItemAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackItemAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(productList.get(position).getPrice()));
        holder.productSize.setText(productList.get(position).getSize());
        holder.note.setText(productList.get(position).getNote());
        holder.quantity.setText(String.valueOf(productList.get(position).getQuantity()));
        Glide.with(holder.itemView.getContext())
                .load(productList.get(position).getProductImage())
                .into(holder.productImage);

        ArrayList<String> listTopping = productList.get(position).getTopping();
        if(!listTopping.isEmpty()){
            String toppings = "";
            int dem = 0;
            for(String i : listTopping){
                toppings += i;
                dem++;
                if(dem < listTopping.size()){
                    toppings += ", ";
                }
            }
            holder.topping.setText(toppings);
        }else{
            holder.topping.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productSize, note, quantity;
        TextView topping;
        ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txt_product_name);
            productPrice = itemView.findViewById(R.id.txt_product_cost);
            productSize = itemView.findViewById(R.id.txt_product_size);
            note = itemView.findViewById(R.id.txt_note);
            quantity = itemView.findViewById(R.id.txt_quantity);
            productImage = itemView.findViewById(R.id.img_fb_product);
            topping = itemView.findViewById(R.id.txt_fb_topping);
        }
    }
}
