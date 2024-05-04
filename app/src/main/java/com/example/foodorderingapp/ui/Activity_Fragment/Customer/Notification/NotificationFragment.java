package com.example.foodorderingapp.ui.Activity_Fragment.Customer.Notification;

        import android.os.Bundle;

        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.example.foodorderingapp.R;
        import com.example.foodorderingapp.ui.adapter.NotificationAdapter;
        import com.example.foodorderingapp.data.model.entity.Notification;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;

public class NotificationFragment extends Fragment {
    private RecyclerView recyclerViewList;
    TextView screenName;
    ArrayList<Notification> notiList;
    NotificationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        screenName = view.findViewById(R.id.screen_name);
        screenName.setText("Thông báo");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.recyclerViewNotification);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        notiList = new ArrayList<Notification>();
        adapter = new NotificationAdapter(notiList);
        recyclerViewList.setAdapter(adapter);

        // Original date string
        String dateString = "06-03-2025";

        // Define the date format of your input string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Parse the string to obtain a Date object
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        notiList.add(new Notification("Giao hàng thành công", "Đơn hàng 000 của bạn đã giao thành công", date));
        notiList.add(new Notification("Đơn hàng đang giao đến bạn", "Đơn hàng 000 đang trên đuờng giao", date));
        adapter.notifyDataSetChanged();
        return view;
    }

}
