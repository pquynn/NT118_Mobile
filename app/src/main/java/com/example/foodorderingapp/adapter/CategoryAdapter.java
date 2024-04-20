package com.example.foodorderingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.CategoryDomain;
import com.example.foodorderingapp.domain.ProductSearchDomain;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryDomain> mCategory;
    private Context context;

    public CategoryAdapter(Context context, List<CategoryDomain> list){
        this.context = context;
        this.mCategory = list;
    }

    public CategoryAdapter(List<CategoryDomain> list){
        this.mCategory = list;
    }

    public void setData(List<CategoryDomain> list){
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
        CategoryDomain categoryDomain = mCategory.get(position);
        if (categoryDomain == null)
            return;

        holder.imgCategory.setImageResource(categoryDomain.getResourceid());
        holder.txtname.setText(categoryDomain.getName());

//        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (mCategory != null){
            return mCategory.size();
        }
        return 0;
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
