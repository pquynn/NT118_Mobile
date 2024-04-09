package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.domain.Coupon;
import com.example.foodorderingapp.domain.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    ArrayList<Notification> notiList;

    public NotificationAdapter(ArrayList<Notification> notiList){
        this.notiList = notiList;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_notification, parent, false);
        return new NotificationAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.notiName.setText(notiList.get(position).getType());
        holder.content.setText(notiList.get(position).getContent());
        holder.date.setText(notiList.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notiName;
        TextView content;
        TextView date;
        ConstraintLayout mainlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notiName = itemView.findViewById(R.id.txt_noti_name);
            content = itemView.findViewById(R.id.txt_noti_message);
            date = itemView.findViewById(R.id.txt_date);
        }
    }
}

