package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
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

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    private Context mContext;
    private List<CategoryList> mlistCategory;
    private OnItemClickListener listener; // Thêm biến thành viên cho OnItemClickListener

    // Định nghĩa interface OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(ProductSearch product);
    }

    // Phương thức để thiết lập OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //lấy mục sản phẩm ở một vị trí cụ thể
    public ProductSearch getItemAtPosition(int position) {
        return mlistCategory.get(position).getListProducts().get(position);
    }

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

        // Gọi phương thức onItemClick của OnItemClickListener khi một mục được nhấp vào
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getItemAtPosition(position));
                }
            }
        });
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
