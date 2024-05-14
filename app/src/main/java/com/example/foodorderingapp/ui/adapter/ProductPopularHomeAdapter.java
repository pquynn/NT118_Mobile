package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.R;


import java.util.List;

public class ProductPopularHomeAdapter extends RecyclerView.Adapter<ProductPopularHomeAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private List<Product> productList;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    public ProductPopularHomeAdapter(Context context, List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_productpopular_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < 5) {
            Product product = productList.get(position);
            Glide.with(context).load(product.getProductImage()).into(holder.imgProduct);
            holder.txtName.setText(product.getProductName());
            holder.txtPrice.setText(product.getProductPrice());
            holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
        }else {
            // Ẩn itemView nếu không có sản phẩm tại vị trí này
            holder.itemView.setVisibility(View.GONE);
        }
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
