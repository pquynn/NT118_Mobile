package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.Address;

import java.util.ArrayList;

public class AccountAddressAdapter extends RecyclerView.Adapter<AccountAddressAdapter.ViewHolder> {
    ArrayList<Address> addresslist;

    public AccountAddressAdapter(ArrayList<Address> addresslist){
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
        ConstraintLayout mainlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txt_description);
            recipientName = itemView.findViewById(R.id.txt_coupon_name);
            phone = itemView.findViewById(R.id.txt_valid_date);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
