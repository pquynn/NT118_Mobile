package com.example.foodorderingapp.ui.activityfragment.customer.notification;

        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.Observer;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.example.foodorderingapp.R;
        import com.example.foodorderingapp.data.model.entity.Order;
        import com.example.foodorderingapp.databinding.FragmentCartBinding;
        import com.example.foodorderingapp.databinding.FragmentNotificationBinding;
        import com.example.foodorderingapp.ui.activityfragment.customer.checkout.CheckoutActivity;
        import com.example.foodorderingapp.ui.adapter.CartAdapter;
        import com.example.foodorderingapp.ui.adapter.NotificationAdapter;
        import com.example.foodorderingapp.data.model.entity.Notification;
        import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;
        import com.example.foodorderingapp.ui.viewmodel.customer.notification.NotificationViewModel;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;

public class NotificationFragment extends Fragment {
    String userId = "3";
    int role = 0;
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
        adapter = new NotificationAdapter(notiList, viewModel);
        binding.recyclerViewNotification.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewNotification.setAdapter(adapter);

        viewModel.getNotiListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                binding.setNotificationVM(viewModel);
                notiList.clear();
                notiList.addAll(notifications);
                adapter.notifyDataSetChanged();
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
    }


}
