package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.PaymentMethod;

import java.util.ArrayList;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {
    private ArrayList<PaymentMethod> methodList;
    private String selectedPayment;
    private Context context;
    private OnItemClickListener listener;
    private int row_index = -1; // default to no selection

    public PaymentMethodAdapter(Context context, ArrayList<PaymentMethod> methodList, String selectedPayment, OnItemClickListener listener) {
        this.context = context;
        this.methodList = methodList;
        this.selectedPayment = selectedPayment;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_payment_method, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String methodNameStr = methodList.get(position).getMethodName();
        holder.methodName.setText(methodNameStr);
        Glide.with(context)
                .load(methodList.get(position).getMethodImg())
                .into(holder.methodImg);

        // Click event for view holder
        holder.itemView.setOnClickListener(view -> {
            row_index = holder.getAdapterPosition();
            selectedPayment = methodNameStr;
            listener.onItemClick(selectedPayment);
            notifyDataSetChanged();
        });

        // check if there is any address is chosen in checkout, if yes set row_index
        if (selectedPayment.equals(methodNameStr)) {
            row_index = holder.getAdapterPosition();
        }

        // Set foreground based on position of viewholder
        if (row_index == position) {
            holder.container.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_border));
        } else {
            holder.container.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return methodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView methodName;
        ImageView methodImg;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            methodName = itemView.findViewById(R.id.txt_payment);
            methodImg = itemView.findViewById(R.id.img_payment);
            container = itemView.findViewById(R.id.viewholder_payment_method);
        }
    }

    // viewholder on click listener
    public interface OnItemClickListener {
        void onItemClick(String selectedPayment);
    }
}
