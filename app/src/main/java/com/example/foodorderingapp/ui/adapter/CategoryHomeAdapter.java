package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.HomeCategory;
import com.example.foodorderingapp.data.model.entity.Category;

import java.util.List;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder> {
    private List<Category> list;
    Context mcontext;

    public CategoryHomeAdapter(List<Category> homeCategory) {
        this.list = homeCategory;
    }

    @NonNull
    @Override
    public CategoryHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_categoryhome, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHomeAdapter.ViewHolder holder, int position) {
        Category homeCategory = list.get(position);

        holder.txtname.setText(homeCategory.getNameCategory());

        //Set màu cho danh mục All - xanh, còn lại trắng
        if (position == 0) {
            holder.frameLayout.setBackgroundTintList(ColorStateList.valueOf(mcontext.getResources().getColor(R.color.lightgreen, mcontext.getTheme()))); // Set green color for the first item
        } else {
            holder.frameLayout.setBackgroundTintList(ColorStateList.valueOf(mcontext.getResources().getColor(R.color.white, mcontext.getTheme()))); // Set white color for other items
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setCategoryList(List<Category> categoryList) {
        this.list = categoryList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout frameLayout;
        private TextView txtname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            frameLayout = itemView.findViewById(R.id.frameLayoutCategory);
            txtname = itemView.findViewById(R.id.textName);

        }
    }
}
