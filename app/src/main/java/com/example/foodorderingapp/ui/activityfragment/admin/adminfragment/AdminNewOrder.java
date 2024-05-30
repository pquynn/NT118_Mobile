package com.example.foodorderingapp.ui.activityfragment.admin.adminfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.ui.AdminOrderVM;
import com.example.foodorderingapp.ui.adapter.AdminOrderItemAdapter;
import com.example.foodorderingapp.ui.adapter.OrderItemAdapter;
import com.example.foodorderingapp.data.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminNewOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminNewOrder extends Fragment {

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

    public AdminNewOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminNewOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminNewOrder newInstance(String param1, String param2) {
        AdminNewOrder fragment = new AdminNewOrder();
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
        return inflater.inflate(R.layout.fragment_admin_new_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adminOrderVM = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(AdminOrderVM.class)) {
                    return (T) new AdminOrderVM("Chờ xác nhận");
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(AdminOrderVM.class);

        recyclerViewList = view.findViewById(R.id.recyclerViewOrderItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);
        listOrderItem = new ArrayList<>();

        adminOrderVM.getOrderListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                for (Order i : orders) {
                    if (i != null) {
                        listOrderItem.add(new OrderItem(i.getId(), i.getTotalPrice(), i.getTotalProduct()));
                    }
                }
                Adapter = new AdminOrderItemAdapter(listOrderItem);
                recyclerViewList.setAdapter(Adapter);
            }
        });
    }
}