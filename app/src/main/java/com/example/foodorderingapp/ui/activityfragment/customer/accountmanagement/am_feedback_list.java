package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.ui.adapter.FeedbackItemAdapter;
import com.example.foodorderingapp.data.model.OrderDetail;
import com.example.foodorderingapp.ui.viewmodel.customer.accountmanagement.OrderFeedbackListVM;

import java.util.ArrayList;
import java.util.List;

public class am_feedback_list extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private OrderFeedbackListVM viewModel;
    ArrayList<OrderItem> productList = new ArrayList<>();
    String orderId, orderStatus;

    TextView txt_orderId, txt_orderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_feedback_list);
        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Đánh giá đơn hàng");

        //get intent gì đó để lấy orderid
        orderId = "1";
        txt_orderId = findViewById(R.id.txt_orderID);
        txt_orderId.setText(orderId);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory(){
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(OrderFeedbackListVM.class)) {
                    return (T) new OrderFeedbackListVM(orderId);
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
            }).get(OrderFeedbackListVM.class);

        recyclerViewOrderFeedBack();

    }

    private void recyclerViewOrderFeedBack() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerFeedbackList);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        //Lấy danh sách từ viewModel
        viewModel.getOrderDetailListLiveData().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderDetails) {
                for(OrderItem i : orderDetails){
                    productList.add(i);
                }
                adapter = new FeedbackItemAdapter(productList);
                recyclerViewList.setAdapter(adapter);
            }
        });
        txt_orderStatus = findViewById(R.id.txt_order_status);
        txt_orderStatus.setText("");
        viewModel.getOrderStatusLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                orderStatus = s;
                txt_orderStatus.setText(orderStatus);
            }
        });


    }
}