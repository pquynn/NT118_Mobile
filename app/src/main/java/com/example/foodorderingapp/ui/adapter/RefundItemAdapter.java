package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.RefundItem;
import com.example.foodorderingapp.databinding.ViewholderRefundProgressBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RefundItemAdapter extends RecyclerView.Adapter<RefundItemAdapter.ViewHolder> {
    private Map<String, OrderItem> orderItemMap;
    private Map<String, RefundItem> refundItemMap;
    private boolean isImageFitToScreen = false;

    public RefundItemAdapter(Map<String, OrderItem> orderItemMap, Map<String, RefundItem> refundItemMap){
        this.orderItemMap = orderItemMap;
        this.refundItemMap = refundItemMap;
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
//        holder.binding.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(isImageFitToScreen) {
////                    isImageFitToScreen=false;
////                    holder.binding.imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
////                    holder.binding.imageView.setAdjustViewBounds(true);
////                }else{
////                    isImageFitToScreen=true;
////                    holder.binding.imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
////                    holder.binding.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
////                }
//            }
//        });
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
