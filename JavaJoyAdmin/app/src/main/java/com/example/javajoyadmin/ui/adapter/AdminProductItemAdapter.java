package com.example.javajoyadmin.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class AdminProductItemAdapter extends RecyclerView.Adapter<AdminProductItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> productArrayList;

    public AdminProductItemAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.productArrayList = list;
    }

    private static String formatNumber(double temp) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.0");
        return formatter.format(temp);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_item, parent, false);
        return new ViewHolder(productItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productArrayList.get(position);

        if (product == null)
            return;

        Glide.with(context).load(product.getProductImage()).into(holder.imgProduct);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText(formatNumber(product.getProductPrice()) + " Đ");

        holder.txtSize.setText("");

        for (Map.Entry<String, Map<String, Integer>> entry : product.getProductSize().entrySet()) {
            String size = entry.getKey();
            Map<String, Integer> sizeDetails = entry.getValue();

            holder.txtSize.append(size + ": " + String.valueOf(sizeDetails.get("QUANTITY")) + "\n");
        }
    }


    @Override
    public int getItemCount() {
        return this.productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtProductName, txtSize, txtProductPrice;
        private ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
