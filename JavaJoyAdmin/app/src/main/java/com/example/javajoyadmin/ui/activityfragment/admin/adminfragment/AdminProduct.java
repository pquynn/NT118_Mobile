package com.example.javajoyadmin.ui.activityfragment.admin.adminfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.javajoyadmin.R;
import com.example.javajoyadmin.data.model.entity.Product;
import com.example.javajoyadmin.ui.adapter.AdminProductItemAdapter;
import com.example.javajoyadmin.ui.viewmodel.admin.product.AdminProductVM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewList;
    private AdminProductItemAdapter adapter;
    private ArrayList<Product> listProduct;
    private AdminProductVM adminProductVM;
    private Button btnUpdateQuantity;

    public AdminProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProduct newInstance(String param1, String param2) {
        AdminProduct fragment = new AdminProduct();
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
        return inflater.inflate(R.layout.fragment_admin_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUpdateQuantity = view.findViewById(R.id.btnUpdateQuantity);

        adminProductVM = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(AdminProductVM.class)) {
                    return (T) new AdminProductVM();
                }
                throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
            }
        }).get(AdminProductVM.class);

        recyclerViewList = view.findViewById(R.id.recyclerViewProductItem);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setHasFixedSize(true);

        listProduct = new ArrayList<>();
        adapter = new AdminProductItemAdapter(getContext(), listProduct);
        recyclerViewList.setAdapter(adapter);

        btnUpdateQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thời gian hiện tại
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                // Kiểm tra thời gian hiện tại có nằm trong khoảng từ 10h30 tối đến 7h30 sáng
                boolean isAllowedTime = (currentHour >= 22 && currentMinute >= 30) || (currentHour < 7) || (currentHour == 7 && currentMinute <= 30);

                if (isAllowedTime) {
                    adminProductVM.UpdateQuantity(getContext());
                } else {
                    Toast.makeText(getContext(), "Chỉ có thể cập nhật số lượng từ 10h30 tối tới 7h30 sáng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void LoadListProduct() {
        // Loại bỏ Observer cũ
        adminProductVM.GetProductListLiveData().removeObservers(this);

        // Thiết lập observer để nhận dữ liệu mới
        adminProductVM.GetProductListLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                // Cập nhật lại listOrderItem với dữ liệu mới

                // Xóa dữ liệu cũ trong listOrderItem
                listProduct.clear();
                adapter.notifyDataSetChanged(); // Thông báo adapter để cập nhật giao diện

                if (products != null) {
                    for (Product i : products) {
                        if (i != null) {
                            Product temp = new Product();
                            temp.setId(i.getId());
                            temp.setProductName(i.getProductName());
                            temp.setProductImage(i.getProductImage());
                            temp.setProductPrice(i.getProductPrice());
                            temp.setProductSize(i.getProductSize());
                            listProduct.add(temp);
                        }
                    }
                    // Thông báo adapter để cập nhật giao diện với dữ liệu mới
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadListProduct();
    }
}