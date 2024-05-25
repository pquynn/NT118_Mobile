package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.data.model.entity.Product;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class CategoryProductListAdapter extends RecyclerView.Adapter<CategoryProductListAdapter.CategoryProductListViewHolder> {
    private List<Product> mProducts;
    private Context context;

    public void setData(List<Product> list){
        this.mProducts = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_search, parent, false);
        return new CategoryProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductListViewHolder holder, int position) {
        Product product = mProducts.get(position);
        if (product == null){
            return;
        }
        Glide.with(context).load(product.getProductImage()).into(holder.imgProduct);
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(product.getProductPrice());
    }

    @Override
    public int getItemCount() {
        if (mProducts != null) {
            return mProducts.size();
        }
        return 0;
    }

    public static class CategoryProductListViewHolder extends RecyclerView.ViewHolder{

        private ShapeableImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice;
        public CategoryProductListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
