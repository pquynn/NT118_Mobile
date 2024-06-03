package com.example.javajoyadmin.ui.activityfragment.admin.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.databinding.FragmentNotificationBinding;
import com.example.javajoyadmin.ui.adapter.NotificationAdapter;
import com.example.javajoyadmin.data.model.entity.Notification;
import com.example.javajoyadmin.ui.viewmodel.admin.notification.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    String userId = "4";
    int role = 1;
    TextView screenName;
    FragmentNotificationBinding binding;
    NotificationViewModel viewModel;
    ArrayList<Notification> notiList;
    NotificationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set top navigation text
        screenName = view.findViewById(R.id.screen_name);
        screenName.setText("Thông báo");


        // View model
        viewModel = new NotificationViewModel(userId, role);
        //Adapter
        notiList = new ArrayList<Notification>();
        adapter = new NotificationAdapter(notiList, viewModel, getContext());
        binding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewNotification.setAdapter(adapter);

        viewModel.getNotiListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                binding.setNotificationVM(viewModel);
                notiList.clear();
                notiList.addAll(notifications);
                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false); // Stop the refreshing animation

                if(notifications.isEmpty()){
                    binding.noNotificationContainer.setVisibility(View.VISIBLE);
                    binding.scrollview.setVisibility(View.GONE);
                }
                else{

                    binding.noNotificationContainer.setVisibility(View.GONE);
                    binding.scrollview.setVisibility(View.VISIBLE);
                }
            }
        });

//        String dateString = "06-03-2025";
//
//        // Define the date format of your input string
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//        // Parse the string to obtain a Date object
//        Date date = null;
//        try {
//            date = dateFormat.parse(dateString);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }


        // Set the color scheme for the spinner
        binding.swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.secondary),
                getResources().getColor(R.color.primary)
        );
        // Set up the swipe refresh listener
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Trigger the reload in the ViewModel
                viewModel.reloadData();
            }
        });
    }


}
