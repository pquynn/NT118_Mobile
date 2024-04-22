package com.example.foodorderingapp.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.Data.Model.ProductSearch;
import com.example.foodorderingapp.R;


import java.util.List;

public class ProductPopularHomeAdapter extends RecyclerView.Adapter<ProductPopularHomeAdapter.ViewHolder> {
    private Context context;
    private List<ProductSearch> productList;

    public ProductPopularHomeAdapter(Context context, List<ProductSearch> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_productpopular_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductSearch product = productList.get(position);

        holder.imgProduct.setImageResource(product.getImage());
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice());

    }

    @Override
    public int getItemCount() {
        return productList.size();
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
