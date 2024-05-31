package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.databinding.ViewholderRefundProgressBinding;
import com.example.foodorderingapp.ui.activityfragment.customer.refund.MediaFullScreenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RefundItemAdapter extends RecyclerView.Adapter<RefundItemAdapter.ViewHolder> {
    private Map<String, OrderItem> orderItemMap;
    private Map<String, RefundItem> refundItemMap;
    private Context context;

    public RefundItemAdapter(Map<String, OrderItem> orderItemMap, Map<String, RefundItem> refundItemMap, Context context){
        this.orderItemMap = orderItemMap;
        this.refundItemMap = refundItemMap;
        this.context = context;
    }

    @Override
    public RefundItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderRefundProgressBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_refund_progress, parent, false);
        return new RefundItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RefundItemAdapter.ViewHolder holder, int position) {
        // refund item id == order item id
        List<String> keys = new ArrayList<>(refundItemMap.keySet());
        String key = keys.get(position);
        RefundItem refundItem = refundItemMap.get(key);
        OrderItem orderItem = orderItemMap.get(key);
        holder.bind(orderItem, refundItem);

        //TODO: XỬ LÝ TRƯỜNG HỢP IMAGE VIEW, VIDEO VIEW CLICK EVENT (MỞ ACTIVITY MỚI?)
        holder.binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaFullScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isImage", true);
                bundle.putString("mediaUrl", refundItem.getProofImage());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.binding.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaFullScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isImage", false);
                bundle.putString("mediaUrl", refundItem.getProofVideo());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return refundItemMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderRefundProgressBinding binding;

        public ViewHolder(@NonNull ViewholderRefundProgressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderItem orderItem, RefundItem refundItem) {
            binding.setOrderItem(orderItem);
            binding.setRefundItem(refundItem);
            binding.executePendingBindings();
        }
    }
}
