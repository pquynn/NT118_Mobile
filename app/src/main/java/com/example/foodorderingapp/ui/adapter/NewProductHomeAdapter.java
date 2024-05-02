package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.R;


import java.util.List;

public class NewProductHomeAdapter extends RecyclerView.Adapter<NewProductHomeAdapter.ViewHolder> {
    private Context context;
    private List<ProductSearch> listNewProduct;

    public NewProductHomeAdapter(Context context, List<ProductSearch> newProductList) {
        this.context = context;
        this.listNewProduct = newProductList;
    }

    @NonNull
    @Override
    public NewProductHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_productpopular_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductHomeAdapter.ViewHolder holder, int position) {
        ProductSearch product = listNewProduct.get(position);

        holder.imgProduct.setImageResource(product.getImage());
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return listNewProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private ImageView imgProduct;
        private TextView txtName, txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.constraint_product);
            imgProduct = itemView.findViewById(R.id.img_product);
            txtName = itemView.findViewById(R.id.txt_name);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
