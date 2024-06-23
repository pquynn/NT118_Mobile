package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.ui.viewmodel.customer.home.BuyNowViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class BuyNowToppingAdapter extends RecyclerView.Adapter<BuyNowToppingAdapter.ViewHolder> {
    private BuyNowViewModel viewModel;
    private List<Topping> toppings;
    private List<String> toppingNames, selectedToppingNames;
    private OnCheckedChangeListener listener;
    private Context context;

    public BuyNowToppingAdapter(Context context,
                                List<Topping> toppings,
                                List<String> toppingNames,
                                List<String> selectedToppingNames,
                                BuyNowViewModel viewModel,
                                OnCheckedChangeListener listener) {
        this.context = context;
        this.toppings = toppings;
        this.toppingNames = toppingNames;
        this.selectedToppingNames = selectedToppingNames;
        this.viewModel = viewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_topping_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String toppingName = toppingNames.get(position);
        holder.bind(toppingName);

        holder.checkbox.setChecked(selectedToppingNames.contains(toppingName));

        // Find the topping by name and set its price
        Topping topping = findToppingByName(toppingName);
        if (topping != null) {
            setPriceFormatted(holder.tvToppingPrice, topping.getPriceTopping());
        }

        // Set checkbox listener
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onItemCheckedChanged(isChecked, topping);
            }
        });
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
        private TextView tvToppingPrice;
        private CompoundButton checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingPrice = itemView.findViewById(R.id.tv_topping_price);
            checkbox = itemView.findViewById(R.id.checkbox);
        }

        public void bind(String toppingName) {
            checkbox.setText(toppingName);
        }
    }

    public interface OnCheckedChangeListener {
        void onItemCheckedChanged(boolean isChecked, Topping topping);
    }
}
