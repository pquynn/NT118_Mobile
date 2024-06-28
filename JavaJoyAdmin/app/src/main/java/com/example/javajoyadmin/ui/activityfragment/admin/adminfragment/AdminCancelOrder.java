package com.example.javajoyadmin.ui.activityfragment.admin.adminfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.OrderItem;
import com.example.javajoyadmin.data.model.entity.Order;
import com.example.javajoyadmin.ui.adapter.AdminOrderItemAdapter;
import com.example.javajoyadmin.ui.viewmodel.admin.order.AdminOrderVM;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminCancelOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminCancelOrder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerViewList;
    private AdminOrderItemAdapter Adapter;
    private ArrayList<OrderItem> listOrderItem;
    private AdminOrderVM adminOrderVM;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminCancelOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminCancelOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminCancelOrder newInstance(String param1, String param2) {
        AdminCancelOrder fragment = new AdminCancelOrder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_cancel_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adminOrderVM = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(AdminOrderVM.class)) {
                    return (T) new AdminOrderVM("Đã hủy");
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(AdminOrderVM.class);

        recyclerViewList = view.findViewById(R.id.recyclerViewOrderItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);

        listOrderItem = new ArrayList<>();
        Adapter = new AdminOrderItemAdapter(listOrderItem);
        recyclerViewList.setAdapter(Adapter);
    }

    public void LoadListOrder() {
        // Loại bỏ Observer cũ
        adminOrderVM.getOrderListLiveData().removeObservers(this);


        // Thiết lập observer để nhận dữ liệu mới
        adminOrderVM.getOrderListLiveData().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                // Cập nhật lại listOrderItem với dữ liệu mới

                // Xóa dữ liệu cũ trong listOrderItem
                listOrderItem.clear();
                Adapter.notifyDataSetChanged(); // Thông báo adapter để cập nhật giao diện

                if (orders != null) {
                    for (Order i : orders) {
                        if (i != null) {
                            listOrderItem.add(new OrderItem(i.getId(), i.getOrderPrice(), i.getTotalProduct()));
                        }
                    }
                    // Thông báo adapter để cập nhật giao diện với dữ liệu mới
                    Adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadListOrder();
    }
}