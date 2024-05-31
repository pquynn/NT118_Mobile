package com.example.foodorderingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Notification;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.ViewholderCartBinding;
import com.example.foodorderingapp.databinding.ViewholderNotificationBinding;
import com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement.am_order_detail;
import com.example.foodorderingapp.ui.viewmodel.customer.notification.NotificationViewModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private ArrayList<Notification> notiList;
    private NotificationViewModel viewModel;
    private Context context;
    public NotificationAdapter(ArrayList<Notification> notiList, NotificationViewModel viewModel, Context context){
        this.notiList = notiList;
        this.viewModel = viewModel;
        this.context = context;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_notification, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = notiList.get(position);
        holder.bind(notification);

        //set background color by noti status (read, unread)
        int lightgreen = ContextCompat.getColor(context, R.color.transparent50_lightgreen);
        int transparent = ContextCompat.getColor(context, R.color.transparent);
        if(notification.getStatus().equals("read")){
            holder.binding.viewholderNotification.setBackgroundColor(transparent);
        }
        else {
            holder.binding.viewholderNotification.setBackgroundColor(lightgreen);
        }

        holder.binding.viewholderNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, am_order_detail.class);
                intent.putExtra("order_id", notification.getIdOrder());
                context.startActivity(intent);
                notification.setStatus("read");
                notifyDataSetChanged();
                viewModel.updateNotificationStatus(notification.getId());
            }
        });
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

