package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.data.model.entity.Category;
import com.example.foodorderingapp.data.model.entity.Product;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.DecimalFormat;
import java.util.List;

public class CategoryProductListAdapter extends RecyclerView.Adapter<CategoryProductListAdapter.CategoryProductListViewHolder> {
    private List<Product> mProducts;
    private Context context;
    private ProductClickListener mListener; // Khai báo listener

    // Khai báo interface để định nghĩa listener
    public interface ProductClickListener {
        void onProductClick(Product product);
    }
    //Khởi tạo
    public CategoryProductListAdapter(Context context, List<Product> list,  ProductClickListener listener){
        this.context = context;
        this.mProducts = list;
        this.mListener = listener;
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
        setPriceFormatted(holder.tvPrice, product.getProductPrice());

        //Xử lý sự kiện khi click vào sản phẩm
//        holder.addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Gọi listener và chuyển dữ liệu sản phẩm khi click vào
//                if (mListener != null) {
//                    mListener.onProductClick(product);
//                } else {
//                    Log.d("Listerer Error", "Error");
//                }
//            }
//        });

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi listener và chuyển dữ liệu sản phẩm khi click vào
                if (mListener != null) {
                    mListener.onProductClick(product);
                } else {
                    Log.d("Listerer Error", "Error");
                }
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi listener và chuyển dữ liệu sản phẩm khi click vào
                if (mListener != null) {
                    mListener.onProductClick(product);
                } else {
                    Log.d("Listerer Error", "Error");
                }
            }
        });
    }

    public static void setPriceFormatted(TextView textView, int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        textView.setText(formattedPrice);
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
        private FrameLayout addToCart;
        public CategoryProductListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            addToCart = itemView.findViewById(R.id.add_to_cart);
        }
    }

}