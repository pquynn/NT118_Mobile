package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.ui.viewmodel.customer.productdetail.ProductDetailViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ViewHolder> {
    private ProductDetailViewModel productDetailViewModel;
    private Context context;
    private List<Topping> nList;
    private List<String> toppingNames, selectedToppingNames;
    private OnCheckedChangeListener listener;

    public ToppingAdapter(List<Topping> list, List<String> toppingNames, List<String> selectedToppingNames, ProductDetailViewModel viewModel, OnCheckedChangeListener listener){
        this.nList = list;
        this.toppingNames = toppingNames;
        this.selectedToppingNames = selectedToppingNames;
        this.listener = listener;
        this.productDetailViewModel = viewModel;
    }

    @NonNull
    @Override
    public ToppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_topping_productdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingAdapter.ViewHolder holder, int position) {
//        Topping toppingSizeDomain = nList.get(position);
//        if (toppingSizeDomain == null)
//            return;
//
//        holder.txtName.setText(toppingSizeDomain.getNameTopping());
//        holder.txtPrice.setText(String.valueOf(toppingSizeDomain.getPriceTopping()));
        if(!toppingNames.isEmpty()){
            String toppingName = toppingNames.get(position);
            holder.txtName.setText(toppingName);
            holder.checkbox.setText(toppingName);

            holder.checkbox.setChecked(selectedToppingNames.contains(toppingName));

            Topping topping = findToppingByName(toppingName);
            if(topping != null) {
                setPriceFormatted(holder.txtPrice, topping.getPriceTopping());
            }
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onItemCheckedChanged(isChecked, topping);
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CompoundButton checkbox;
        private TextView txtName, txtPrice;
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            txtName = itemView.findViewById(R.id.txt_nameTopping);
            txtPrice = itemView.findViewById(R.id.txt_priceTopping);
        }
    }

    // Method to find topping by name
    private Topping findToppingByName(String toppingName) {
        for (Topping topping : nList) {
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

    public interface OnCheckedChangeListener {
        void onItemCheckedChanged(boolean isChecked, Topping topping);
    }

}
