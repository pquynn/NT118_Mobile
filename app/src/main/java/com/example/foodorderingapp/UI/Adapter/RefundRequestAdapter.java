package com.example.foodorderingapp.UI.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.OrderDetail;

import java.util.ArrayList;

public class RefundRequestAdapter extends RecyclerView.Adapter<RefundRequestAdapter.ViewHolder> {
    private ArrayList<OrderDetail> productList;
    private View.OnClickListener onClickListener;
    private boolean isDialogOpen = false;

    public RefundRequestAdapter(ArrayList<OrderDetail> productList){
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_refund_request, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(productList.get(position).getProductPrice()));
        holder.productSize.setText(productList.get(position).getProductSize());
        holder.note.setText(productList.get(position).getNote());
//        holder.quantity.setText(productList.get(position).getQuantity());

        holder.btnSelectReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDialogOpen) {
                    showDialog(view.getContext());
                }
            }
        });
    }

//    function to show bottom dialog when button is clicked
    public void showDialog(Context context) {
        isDialogOpen = true; // Update dialog state
        Dialog dialog = new Dialog(context); // Corrected line
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_refund_reason);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isDialogOpen = false; // Update dialog state when dismissed
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productSize;
        TextView note;
        TextView quantity;
        ImageView btnSelectReason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txt_product_name);
            productPrice = itemView.findViewById(R.id.txt_product_cost);
            productSize = itemView.findViewById(R.id.txt_product_size);
            note = itemView.findViewById(R.id.txt_note);
            quantity = itemView.findViewById(R.id.quantity);
            btnSelectReason = itemView.findViewById(R.id.btn_select_reason);
        }
    }
}
