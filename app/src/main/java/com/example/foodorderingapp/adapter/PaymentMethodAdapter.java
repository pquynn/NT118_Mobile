package com.example.foodorderingapp.adapter;

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
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.PaymentMethod;

import java.util.ArrayList;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {
    ArrayList<PaymentMethod> methodList;
    Context context;
    private int row_index = 0; // position of selected viewholder
    public PaymentMethodAdapter(Context context, ArrayList<PaymentMethod> methodList){
        this.context = context;
        this.methodList = methodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_payment_method, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.methodName.setText(methodList.get(position).getMethodName());
        Glide.with(context)
                .load(methodList.get(position).getMethodImg())
                .into(holder.methodImg);

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
            holder.container.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.solid_line_rounded_border));
        } else {
            holder.container.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return methodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
}
