package com.example.foodorderingapp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Coupon;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.UserAddress;
import com.example.foodorderingapp.databinding.ViewholderAddressBinding;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.checkout.CheckoutAddressViewModel;

import java.util.List;

public class CheckoutAddressAdapter extends RecyclerView.Adapter<CheckoutAddressAdapter.ViewHolder> {
    private List<UserAddress> addresslist;
    private UserAddress selectedAddress;
    private CheckoutAddressViewModel viewModel;
    private OnItemClickListener listener;
    private int row_index; // position of selected viewholder
    public CheckoutAddressAdapter(UserAddress selectedAddress, List<UserAddress> addresslist, CheckoutAddressViewModel viewModel, OnItemClickListener listener){
        this.addresslist = addresslist;
        this.viewModel = viewModel;
        this.selectedAddress = selectedAddress;
        this.listener = listener;
    }

    public void setSelectedAddress(UserAddress userAddress){
        selectedAddress = userAddress;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderAddressBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_address, parent, false);
        return new CheckoutAddressAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserAddress userAddress = addresslist.get(position);
        holder.bind(userAddress);

        // HIDE EDIT, DELETE BUTTON FROM EACH ADDRESS CONTAINER
        holder.binding.btnDelete.setVisibility(View.GONE);
        holder.binding.btnEdit.setVisibility(View.GONE);

        // Click event for view holder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = holder.getBindingAdapterPosition();
                selectedAddress = userAddress;
                listener.onItemClick(selectedAddress);
                notifyDataSetChanged();
            }
        });

        // check if there is any address is chosen in checkout, if yes set row_index
        if (selectedAddress.getId().equals(userAddress.getId())) {
            row_index = holder.getBindingAdapterPosition();
        }
        else{
            row_index = -1;
        }

        // Set foreground based on position of viewholder
        if (row_index == position) {
            //add foreground: solid_line
            holder.binding.viewholderAddress.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_border));
        } else {
            holder.binding.viewholderAddress.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderAddressBinding binding;

        public ViewHolder(@NonNull ViewholderAddressBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(UserAddress userAddress){
            binding.setUserAddress(userAddress);
            binding.executePendingBindings();
        }
    }

    // viewholder on click listener
    public interface OnItemClickListener {
        void onItemClick(UserAddress selectedAddress);
    }
}
