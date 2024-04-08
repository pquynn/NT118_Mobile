package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.OrderItemDomain;


import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{
    ArrayList<OrderItemDomain> OrderItemList;

    public OrderItemAdapter (ArrayList<OrderItemDomain> orderItemList){
        this.OrderItemList = orderItemList;
    }
    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_item, parent, false);
        return new ViewHolder(orderItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.ViewHolder holder, int position) {
        holder.orderid.setText(OrderItemList.get(position).getOrderid());
        holder.totalPrice.setText(String.valueOf(OrderItemList.get(position).getTotalPrice()));
        holder.totalDish.setText(String.valueOf(OrderItemList.get(position).getTotalDish()));
    }

    @Override
    public int getItemCount() {
        return OrderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderid, totalPrice, totalDish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.txt_order_id);
            totalPrice = itemView.findViewById(R.id.txt_total_price);
            totalDish = itemView.findViewById(R.id.txt_total_dish);
        }
    }
}
