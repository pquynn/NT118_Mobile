package com.example.javajoyadmin.ui.adapter;

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

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.OrderItem;
import com.example.javajoyadmin.databinding.ViewholderOrderDetailBinding;
import com.example.javajoyadmin.ui.activityfragment.admin.activity_adminOrderDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdminOrderItemAdapter extends RecyclerView.Adapter<AdminOrderItemAdapter.ViewHolder> {

    private ArrayList<OrderItem> OrderItemList;

    public AdminOrderItemAdapter(ArrayList<OrderItem> orderItemList) {
        this.OrderItemList = orderItemList;
    }

    private static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_item, parent, false);
        return new ViewHolder(orderItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderid.setText(OrderItemList.get(position).getOrderid());
        holder.totalPrice.setText(formatNumber(OrderItemList.get(position).getOrderPrice()));
        holder.totalDish.setText(String.valueOf(OrderItemList.get(position).getTotalDish()));

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent myIntent = new Intent(context, activity_adminOrderDetail.class);
                myIntent.putExtra("order_id", OrderItemList.get(position).getOrderid());
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OrderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
