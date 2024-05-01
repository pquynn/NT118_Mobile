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

import com.example.foodorderingapp.data.model.BuyingProduct;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
//import com.example.foodorderingapp.databinding.ViewholderCartBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
//    private Map<String, BuyingProduct> orderItemMap = new HashMap<>();
    private Map<String, OrderItem> orderItemMap = new HashMap<>();
    private Map<String, BuyingProduct> productMap = new HashMap<>();
    private boolean isDialogOpen = false;
    private View.OnClickListener onClickListener;

    public CartAdapter( Map<String, BuyingProduct> productMap){
        this.productMap = productMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
//        return new ViewHolder(inflate);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCartBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_cart, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(productMap.keySet());
        String key = keys.get(position);
//        OrderItem orderItem = orderItemMap.get(key);
        BuyingProduct product = productMap.get(key);
//        holder.productPrice.setText(String.valueOf(orderItem.getPrice()));
//        holder.productSize.setText(orderItem.getSize());
//        holder.binding.setOrderItem(orderItem);
//        holder.binding.executePendingBindings();
        holder.bind(product);
//                holder.productName.setText(orderItem.getIdProduct());
//        holder.productPrice.setText(String.valueOf(orderItem.getPrice()));
//        holder.productSize.setText(orderItem.getSize());
//        holder.quantity.setText(String.valueOf(orderItemMap.get(position).getQuantity()));

//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isDialogOpen) {
//                    showDialog(view.getContext());
//                }
//            }
//        });
    }

    // Your existing code

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
        TextView productName;
        TextView productPrice;
        TextView productSize;
        TextView quantity;
        ImageView btnEdit;
        private ViewholderCartBinding binding;

//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            productName = itemView.findViewById(R.id.txt_product_name);
//            productPrice = itemView.findViewById(R.id.txt_product_cost);
//            productSize = itemView.findViewById(R.id.txt_product_size);
////            quantity = itemView.findViewById(R.id.tv_quantity);
//            btnEdit = itemView.findViewById(R.id.btn_edit);
//        }

        public ViewHolder(@NonNull ViewholderCartBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BuyingProduct buyingProduct){
//            binding.setOrderItem(orderItem);
            binding.setBuyingProduct(buyingProduct);
            binding.executePendingBindings();
        }
    }
}


//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.content.DialogInterface;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.foodorderingapp.R;
//import com.example.foodorderingapp.data.model.OrderDetail;
//import com.example.foodorderingapp.data.model.entity.OrderItem;
//
//import java.util.ArrayList;
//
//public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
//    private ArrayList<OrderItem> orderItemMap;
//    private boolean isDialogOpen = false;
//    private View.OnClickListener onClickListener;
//
//    public CartAdapter(ArrayList<OrderItem> orderItemMap){
//        this.orderItemMap = orderItemMap;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
//        return new ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
////        holder.productName.setText(orderItemMap.get(position).getProductName());
////        holder.productPrice.setText(String.valueOf(orderItemMap.get(position).getProductPrice()));
////        holder.productSize.setText(orderItemMap.get(position).getProductSize());
////        holder.quantity.setText(String.valueOf(orderItemMap.get(position).getQuantity()));
//
//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isDialogOpen) {
//                    showDialog(view.getContext());
//                }
//            }
//        });
//    }
//
//    // Your existing code
//
//    // Function to show bottom dialog when button is clicked
//    public void showDialog(Context context) {
//        isDialogOpen = true; // Update dialog state
//        Dialog dialog = new Dialog(context); // Corrected line
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.bottomsheet_edit_cart_drink);
//
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                isDialogOpen = false; // Update dialog state when dismissed
//            }
//        });
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//    }
//
//    @Override
//    public int getItemCount() {
//        return orderItemMap.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView productName;
//        TextView productPrice;
//        TextView productSize;
//        TextView quantity;
//        ImageView btnEdit;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            productName = itemView.findViewById(R.id.txt_product_name);
//            productPrice = itemView.findViewById(R.id.txt_product_cost);
//            productSize = itemView.findViewById(R.id.txt_product_size);
////            quantity = itemView.findViewById(R.id.tv_quantity);
//            btnEdit = itemView.findViewById(R.id.btn_edit);
//        }
//    }
//}