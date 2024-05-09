package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.UserAddress;


import java.util.ArrayList;

public class AccountAddressAdapter extends RecyclerView.Adapter<AccountAddressAdapter.ViewHolder> {
    ArrayList<UserAddress> addresslist;

    public AccountAddressAdapter(ArrayList<UserAddress> addresslist){
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txt_full_address);
            recipientName = itemView.findViewById(R.id.txt_recipient_name);
            phone = itemView.findViewById(R.id.txt_recipient_phone);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
