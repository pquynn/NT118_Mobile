package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.ProductSearchDomain;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import kotlinx.coroutines.internal.InlineList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductViewHolder> {

    private List<ProductSearchDomain> nListProduct;

    public SearchAdapter(List<ProductSearchDomain> nListProduct) {
        this.nListProduct = nListProduct;
    }

    public void setFilterList(List<ProductSearchDomain> filterList){
        this.nListProduct = filterList;
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
        ProductSearchDomain product = nListProduct.get(position);
        if(product==null)
            return;

        holder.imgProduct.setImageResource(product.getImage());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        if(nListProduct!= null)
            return nListProduct.size();
        return 0;
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
}
