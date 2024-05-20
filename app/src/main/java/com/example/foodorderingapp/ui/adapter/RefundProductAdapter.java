package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.ViewholderRefundProdBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RefundProductAdapter extends RecyclerView.Adapter<RefundProductAdapter.ViewHolder> {
    private Map<String, OrderItem> orderItemMap;
    private List<String> selectedOrderItemIds = new ArrayList<>();
    private OnItemClickListener listener;

    public RefundProductAdapter(Map<String, OrderItem> orderItemMap, OnItemClickListener listener) {
        this.orderItemMap = orderItemMap;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RefundProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderRefundProdBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_refund_prod, parent, false);
        return new RefundProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RefundProductAdapter.ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(orderItemMap.keySet());
        String key = keys.get(position);
        OrderItem orderItem = orderItemMap.get(key);
        holder.bind(orderItem, key);
    }

    @Override
    public int getItemCount() {
        return orderItemMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderRefundProdBinding binding;

        public ViewHolder(@NonNull ViewholderRefundProdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderItem orderItem, String key) {
            binding.setOrderItem(orderItem);
            binding.executePendingBindings();

            // Set initial state of checkbox
            binding.checkBox.setChecked(selectedOrderItemIds.contains(key));

            // Checkbox check event
            binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!selectedOrderItemIds.contains(key)) {
                        selectedOrderItemIds.add(key);
                    }
                } else {
                    selectedOrderItemIds.remove(key);
                }
                listener.onItemClick(selectedOrderItemIds);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(List<String> selectedOrderItemIds);
    }
}
