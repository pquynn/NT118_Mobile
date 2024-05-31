package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.data.model.ProductSearch;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.CategoryList;
import com.example.foodorderingapp.data.model.entity.Product;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {
    private Context mContext;
    private List<CategoryList> mlistCategory;
    private CategoryProductListAdapter.ProductClickListener productClickListener;

    public CategoryListAdapter(Context context, List<CategoryList> list, CategoryProductListAdapter.ProductClickListener listener){
        this.mContext = context;
        this.mlistCategory = list;
        this.productClickListener = listener;
    }

    public void setData(List<CategoryList> list){
        this.mlistCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_listcategory, parent, false);
        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
        CategoryList category = mlistCategory.get(position);
        if (category == null) {
            return;
        }
        holder.bind(category, productClickListener);
    }

    @Override
    public int getItemCount() {
        if (mlistCategory != null) {
            return mlistCategory.size();
        }
        return 0;
    }

    public static class CategoryListViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCategoryName;
        private RecyclerView rcvListCategory;

        public CategoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_categoryName);
            rcvListCategory = itemView.findViewById(R.id.rcv_ListCategory);
        }

        public void bind(CategoryList categoryList, CategoryProductListAdapter.ProductClickListener listener) {
            tvCategoryName.setText(categoryList.getCategory().getNameCategory());
            rcvListCategory.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
            CategoryProductListAdapter productAdapter = new CategoryProductListAdapter(itemView.getContext(), categoryList.getListProducts(), listener);
            rcvListCategory.setAdapter(productAdapter);
        }
    }
}
