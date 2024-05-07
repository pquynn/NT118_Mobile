package com.example.foodorderingapp.ui.adapter;//package com.example.foodorderingapp.UI.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;
//import com.example.foodorderingapp.databinding.ViewholderCartBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private CartViewModel cartViewModel;
    private Map<String, OrderItem> orderItemMap;
    private boolean isDialogOpen = false;

    public CartAdapter(Map<String, OrderItem> orderItemMap, CartViewModel cartViewModel){
        this.orderItemMap = orderItemMap;
        this.cartViewModel = cartViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCartBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_cart, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Map<String, OrderItem> orderItemMap = cartViewModel.getOrderMutableLiveData().getValue().getOrderItem();
        List<String> keys = new ArrayList<>(orderItemMap.keySet());
        String key = keys.get(position);
        OrderItem orderItem = orderItemMap.get(key);
        holder.bind(orderItem);

        //button edit
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDialogOpen) {
                    showDialog(view.getContext());
                }
            }
        });

        //button increase, decrease
        holder.binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
                if(quantity > 0){
                    quantity -= 1;
                    holder.binding.txtQuantity.setText(String.valueOf(quantity)); // Convert int to String
                    cartViewModel.onChangeQuantityButtonClick(key, quantity);
                }
            }
        });

        holder.binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
                quantity += 1;
                holder.binding.txtQuantity.setText(String.valueOf(quantity)); // Convert int to String
                cartViewModel.onChangeQuantityButtonClick(key, quantity);
            }
        });

    }



    // Function to show bottom dialog when button is clicked
    public void showDialog(Context context) {
        isDialogOpen = true; // Update dialog state
        Dialog dialog = new Dialog(context); // Corrected line
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_edit_cart_drink);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isDialogOpen = false; // Update dialog state when dismissed
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public int getItemCount() {
        return orderItemMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderCartBinding binding;

        public ViewHolder(@NonNull ViewholderCartBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderItem orderItem){
            binding.setOrderItem(orderItem);
            binding.executePendingBindings();
        }
    }
}

