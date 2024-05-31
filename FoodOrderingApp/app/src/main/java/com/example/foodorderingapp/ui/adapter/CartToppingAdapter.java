package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.databinding.ViewholderToppingCartBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class CartToppingAdapter extends RecyclerView.Adapter<CartToppingAdapter.ViewHolder> {

    private CartViewModel viewModel;
    private List<Topping> toppings;
    private List<String> toppingNames, selectedToppingNames;
    private OnCheckedChangeListener listener;

    // Constructor
    public CartToppingAdapter(List<Topping> toppings,
                              List<String> toppingNames,
                              List<String> selectedToppingNames,
                              CartViewModel viewModel,
                              OnCheckedChangeListener listener) {
        this.toppings = toppings;
        this.toppingNames = toppingNames;
        this.selectedToppingNames = selectedToppingNames;
        this.viewModel = viewModel;
        this.listener = listener;
    }

//    public void setSelectedToppings(List<String> selectedToppings) {
//        this.selectedToppingNames.clear();
//        this.selectedToppingNames.addAll(selectedToppings);
//    }

    @Override
    public CartToppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderToppingCartBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_topping_cart, parent, false);
        return new CartToppingAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartToppingAdapter.ViewHolder holder, int position) {
        if(!toppingNames.isEmpty()) {
            String toppingName = toppingNames.get(position);
            holder.bind(toppingName);
            holder.binding.checkbox.setText(toppingName);

            // Set checkbox state based on selected topping names
            holder.binding.checkbox.setChecked(selectedToppingNames.contains(toppingName));

            // Find the topping by name and set its price
            Topping topping = findToppingByName(toppingName);
            if (topping != null) {
                setPriceFormatted(holder.binding.tvToppingPrice, topping.getPriceTopping());
            }

            // Set checkbox listener
            holder.binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onItemCheckedChanged(isChecked, topping);
                }
            });
        }
    }

    // Method to find topping by name
    private Topping findToppingByName(String toppingName) {
        for (Topping topping : toppings) {
            if (toppingName.equals(topping.getNameTopping())) {
                return topping;
            }
        }
        return null;
    }

    public static void setPriceFormatted(TextView textView, int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        textView.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        return toppingNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderToppingCartBinding binding;

        public ViewHolder(@NonNull ViewholderToppingCartBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String toppingName){
            binding.setToppingName(toppingName);
            binding.executePendingBindings();
        }
    }

    public interface OnCheckedChangeListener {
        void onItemCheckedChanged(boolean isChecked, Topping topping);
    }

}
