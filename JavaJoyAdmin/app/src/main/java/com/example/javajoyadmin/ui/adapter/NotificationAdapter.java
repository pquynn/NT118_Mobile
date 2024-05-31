package com.example.javajoyadmin.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.databinding.ViewholderNotificationBinding;
import com.example.javajoyadmin.ui.activityfragment.admin.activity_adminOrderDetail;
import com.example.javajoyadmin.ui.viewmodel.admin.notification.NotificationViewModel;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.viewholder_notification, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
                notification.setStatus("read");
                notifyDataSetChanged();
                viewModel.updateNotificationStatus(notification.getId());
                Intent intent = new Intent(context, activity_adminOrderDetail.class);
                intent.putExtra("order_id", notification.getIdOrder());
                context.startActivity(intent);
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

