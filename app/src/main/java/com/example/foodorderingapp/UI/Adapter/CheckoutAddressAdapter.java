package com.example.foodorderingapp.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.Data.Model.Entity.UserAddress;


import java.util.ArrayList;

public class CheckoutAddressAdapter extends RecyclerView.Adapter<CheckoutAddressAdapter.ViewHolder> {
    private ArrayList<UserAddress> addresslist;
    private int row_index = 0; // position of selected viewholder
    public CheckoutAddressAdapter(ArrayList<UserAddress> addresslist){
        this.addresslist = addresslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_address, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(addresslist.get(position).getAddress());
        holder.recipientName.setText(addresslist.get(position).getRecipientName());
        holder.phone.setText(addresslist.get(position).getPhone());

////      HIDE EDIT, DELETE BUTTON FROM EACH ADDRESS CONTAINER
            holder.btn_delete.setVisibility(View.GONE);
            holder.btn_edit.setVisibility(View.GONE);

        // Click event for view holder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        // Set foreground based on position of viewholder
        if (row_index == position) {
            //add foreground: solid_line
            holder.container.setForeground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_border));
        } else {
            holder.container.setForeground(null);
        }
    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView recipientName;
        TextView phone;
        ImageView btn_edit;
        ImageView btn_delete;
        ConstraintLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txt_full_address);
            recipientName = itemView.findViewById(R.id.txt_recipient_name);
            phone = itemView.findViewById(R.id.txt_recipient_phone);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            container = itemView.findViewById(R.id.viewholder_address);
        }
    }
}
