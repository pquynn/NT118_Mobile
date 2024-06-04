package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.R;


import java.text.DecimalFormat;
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
        this.context = context;
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
        Product product = productList.get(position);
        holder.txtName.setText(product.getProductName());
        setPriceFormatted(holder.txtPrice, product.getProductPrice());
        String productImage = product.getProductImage();
        if (productImage != null && !productImage.isEmpty()) {
            Glide.with(context)
                    .load(productImage)
                    .into(holder.imgProduct);
        }

        holder.imgProduct.setOnClickListener(v -> listener.onItemClick(product));
//        holder.addToCart.setOnClickListener(v -> listener.onItemClick(product));
    }

    //set format giá
    public static void setPriceFormatted(TextView textView, int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        textView.setText(formattedPrice);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private ImageView imgProduct;
        private TextView txtName, txtPrice;
        private FrameLayout addToCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.constraint_product);
            imgProduct = itemView.findViewById(R.id.img_product);
            txtName = itemView.findViewById(R.id.txt_name);
            txtPrice = itemView.findViewById(R.id.txt_price);
            addToCart = itemView.findViewById(R.id.img_addtocart);
        }
    }
}
