package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.ViewholderOrderDetailBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private Map<String, OrderItem> orderItemMap;

    public OrderDetailAdapter(Map<String, OrderItem> orderItemMap){
        this.orderItemMap = orderItemMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderOrderDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_order_detail, parent, false);
        return new OrderDetailAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(orderItemMap.keySet());
        String key = keys.get(position);
        OrderItem orderItem = orderItemMap.get(key);
        holder.bind(orderItem);

    }

    @Override
    public int getItemCount() {
        return orderItemMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderOrderDetailBinding binding;

        public ViewHolder(@NonNull ViewholderOrderDetailBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderItem orderItem){
            binding.setOrderItem(orderItem);
            binding.executePendingBindings();
        }
    }
}
