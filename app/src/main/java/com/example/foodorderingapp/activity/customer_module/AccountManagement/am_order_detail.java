<<<<<<<< HEAD:app/src/main/java/com/example/foodorderingapp/activity/customer_module/refund/AddRefundProductActivity.java
package com.example.foodorderingapp.activity.customer_module.refund;
========
package com.example.foodorderingapp.activity.customer_module.AccountManagement;
>>>>>>>> cart-checkout-payment:app/src/main/java/com/example/foodorderingapp/activity/customer_module/AccountManagement/am_order_detail.java

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.R;
<<<<<<<< HEAD:app/src/main/java/com/example/foodorderingapp/activity/customer_module/refund/AddRefundProductActivity.java
import com.example.foodorderingapp.adapter.CouponAdapter;
import com.example.foodorderingapp.adapter.RefundProductAdapter;
import com.example.foodorderingapp.domain.Coupon;
========
import com.example.foodorderingapp.adapter.OrderDetailAdapter;
>>>>>>>> cart-checkout-payment:app/src/main/java/com/example/foodorderingapp/activity/customer_module/AccountManagement/am_order_detail.java
import com.example.foodorderingapp.domain.OrderDetail;
import com.example.foodorderingapp.domain.OrderDetailDomain;

import java.util.ArrayList;

<<<<<<<< HEAD:app/src/main/java/com/example/foodorderingapp/activity/customer_module/refund/AddRefundProductActivity.java
public class AddRefundProductActivity extends AppCompatActivity {
========
public class am_order_detail extends AppCompatActivity {
>>>>>>>> cart-checkout-payment:app/src/main/java/com/example/foodorderingapp/activity/customer_module/AccountManagement/am_order_detail.java
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<<< HEAD:app/src/main/java/com/example/foodorderingapp/activity/customer_module/refund/AddRefundProductActivity.java
        setContentView(R.layout.activity_choose_refundprod);

        // set top navigation text
        TextView screenName = findViewById(R.id.screen_name);
        screenName.setText("Chọn sản phẩm hoàn tiền");

        // set recyler list
        recyclerViewRefundProd();
    }

    private void recyclerViewRefundProd() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerViewRefundProd);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();

        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "50% đường", 3));
        productList.add(new OrderDetail("Bánh", "60.000 đ", "Lớn", "a", 2));

        adapter = new RefundProductAdapter(productList);
========
        setContentView(R.layout.activity_am_order_detail);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Chi tiết đơn hàng");

        recyclerViewOrderDetail();
    }

    private void recyclerViewOrderDetail() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList = findViewById(R.id.recyclerProductList);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<OrderDetail> productList = new ArrayList<OrderDetail>();
        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "50% đường", 3));
        productList.add(new OrderDetail("Bánh", "60.000 đ", "Lớn", "a", 2));
        productList.add(new OrderDetail("Trà sữa trân châu", "45.000 đ", "Lớn", "a", 3));
        adapter = new OrderDetailAdapter(productList);
>>>>>>>> cart-checkout-payment:app/src/main/java/com/example/foodorderingapp/activity/customer_module/AccountManagement/am_order_detail.java
        recyclerViewList.setAdapter(adapter);

    }
}