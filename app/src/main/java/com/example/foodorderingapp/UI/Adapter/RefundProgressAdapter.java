package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.OrderDetail;
import java.util.ArrayList;

public class RefundProgressAdapter extends RecyclerView.Adapter<RefundProgressAdapter.ViewHolder> {
    private ArrayList<OrderDetail> productList;

    public RefundProgressAdapter(ArrayList<OrderDetail> productList){
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_refund_progress, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(productList.get(position).getProductPrice()));
        holder.productSize.setText(productList.get(position).getProductSize());
        holder.note.setText(productList.get(position).getNote());
//        holder.quantity.setText(productList.get(position).getQuantity());
    }

    //    function to show bottom dialog when button is clicked

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productSize;
        TextView note;
        TextView quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txt_product_name);
            productPrice = itemView.findViewById(R.id.txt_product_cost);
            productSize = itemView.findViewById(R.id.txt_product_size);
            note = itemView.findViewById(R.id.txt_note);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
