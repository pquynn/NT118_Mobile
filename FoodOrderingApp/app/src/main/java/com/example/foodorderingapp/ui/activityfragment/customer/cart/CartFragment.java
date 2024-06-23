package com.example.foodorderingapp.ui.activityfragment.customer.cart;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.checkout.CheckoutActivity;
import com.example.foodorderingapp.ui.adapter.CartAdapter;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.FragmentCartBinding;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartFragment extends Fragment {
    TextView screenname;
    private Map<String, OrderItem> orderItemMap;
    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartAdapter adapter;

    private List<Product> productList;
    private MainActivity mainActivity;
    private static final int CHECKOUT_REQUEST_CODE = 1;
    private static final int LOGIN_REQUEST_CODE = 2;
    private String userId;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "sharePrefName";
    private static final String KEY_USER_ID = "userID";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get user id from shared preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            // User ID not found, handle this case
            Intent intent = new Intent(getContext(), activity_login.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
            return null;
        }

        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set top navigation text
        screenname = view.findViewById(R.id.screen_name);
        screenname.setText("Giỏ hàng");

        // init main activity
        mainActivity = (MainActivity) getActivity();

        // init cart View model
        viewModel = new CartViewModel(userId, getActivity());

        //button checkout
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivityForResult(intent, CHECKOUT_REQUEST_CODE);
            }
        });

        //Adapter
        orderItemMap = new HashMap<>();
        productList = new ArrayList<>();
        adapter = new CartAdapter(orderItemMap, productList, viewModel, getActivity(), mainActivity);
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewCart.setAdapter(adapter);
        binding.setLifecycleOwner(this);

        // check if overder live data is null or empty
        if(viewModel.getOrderLiveData() == null || viewModel.getOrderLiveData().getValue() == null){
            binding.emptyCartContainer.setVisibility(View.VISIBLE);
            binding.recyclerViewCart.setVisibility(View.GONE);
            binding.linearLayout5.setVisibility(View.GONE);
        }
        else {
            binding.emptyCartContainer.setVisibility(View.GONE);
            binding.recyclerViewCart.setVisibility(View.VISIBLE);
            binding.linearLayout5.setVisibility(View.VISIBLE);
        }

        // observe change in orderlivedata
        viewModel.getOrderLiveData().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                mainActivity.reloadBadge();
                if(order != null) {
                    binding.setCartVM(viewModel);

                    //observe productlist when order change
                    viewModel.loadProductList();

                    // update order item map
                    orderItemMap.clear();
                    orderItemMap.putAll(order.getOrderItem());
                    adapter.notifyDataSetChanged();


                    // check if cart is empty or not
                    if (order.getOrderItem() == null || order.getOrderItem().isEmpty()) {
                        binding.emptyCartContainer.setVisibility(View.VISIBLE);
                        binding.recyclerViewCart.setVisibility(View.GONE);
                        binding.linearLayout5.setVisibility(View.GONE);
                    }else {
                        binding.emptyCartContainer.setVisibility(View.GONE);
                        binding.recyclerViewCart.setVisibility(View.VISIBLE);
                        binding.linearLayout5.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    binding.emptyCartContainer.setVisibility(View.VISIBLE);
                    binding.recyclerViewCart.setVisibility(View.GONE);
                    binding.linearLayout5.setVisibility(View.GONE);
                }

                binding.swipeRefreshLayout.setRefreshing(false); // Stop the refreshing animation
            }
        });

        //obseve change in isValid Checkout
        viewModel.getIsValidCheckout().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true){
                    binding.btnCheckout.setEnabled(true);
                    binding.outOfStockWarning.setVisibility(View.GONE);
                }
                else {
                    binding.btnCheckout.setEnabled(false);
                    binding.outOfStockWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        //observe change in product list livedata
        viewModel.getProductListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productList.clear();
                productList.addAll(products);
                adapter.notifyDataSetChanged();
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

    //receive result from login activity
    // method to get result from activity through intent (activity2 -> activity1)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GET DATA FROM COUPON ACTIVITY
        if (requestCode == CHECKOUT_REQUEST_CODE && resultCode == RESULT_OK) {
            viewModel.reloadData();
        }
        else if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK){
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_area);
            if(data != null && data.hasExtra("isLogin")){
                if(!data.getBooleanExtra("isLogin", false)){
                    navController.popBackStack(); // This will navigate back to the previous fragment
                }
                else {
                    navController.navigate(R.id.cartFragment);
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.reloadData();
    }
}

