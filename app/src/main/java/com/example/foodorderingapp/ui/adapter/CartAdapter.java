package com.example.foodorderingapp.ui.adapter;//package com.example.foodorderingapp.UI.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.databinding.BottomsheetEditCartBinding;
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
    private OnItemClickListener listener;

    public CartAdapter(Map<String, OrderItem> orderItemMap, CartViewModel cartViewModel, OnItemClickListener listener){
        this.orderItemMap = orderItemMap;
        this.cartViewModel = cartViewModel;
        this.listener = listener;
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

        //button decrease quanity
        holder.binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
                quantity -= 1;
                if(quantity > 0){
                    orderItem.setQuantity(quantity);
                    notifyDataSetChanged();
                    cartViewModel.onChangeQuantityButtonClick(key, -1 * orderItem.getPrice());
                }
                // show message to confirm delete product or not
                else {
                    showAlertDialog(v.getContext(), key);
                }
            }
        });


        //button increase quanity
        holder.binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.binding.txtQuantity.getText().toString());
                quantity += 1;
                orderItem.setQuantity(quantity);
                notifyDataSetChanged();
                cartViewModel.onChangeQuantityButtonClick(key, 1 * orderItem.getPrice());

            }
        });


        //button edit
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(key, orderItem);
            }
        });

    }

    // Function to show alert dialog when quantity == 0
    public void showAlertDialog(Context context, String orderItemId){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Xóa sản phẩm");
        alert.setMessage("Bạn muốn xóa sản phẩm này?");
        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cartViewModel.deleteProductCart(orderItemId);
            }
        });

        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
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


    public interface OnItemClickListener {
        void onItemClick(String key, OrderItem orderItem);
    }
}

