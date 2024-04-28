package com.example.foodorderingapp.UI.Activity_Fragment.Customer.AccountManagement;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.Data.Model.Entity.Comment;
import com.example.foodorderingapp.Data.Repository.AccountManagement.MyOrders.OrdersFeedbackRepository;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.UI.Adapter.FeedbackItemAdapter;
import com.example.foodorderingapp.Data.Model.OrderDetail;
import com.example.foodorderingapp.UI.ViewModel.Customer.AccountManagement.OrderFeedbackListVM;

import java.util.ArrayList;
import java.util.List;

public class am_feedback_list extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private OrderFeedbackListVM viewModel;
    ArrayList<OrderDetail> productList = new ArrayList<>();
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
        viewModel.getOrderDetailListLiveData().observe(this, new Observer<ArrayList<OrderDetail>>() {
            @Override
            public void onChanged(ArrayList<OrderDetail> orderDetails) {
                for(OrderDetail i : orderDetails){
                    productList.add(i);
                }
                adapter = new FeedbackItemAdapter(productList);
                recyclerViewList.setAdapter(adapter);
            }
        });
        txt_orderStatus = findViewById(R.id.txt_order_status);
        viewModel.getOrderStatusLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                orderStatus = s;
                txt_orderStatus.setText(orderStatus);
            }
        });


    }
}