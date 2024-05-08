package com.example.foodorderingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.databinding.ViewholderNotificationBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.notification.NotificationViewModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private ArrayList<Notification> notiList;
    private NotificationViewModel viewModel;
    public NotificationAdapter(ArrayList<Notification> notiList, NotificationViewModel viewModel){
        this.notiList = notiList;
        this.viewModel = viewModel;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_notification, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.bind(notiList.get(position));
    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewholderNotificationBinding binding;

        public ViewHolder(@NonNull ViewholderNotificationBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Notification notification){
            binding.setNotification(notification);
            binding.executePendingBindings();
        }
    }
}

