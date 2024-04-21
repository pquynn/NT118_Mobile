package com.example.foodorderingapp.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.CategoryList;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    private Context mContext;
    private List<CategoryList> mlistCategory;

    public CategoryListAdapter(Context context, List<CategoryList> list){
        this.mContext = context;
        this.mlistCategory = list;
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

        holder.tvCategoryName.setText(category.getNameCategory());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        holder.rcvListCategory.setLayoutManager(linearLayoutManager);

        CategoryProductListAdapter categoryProductListAdapter = new CategoryProductListAdapter();
        categoryProductListAdapter.setData(category.getListProducts());

        holder.rcvListCategory.setAdapter(categoryProductListAdapter);
    }

    @Override
    public int getItemCount() {
        if (mlistCategory != null) {
            return mlistCategory.size();
        }
        return 0;
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCategoryName;
        private RecyclerView rcvListCategory;
        public CategoryListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tv_categoryName);
            rcvListCategory = itemView.findViewById(R.id.rcv_ListCategory);
        }
    }
}
