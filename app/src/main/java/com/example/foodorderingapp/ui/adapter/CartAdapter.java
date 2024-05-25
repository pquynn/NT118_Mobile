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
import androidx.core.content.ContextCompat;
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
    private List<Product> productList;
    private OnItemClickListener listener;
    private Context context;

    public CartAdapter(Map<String, OrderItem> orderItemMap, List<Product> productList, CartViewModel cartViewModel, OnItemClickListener listener, Context context){
        this.orderItemMap = orderItemMap;
        this.cartViewModel = cartViewModel;
        this.productList = productList;
        this.listener = listener;
        this.context = context;
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

        // check if product is available
        //1. get size of order item
        String size = (orderItem.getSize() == null || orderItem.getSize().isEmpty()) ? "Mặc định" : orderItem.getSize();
        String productId = orderItem.getIdProduct();

        //2. get product
        Product product = null;
        for (Product p : productList) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        //3. check --> if unavailable --> disable button checkout
        int grey = ContextCompat.getColor(context, R.color.transparent50_gray);
        int dark = ContextCompat.getColor(context, R.color.dark_text);

        holder.binding.btnEdit.setVisibility(View.VISIBLE);
        holder.binding.clEditQuantity.setVisibility(View.VISIBLE);
        holder.binding.clOutOfStock.setVisibility(View.INVISIBLE);
        holder.binding.btnDelete.setVisibility(View.INVISIBLE);
        holder.binding.txtProductCost.setTextColor(dark);
        holder.binding.txtProductName.setTextColor(dark);
        holder.binding.txtProductSize.setTextColor(dark);

        int productQuantity = 0;

        if (product != null) {
            productQuantity = product.getProductSize().get(size).getOrDefault("QUANTITY", 0);
            if (product == null || productQuantity == 0) {
                // if product is out of stock --> change viewholder UI
                cartViewModel.updateTotalPrice(orderItem.getQuantity() * orderItem.getPrice());
                orderItem.setQuantity(0);
                cartViewModel.setIsValidCheckout(false);
                holder.binding.btnEdit.setVisibility(View.INVISIBLE);
                holder.binding.clEditQuantity.setVisibility(View.INVISIBLE);
                holder.binding.clOutOfStock.setVisibility(View.VISIBLE);
                holder.binding.btnDelete.setVisibility(View.VISIBLE);
                holder.binding.txtProductCost.setTextColor(grey);
                holder.binding.txtProductName.setTextColor(grey);
                holder.binding.txtProductSize.setTextColor(grey);

            } else if (productQuantity < orderItem.getQuantity() && productQuantity > 0) {
                // if product quantity is less than order item quantity --> update order item
                orderItem.setQuantity(productQuantity);
//                notifyDataSetChanged();
                cartViewModel.updateOrderItemByOrderId(key);
            }

        }
        //button delete click
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.deleteProductCart(key);
            }
        });


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

