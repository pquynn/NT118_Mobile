package com.example.foodorderingapp.ui.activityfragment.customer.cart;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.data.model.entity.Product;
import com.example.foodorderingapp.data.model.entity.Topping;
import com.example.foodorderingapp.databinding.BottomsheetEditCartBinding;
import com.example.foodorderingapp.ui.activityfragment.authentication.activity_login;
import com.example.foodorderingapp.ui.activityfragment.customer.MainActivity;
import com.example.foodorderingapp.ui.activityfragment.customer.checkout.CheckoutActivity;
import com.example.foodorderingapp.ui.adapter.CartAdapter;
import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;
import com.example.foodorderingapp.databinding.FragmentCartBinding;
import com.example.foodorderingapp.ui.adapter.CartToppingAdapter;
import com.example.foodorderingapp.ui.viewmodel.customer.cart.CartViewModel;
import com.example.foodorderingapp.ui.viewmodel.customer.navigation.BottomNavigationViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment  implements CartAdapter.OnItemClickListener, CartToppingAdapter.OnCheckedChangeListener {
    TextView screenname;
    private Map<String, OrderItem> orderItemMap;
    private FragmentCartBinding binding;
    private BottomsheetEditCartBinding bindingBottomSheet;
    private CartViewModel viewModel;
    private CartAdapter adapter;

    // bottom sheet
    private boolean isDialogOpen = false;
    private List<Topping> toppings;
    private List<Product> productList;
    private List<String> toppingNames, toppingSelected, tempCheckedTopping;
    private int tempOrderItemPrice = 0, oldSizePrice = 0, newSizePrice = 0;
    private Product tempProduct;
    private String tempSize = "";
    private CartToppingAdapter cartToppingAdapter;
    private MainActivity mainActivity;

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
            getActivity().finish();
            startActivity(intent);
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

        //button checkout
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        // init cart View model
        viewModel = new CartViewModel(userId, getActivity());

        //Adapter
        orderItemMap = new HashMap<>();
        productList = new ArrayList<>();
        adapter = new CartAdapter(orderItemMap, productList, viewModel, this::onItemClick, getActivity());
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewCart.setAdapter(adapter);
        binding.setLifecycleOwner(this);

        if(viewModel.getOrderLiveData() == null || viewModel.getOrderLiveData().getValue() == null){
            binding.emptyCartContainer.setVisibility(View.VISIBLE);
            binding.linearLayout4.setVisibility(View.GONE);
            binding.linearLayout5.setVisibility(View.GONE);
        }
        else {
            binding.emptyCartContainer.setVisibility(View.GONE);
            binding.linearLayout4.setVisibility(View.VISIBLE);
            binding.linearLayout5.setVisibility(View.VISIBLE);
        }

        viewModel.getOrderLiveData().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                binding.setCartVM(viewModel);

                //observe productlist when order change
                viewModel.loadProductList();
                viewModel.loadCartItemCount();

                // update order item map
                orderItemMap.clear();
                orderItemMap.putAll(order.getOrderItem());
                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false); // Stop the refreshing animation


                // check if cart is empty or not
                if(order.getOrderItem() == null || order.getOrderItem().isEmpty()){
                    binding.emptyCartContainer.setVisibility(View.VISIBLE);
                    binding.linearLayout4.setVisibility(View.GONE);
                    binding.linearLayout5.setVisibility(View.GONE);
                }
                else {
                    binding.emptyCartContainer.setVisibility(View.GONE);
                    binding.linearLayout4.setVisibility(View.VISIBLE);
                    binding.linearLayout5.setVisibility(View.VISIBLE);
                }
            }
        });

        BottomNavigationViewModel navigationViewModel = new BottomNavigationViewModel(userId);
        viewModel.getCartItemCountLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer != null){
                    mainActivity.updateProductCartQuantity(integer);
                }
            }
        });

        viewModel.getIsValidCheckout().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true){
                    binding.btnCheckout.setEnabled(true);
//                    binding.btnCheckout.setB
                    binding.outOfStockWarning.setVisibility(View.GONE);
                }
                else {
                    binding.btnCheckout.setEnabled(false);
                    binding.outOfStockWarning.setVisibility(View.VISIBLE);
                }
            }
        });

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


    @Override
    public void onItemClick(String key, OrderItem orderItem) {
        if (!isDialogOpen) {
            showDialog(getActivity(), key, orderItem);
        }
    }

    private Dialog dialog = null;
    // Function to show bottom dialog when button is clicked
    public void showDialog(Context context, String key, OrderItem orderItem) {
        isDialogOpen = true; // Update dialog state
        dialog = new Dialog(context); // Corrected line
        bindingBottomSheet = BottomsheetEditCartBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(bindingBottomSheet.getRoot());
        bindingBottomSheet.setLifecycleOwner(getViewLifecycleOwner());

        // load UI
        loadBottomSheetUI(dialog, orderItem);

        // Size Radio button check event listener
        if(orderItem.getSize() != null)
            tempSize = orderItem.getSize();
        tempOrderItemPrice = orderItem.getPrice();

        // Assuming bindingBottomSheet.radioGroup is your RadioGroup

        if(tempSize != null) {
        bindingBottomSheet.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // get old size price
                    if (tempProduct != null && tempProduct.getProductSize() != null) {
                        Map<String, Map<String, Integer>> size = tempProduct.getProductSize();
                        oldSizePrice = size.get(tempSize).get("PRICE");
                        // Find which radio button is checked
                        RadioButton checkedRadioButton = group.findViewById(checkedId);
                        tempSize = checkedRadioButton.getText().toString();
                        newSizePrice = tempProduct.getProductSize().get(tempSize).get("PRICE");

                        tempOrderItemPrice = tempOrderItemPrice + newSizePrice - oldSizePrice;
                        bindingBottomSheet.btnConfirm.setText(setPriceFormatted(tempOrderItemPrice));
                    }
                }

        });
    }

        // Initialize tempCheckedTopping here
        tempCheckedTopping = new ArrayList<>();
        Log.d("firestore", "init tempCheckedTopping: " + tempCheckedTopping.toString());
        // Button confirm edit cart click event listenr
        bindingBottomSheet.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String note = bindingBottomSheet.txtNote.getText().toString();
                viewModel.onConfrimButtonClick(key, tempProduct.getId(), tempSize, tempCheckedTopping, note, tempOrderItemPrice);
            }
        });

        // dismiss dialog
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isDialogOpen = false; // Update dialog state when dismissed
                tempSize = "";
                toppingNames.clear();
                tempCheckedTopping.clear();
                tempOrderItemPrice = 0;
                dialog = null;
                cartToppingAdapter = null;
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public static String setPriceFormatted(int price) {
        double priceDb = (double) price;
        String formattedPrice = new DecimalFormat("#,### đ").format(priceDb);
        return formattedPrice;
    }

    public void loadBottomSheetUI(Dialog dialog, OrderItem orderItem){
        bindingBottomSheet.setCartVM(viewModel); // Set the ViewModel if needed
        bindingBottomSheet.setOrderItem(orderItem);

        // init collection
        toppings = viewModel.getToppings();
        toppingSelected = new ArrayList<>();
        toppingNames = new ArrayList<>();

//        toppingNames.clear();
//        toppingSelected.clear();
//        tempCheckedTopping.clear();

        // init adapter
        cartToppingAdapter = new CartToppingAdapter(toppings, toppingNames, toppingSelected, viewModel, this::onItemCheckedChanged);
        bindingBottomSheet.recyclerViewTopping.setLayoutManager(new LinearLayoutManager(dialog.getOwnerActivity()));
        bindingBottomSheet.recyclerViewTopping.setAdapter(cartToppingAdapter);


        // observe ProductLiveData change to show in dialog
        viewModel.getProductLiveData(orderItem.getIdProduct()).observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                bindingBottomSheet.setProduct(product);
                if(product != null)
                    tempProduct = product;
                // show topping area if not null
                if (product != null && product.getTopping() != null) {
                    bindingBottomSheet.toppingArea.setVisibility(View.VISIBLE);
                    toppingNames.clear();
                    toppingNames.addAll(product.getTopping());
                    Log.d("firestore", "order item topping: " + orderItem.getTopping().toString());
                    toppingSelected.clear();
                    toppingSelected.addAll(orderItem.getTopping());
                    tempCheckedTopping.clear();
                    tempCheckedTopping.addAll(orderItem.getTopping());
//                    cartToppingAdapter.setSelectedToppings(orderItem.getTopping());
                    cartToppingAdapter.notifyDataSetChanged();
                } else {

                    bindingBottomSheet.toppingArea.setVisibility(View.GONE);
                }
                // show size area if not null
                if (product != null && product.getProductSize() != null) {
                    // hide all radio button and just show if one is exist
                    bindingBottomSheet.sizeArea.setVisibility(View.VISIBLE);
                    bindingBottomSheet.rbLarge.setVisibility(View.GONE);
                    bindingBottomSheet.rbMedium.setVisibility(View.GONE);
                    bindingBottomSheet.rbSmall.setVisibility(View.GONE);
                    bindingBottomSheet.tvBigPrice.setVisibility(View.GONE);
                    bindingBottomSheet.tvMediumPrice.setVisibility(View.GONE);
                    bindingBottomSheet.tvSmallPrice.setVisibility(View.GONE);
                    for (Map.Entry<String, Map<String, Integer>> entry : product.getProductSize().entrySet()) {
                        String size = entry.getKey();
                        Map<String, Integer> details = entry.getValue();

                        switch (size) {
                            case "Lớn": {
                                bindingBottomSheet.rbLarge.setVisibility(View.VISIBLE);
                                bindingBottomSheet.tvBigPrice.setVisibility(View.VISIBLE);
                                bindingBottomSheet.tvBigPrice.setText(setPriceFormatted(details.get("PRICE")));
                                break;
                            }
                            case "Vừa": {
                                bindingBottomSheet.rbMedium.setVisibility(View.VISIBLE);
                                bindingBottomSheet.tvMediumPrice.setVisibility(View.VISIBLE);
                                bindingBottomSheet.tvMediumPrice.setText(setPriceFormatted(details.get("PRICE")));
                                break;
                            }
                            case "Nhỏ": {
                                bindingBottomSheet.rbSmall.setVisibility(View.VISIBLE);
                                bindingBottomSheet.tvSmallPrice.setVisibility(View.VISIBLE);
                                bindingBottomSheet.tvSmallPrice.setText(setPriceFormatted(details.get("PRICE")));
                                break;
                            }
                            case "Mặc định":{
                                bindingBottomSheet.sizeArea.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    bindingBottomSheet.sizeArea.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onItemCheckedChanged(boolean isChecked, Topping topping) {
        Log.d("firestore", "old tempCheckedTopping: " + tempCheckedTopping.toString());
        if (isChecked) {
            tempOrderItemPrice += topping.getPriceTopping();
            tempCheckedTopping.add(topping.getNameTopping());
        } else {
            tempOrderItemPrice -= topping.getPriceTopping();
            if (tempCheckedTopping != null)
                tempCheckedTopping.remove(topping.getNameTopping());
        }
        Log.d("firestore", "new tempCheckedTopping: " + tempCheckedTopping.toString());
        bindingBottomSheet.btnConfirm.setText(setPriceFormatted(tempOrderItemPrice));
    }
}

