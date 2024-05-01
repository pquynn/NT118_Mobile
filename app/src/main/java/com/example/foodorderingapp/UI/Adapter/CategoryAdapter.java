
package com.example.foodorderingapp.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.Entity.Category;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> mCategory;
    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
        this.mCategory = new ArrayList<>();
    }

    public void setData(List<Category> list){
        this.mCategory = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mCategory.get(position);
        if (category == null)
            return;

        Glide.with(context).load(category.getImgCategory()).into(holder.imgCategory);
        holder.txtname.setText(category.getNameCategory());
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imgCategory;
        private TextView txtname;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            txtname = itemView.findViewById(R.id.txt_name);
        }
    }
}
