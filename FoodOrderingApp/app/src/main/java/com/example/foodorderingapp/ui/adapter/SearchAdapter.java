package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder> {
    private List<Product> nListProduct;
    private Context context;
    private List<Product> filteredList;
    private Filter filter;

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
    }

    public SearchAdapter(Context context) {
        this.context = context;
        this.nListProduct = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        this.filter = new SearchFilter();
    }

    public void setFilterList(List<Product> filterList){
        this.nListProduct = filterList;
        this.filteredList = new ArrayList<>(filterList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_search, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = filteredList.get(position);
        if(product==null)
            return;

        Glide.with(context).load(product.getProductImage()).into(holder.imgProduct);
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(String.valueOf(product.getProductPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

    //Lọc sản phẩm dựa trên tên sản phẩm
    private class SearchFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Product> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // Nếu không có ràng buộc, trả về toàn bộ danh sách sản phẩm
                filteredList.addAll(nListProduct);
            } else {
                // Thực hiện lọc dựa trên ràng buộc
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product product : nListProduct) {
                    if (product.getProductName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(product);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList.clear();
            filteredList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    }
    public Filter getFilter() {
        return filter;
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}