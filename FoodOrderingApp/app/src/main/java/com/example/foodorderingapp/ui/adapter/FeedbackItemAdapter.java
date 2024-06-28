package com.example.foodorderingapp.ui.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.OrderDetail;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.data.repository.accountmanagement.myorders.OrdersFeedbackRepository;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_feedback_product;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_my_orders;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_order_detail;

import java.util.ArrayList;

public class FeedbackItemAdapter extends RecyclerView.Adapter<FeedbackItemAdapter.ViewHolder>{
    ArrayList<OrderItem> productList;
    String userId;
    private static final int REQUEST_CODE_FEEDBACK = 1;


    OrdersFeedbackRepository repository = new OrdersFeedbackRepository();

    public FeedbackItemAdapter(ArrayList<OrderItem> productList, String userId){
        this.productList = productList;
        this.userId = userId;
    }

    @Override
    public FeedbackItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_feedback_item, parent, false);
        return new FeedbackItemAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(productList.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(productList.get(position).getPrice()));
        holder.productSize.setText(productList.get(position).getSize());
        holder.note.setText(productList.get(position).getNote());
        holder.quantity.setText(String.valueOf(productList.get(position).getQuantity()));
        Glide.with(holder.itemView.getContext())
                .load(productList.get(position).getProductImage())
                .into(holder.productImage);

        ArrayList<String> listTopping = productList.get(position).getTopping();
        if(!listTopping.isEmpty()){
            String toppings = "";
            int dem = 0;
            for(String i : listTopping){
                toppings += i;
                dem++;
                if(dem < listTopping.size()){
                    toppings += ", ";
                }
            }
            holder.topping.setText(toppings);
        }else{
            holder.topping.setVisibility(View.GONE);
        }

        repository.checkExistComment(userId, productList.get(position).getIdProduct(), new OrdersFeedbackRepository.checkCommentCallback() {
            @Override
            public void checkCommentSuccess(int isExist) {
                holder.btnProduct.setText("Xem đánh giá");
                notifyDataSetChanged();
            }

            @Override
            public void checkCommentError(Exception e) {
            }
        });
        holder.btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent myIntent = new Intent(context, am_feedback_product.class);
                myIntent.putExtra("product_id", productList.get(position).getIdProduct());
                myIntent.putExtra("user_id", userId);
                // Kiểm tra nếu context là một Activity
                if (context instanceof am_my_orders) {
                    ((am_my_orders) context).startActivityForResult(myIntent, REQUEST_CODE_FEEDBACK);
                } else {
                    context.startActivity(myIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productSize, note, quantity;
        TextView topping;
        ImageView productImage;
        Button btnProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txt_product_name);
            productPrice = itemView.findViewById(R.id.txt_product_cost);
            productSize = itemView.findViewById(R.id.txt_product_size);
            note = itemView.findViewById(R.id.txt_note);
            quantity = itemView.findViewById(R.id.txt_quantity);
            productImage = itemView.findViewById(R.id.img_fb_product);
            topping = itemView.findViewById(R.id.txt_fb_topping);
            btnProduct = itemView.findViewById(R.id.btn_product);
        }
    }
}
