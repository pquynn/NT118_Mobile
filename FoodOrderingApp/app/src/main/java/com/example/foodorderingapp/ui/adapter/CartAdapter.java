package com.example.foodorderingapp.ui.adapter;//package com.example.foodorderingapp.UI.adapter;

import android.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.R;
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
                if (!isDialogOpen) {
                    showDialog(view.getContext(), orderItem);
                }
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



    // Function to show bottom dialog when button is clicked
    public void showDialog(Context context, OrderItem orderItem) {
        isDialogOpen = true; // Update dialog state
        Dialog dialog = new Dialog(context); // Corrected line
        BottomsheetEditCartBinding binding = BottomsheetEditCartBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());

        binding.setCartVM(cartViewModel); // Set the ViewModel if needed
        binding.setOrderItem(orderItem);


//        RadioGroup radioGroup = binding.radioGroup;
//        // Iterate through radio buttons to find the one with matching text
//        for (int i = 0; i < radioGroup.getChildCount(); i++) {
//            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
//            if (radioButton.getText().toString().equals("Lớn")) {
//                radioButton.setChecked(true);
//                break;
//            }
//        }

        // Radio checked event
//        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // Handle radio button selection here
//                switch (checkedId) {
//                    case R.id.rb_small:
//                        // Handle small size selection
//                        break;
//                    case R.id.rb_medium:
//                        // Handle medium size selection
//                        break;
//                    case R.id.rb_large:
//                        // Handle large size selection
//                        break;
//                }
//            }
//        });


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

