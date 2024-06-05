package com.example.foodorderingapp.ui.activityfragment.customer.notification;

        import static android.content.Context.MODE_PRIVATE;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.Observer;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.example.foodorderingapp.R;
        import com.example.foodorderingapp.data.model.entity.Order;
        import com.example.foodorderingapp.databinding.FragmentCartBinding;
        import com.example.foodorderingapp.databinding.FragmentNotificationBinding;
        import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
        import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
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
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";
    int role = 0;
    TextView screenName;
    FragmentNotificationBinding binding;
    NotificationViewModel viewModel;
    ArrayList<Notification> notiList;
    NotificationAdapter adapter;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //todo: code trong chatgpt để xử lý chuyển trang, ko nên finish activity mà handle activity result
        // get user id from shared preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            getActivity().finish();
            Intent intent = new Intent(getContext(), activity_login.class);
            startActivity(intent);
        }

        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set top navigation text
        screenName = view.findViewById(R.id.screen_name);
        screenName.setText("Thông báo");

        // init main activity
        mainActivity = (MainActivity) getActivity();

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
                binding.swipeRefreshLayout.setRefreshing(false);

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

//      observe change in unread noti count
        viewModel.getUnreadNotiCountLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mainActivity.updateUnreadNotiQuantity(integer);
            }
        });

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
