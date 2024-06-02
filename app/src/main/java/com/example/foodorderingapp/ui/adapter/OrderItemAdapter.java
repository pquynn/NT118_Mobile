package com.example.foodorderingapp.ui.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.OrderItem;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_order_detail;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{
    ArrayList<OrderItem> OrderItemList;

    public OrderItemAdapter (ArrayList<OrderItem> orderItemList){
        this.OrderItemList = orderItemList;
    }
    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_item, parent, false);
        return new ViewHolder(orderItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderid.setText(OrderItemList.get(position).getOrderid());

        double priceDB = (double) OrderItemList.get(position).getTotalPrice();
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDB);
        holder.totalPrice.setText(formattedPrice);
        holder.totalDish.setText(String.valueOf(OrderItemList.get(position).getTotalDish()));

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent myIntent = new Intent(context, am_order_detail.class);
                myIntent.putExtra("order_id", OrderItemList.get(position).getOrderid());
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OrderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderid, totalPrice, totalDish;
        Button btn_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.txt_order_id);
            totalPrice = itemView.findViewById(R.id.txt_total_price);
            totalDish = itemView.findViewById(R.id.txt_total_dish);
            btn_detail = itemView.findViewById(R.id.btn_order_detail);
        }
    }
}
